package org.jupiter.dispatcher;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Event<MESSAGE> implements Serializable {

	private static final long serialVersionUID = -4731608502721174450L;
	
	private EventType type;
	@Setter
	private MESSAGE message;
	
	public Event(EventType type) {
		this.type = type;
	}
	
	public Event(EventType type, MESSAGE message) {
		this.type = type;
		this.message = message;
	}
}
