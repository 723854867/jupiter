package org.jupiter.dispatcher;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.jetlang.channels.BatchSubscriber;
import org.jetlang.channels.MemoryChannel;
import org.jetlang.core.Callback;
import org.jetlang.core.Disposable;
import org.jetlang.core.Filter;
import org.jupiter.dispatcher.lane.ILane;
import org.jupiter.dispatcher.lane.LaneGroup;
import org.springframework.util.Assert;

import lombok.NonNull;

/**
 * 该分发器一般用在类似网络数据的处理中。该触发器内部的事件执行是顺序的，因为该触发器只绑定一条线程
 * 
 * @author lynn
 */
public class ThreadSoloDispatcher implements IDispatcher {
	
	private ILane lane;
	private MemoryChannel<Event<?>> eventQueue;
	private ConcurrentHashMap<EventType, IEventHandler<?>> handlers;
	private ConcurrentHashMap<IEventHandler<?>, Disposable> disposableHandlerMap;
	
	public ThreadSoloDispatcher(@NonNull LaneGroup laneGroup) {
		this.lane = laneGroup.chooseLane();
		this.eventQueue = new MemoryChannel<Event<?>>();
		this.handlers = new ConcurrentHashMap<EventType, IEventHandler<?>>();
		this.disposableHandlerMap = new ConcurrentHashMap<IEventHandler<?>, Disposable>();
	}
	
	@Override
	public void dispatch(Event<?> event) {
		if (this.lane.isOnSameLane(Thread.currentThread())) {
			IEventHandler<?> handler = handlers.get(EventType.ANY);
			if (null != handler)
				handler.onEvent(event.getMessage());
			handler = handlers.get(event.getType());
			if (null != handler)
				handler.onEvent(event.getMessage());
		} else 
			this.eventQueue.publish(event);
	}
	
	@Override
	public void addHandler(IEventHandler<?> handler) {
		Assert.isTrue(null == this.handlers.putIfAbsent(handler.eventType(), handler), "handler duplicated");
		Callback<List<Event<?>>> eventCallback = _createEventCallbackForHandler(handler);
		
		Filter<Event<?>> eventFilter = new Filter<Event<?>>() {
			@Override
			public boolean passes(Event<?> msg) {
				return EventType.ANY == handler.eventType() || msg.getType() == handler.eventType();
			}
		};
		BatchSubscriber<Event<?>> batchEventSubscriber = new BatchSubscriber<Event<?>>(lane.fiber(), eventCallback, eventFilter, 0, TimeUnit.MILLISECONDS);
		Disposable disposable = eventQueue.subscribe(batchEventSubscriber);
		this.disposableHandlerMap.put(handler, disposable);
	}
	
	private Callback<List<Event<?>>> _createEventCallbackForHandler(IEventHandler<?> eventHandler) {
		Callback<List<Event<?>>> eventCallback = new Callback<List<Event<?>>>() {
			@Override
			public void onMessage(List<Event<?>> messages) {
				for (Event<?> event : messages) 
					eventHandler.onEvent(event.getMessage());
			}
		};
		return eventCallback;
	}
	
	@Override
	public void removeHandler(IEventHandler<?> eventHandler) {
		this.handlers.remove(eventHandler.eventType());
		Disposable disposable = disposableHandlerMap.remove(eventHandler);
		if (null != disposable)
			disposable.dispose();
	}
	
	@Override
	public void dispose() {
		this.lane.dispose();
		this.lane = null;
		for (Disposable disposable : this.disposableHandlerMap.values())
			disposable.dispose();
		this.handlers.clear();
		this.eventQueue.clearSubscribers();
		this.disposableHandlerMap.clear();
	}
}
