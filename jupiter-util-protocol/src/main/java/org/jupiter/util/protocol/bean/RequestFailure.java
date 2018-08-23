package org.jupiter.util.protocol.bean;

/**
 * 请求失败一般指的是请求失败并且没有响应，或者读取响应时失败
 * 
 * @author lynn
 */
public class RequestFailure extends RuntimeException {

	private static final long serialVersionUID = 7268383650197105834L;

	public RequestFailure() {
		super();
	}

	public RequestFailure(String message) {
		super(message);
	}

	public RequestFailure(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestFailure(Throwable cause) {
		super(cause);
	}
}
