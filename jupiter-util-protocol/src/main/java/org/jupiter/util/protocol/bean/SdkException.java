package org.jupiter.util.protocol.bean;

/**
 * 一般是请求响应成功，但是业务码返回错误,抛出的是和业务相关的错误信息
 * 
 * @author lynn
 */
public class SdkException extends RuntimeException {

	private static final long serialVersionUID = -891195901785263827L;

	private String code;
	private String desc;
	
	public SdkException() {}
	
	public SdkException(String code, String desc) {
		super(desc);
		this.code = code;
		this.desc = desc;
	}
	
	public String code() {
		return code;
	}
	
	public String desc() {
		return desc;
	}
}
