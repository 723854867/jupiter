package org.jupiter.sdk.chuanglan.bean.response;

import org.jupiter.util.protocol.bean.HttpResonse;
import org.jupiter.util.protocol.bean.SdkException;

import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChuangLanResponse implements HttpResonse {

	private static final long serialVersionUID = 4798392469296741149L;
	
	// 错误码
	@Expose
	private String code;
	// 响应时间
	@Expose
	private String time;
	// 失败状态码说明（成功返回空）
	@Expose
	private String errorMsg;
	
	@Override
	public void verify() {
		if (!code.equals("0"))
			throw new SdkException(code, errorMsg);
	}
}
