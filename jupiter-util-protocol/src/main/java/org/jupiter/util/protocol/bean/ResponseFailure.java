package org.jupiter.util.protocol.bean;

/**
 * 响应失败：一般是http错误码大于等于400时抛出，表示请求响应的http异常
 * 
 * @author lynn
 */
public class ResponseFailure extends RuntimeException {

	private static final long serialVersionUID = 9011696017622574471L;
	
	private int code;
	private String msg;
	
	public ResponseFailure() {}
	
	public ResponseFailure(int code, String msg) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}
	
	public int code() {
		return code;
	}
	
	public String msg() {
		return msg;
	}
}
