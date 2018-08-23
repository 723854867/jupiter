package org.jupiter.dispatcher;

public interface IDispatcher {
	
	void dispose();

	void dispatch(Event<?> event);
	
	void addHandler(IEventHandler<?> handler);
	
	void removeHandler(IEventHandler<?> handler);
}
