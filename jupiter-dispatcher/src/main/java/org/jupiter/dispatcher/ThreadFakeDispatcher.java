package org.jupiter.dispatcher;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.Assert;

/**
 * 该分发器不使用线程进行异步事件分发，仅仅是使用同一条线程执行消息
 * 
 * @author lynn
 */
public class ThreadFakeDispatcher implements IDispatcher {

	private ConcurrentHashMap<EventType, IEventHandler<?>> handlers;
	
	public ThreadFakeDispatcher() {
		this.handlers = new ConcurrentHashMap<EventType, IEventHandler<?>>();
	}
	
	@Override
	public void dispose() {
		this.handlers.clear();
	}

	@Override
	public void dispatch(Event<?> event) {
		IEventHandler<?> eventHandler = handlers.get(EventType.ANY);
		if (null != eventHandler)
			eventHandler.onEvent(event.getMessage());
		eventHandler = handlers.get(event.getType());
		if (null != eventHandler)
			eventHandler.onEvent(event.getMessage());
	}

	@Override
	public void addHandler(IEventHandler<?> handler) {
		Assert.isTrue(null == this.handlers.putIfAbsent(handler.eventType(), handler), "handler duplicated");
	}

	@Override
	public void removeHandler(IEventHandler<?> handler) {
		this.handlers.remove(handler.eventType());
	}
}
