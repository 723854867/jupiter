package org.jupiter.dispatcher;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.jetlang.channels.BatchSubscriber;
import org.jetlang.channels.MemoryChannel;
import org.jetlang.core.BatchExecutor;
import org.jetlang.core.BatchExecutorImpl;
import org.jetlang.core.Callback;
import org.jetlang.core.Disposable;
import org.jetlang.core.Filter;
import org.jetlang.fibers.Fiber;
import org.jetlang.fibers.PoolFiberFactory;
import org.springframework.util.Assert;

/**
 * 异步事件分发器，一般做成单例，该分发器内部有一个线程池专门负责处理事件
 * 
 * @author lynn
 */
public class ThreadPoolDispatcher implements IDispatcher {

	private Fiber fiber;
	private MemoryChannel<Event<?>> eventQueue;
	private ConcurrentHashMap<EventType, HandlerWrapper> handlers;
	
	public ThreadPoolDispatcher(ThreadPoolExecutor executor) {
		this(executor, new BatchExecutorImpl());
	}
	
	public ThreadPoolDispatcher(ThreadPoolExecutor executor, BatchExecutor batchExecutor) {
		this.eventQueue = new MemoryChannel<Event<?>>();
		this.fiber = new PoolFiberFactory(executor).create(batchExecutor);
		this.fiber.start();
		this.handlers = new ConcurrentHashMap<EventType, HandlerWrapper>();
	}
	
	@Override
	public void dispose() {
		this.fiber.dispose();
		this.fiber = null;
		for (HandlerWrapper wrapper : this.handlers.values()) {
			if (null == wrapper.disposable)
				continue;
			wrapper.disposable.dispose();
		}
		this.handlers.clear();
		this.eventQueue.clearSubscribers();
	}

	@Override
	public void dispatch(Event<?> event) {
		this.eventQueue.publish(event);
	}

	@Override
	public void addHandler(IEventHandler<?> handler) {
		HandlerWrapper wrapper = new HandlerWrapper();
		Assert.isTrue(null == this.handlers.putIfAbsent(handler.eventType(), wrapper), "hanlder duplicated");
		Callback<List<Event<?>>> eventCallback = _createEventCallbackForHandler(handler);
		
		Filter<Event<?>> eventFilter = new Filter<Event<?>>() {
			@Override
			public boolean passes(Event<?> msg) {
				return handler.eventType() == EventType.ANY || msg.getType() == handler.eventType();
			}
		};
		BatchSubscriber<Event<?>> batchEventSubscriber = new BatchSubscriber<Event<?>>(fiber, eventCallback, eventFilter, 0, TimeUnit.MILLISECONDS);
		Disposable disposable = eventQueue.subscribe(batchEventSubscriber);
		wrapper.disposable = disposable;
	}
	
	private Callback<List<Event<?>>> _createEventCallbackForHandler(IEventHandler<?> handler) {
		Callback<List<Event<?>>> eventCallback = new Callback<List<Event<?>>>() {
			@Override
			public void onMessage(List<Event<?>> messages) {
				for (Event<?> event : messages) 
					handler.onEvent(event.getMessage());
			}
		};
		return eventCallback;
	}

	@Override
	public void removeHandler(IEventHandler<?> handler) {
		HandlerWrapper wrapper = this.handlers.remove(handler.eventType());
		if (null != wrapper) {
			Disposable disposable = wrapper.disposable;
			if (null != disposable)
				disposable.dispose();
		}
	}
	
	private class HandlerWrapper {
		private Disposable disposable;
	}
}
