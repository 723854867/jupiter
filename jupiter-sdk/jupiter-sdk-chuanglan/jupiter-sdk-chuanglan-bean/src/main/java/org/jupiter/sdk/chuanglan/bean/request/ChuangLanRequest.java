package org.jupiter.sdk.chuanglan.bean.request;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.jupiter.util.protocol.bean.HttpResonse;

import com.google.gson.annotations.Expose;

public class ChuangLanRequest<RESPONSE extends HttpResonse> implements Serializable {

	private static final long serialVersionUID = -3054061359373628956L;

	private String path;
	// 用户在253云通讯平台上申请的API账号
	@Expose
	private String account;
	@Expose
	// 用户在253云通讯平台上申请的API账号对应的API密钥
	private String password;
	private Class<RESPONSE> responseClass;
	
	@SuppressWarnings("unchecked")
	protected ChuangLanRequest(String path) {
		this.path = path;
		Type superType = getClass().getGenericSuperclass();   
		Type[] generics = ((ParameterizedType) superType).getActualTypeArguments();  
		this.responseClass = (Class<RESPONSE>) generics[0];
	}
	
	public String path() {
		return this.path;
	}
	
	public Class<RESPONSE> responseClass() {
		return responseClass;
	}
	
	public ChuangLanRequest<RESPONSE> account(String account) {
		this.account = account;
		return this;
	}
	
	public ChuangLanRequest<RESPONSE> password(String password) {
		this.password = password;
		return this;
	}
}
