package org.jupiter.dispatcher;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jupiter.bean.model.Option;
import org.springframework.util.Assert;

public class EventType extends Option<Integer, String> {

	private static final long serialVersionUID = 2529570510834166252L;
	
	private static final Map<Integer, EventType> types = new ConcurrentHashMap<Integer, EventType>();
	
	public static final EventType ANY					= create(0x00);

	private EventType(Integer key) {
		super(key);
	}
	
	public static final EventType create(int type) { 
		EventType temp = new EventType(type);
		Assert.isNull(types.putIfAbsent(type, temp), "event type duplicated");
		return temp;
	}
}
