package org.jupiter.dispatcher;

/**
 * 用来处理Event的类
 * 
 * @author Ahab
 *
 * @param <T> 处理Event的返回值
 */
public interface IEventHandler<MESSAGE> {
	
	/**
	 * 事件类型
	 * 
	 * @return
	 */
	EventType eventType();
	
	@SuppressWarnings("unchecked")
	default void onEvent(Object object) {
		onMessage((MESSAGE) object);
	}
	
	void onMessage(MESSAGE message);
}
