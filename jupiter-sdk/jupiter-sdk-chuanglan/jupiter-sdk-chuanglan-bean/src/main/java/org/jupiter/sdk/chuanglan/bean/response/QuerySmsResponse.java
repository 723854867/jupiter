package org.jupiter.sdk.chuanglan.bean.response;

import java.io.Serializable;
import java.util.List;

import org.jupiter.sdk.chuanglan.bean.enums.SmsState;
import org.jupiter.util.protocol.bean.HttpResonse;
import org.jupiter.util.protocol.bean.SdkException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuerySmsResponse implements HttpResonse {

	private static final long serialVersionUID = -5159721344718164389L;

	// 请求状态。0成功，其他状态为失败
	private int ret;
	private String error;
	private List<SmsDetail> result;
	
	@Override
	public void verify() {
		if (ret != 0)
			throw new SdkException(String.valueOf(ret), error);
	}
	
	@Getter
	@Setter
	public static class SmsDetail implements Serializable {
		
		private static final long serialVersionUID = 8835357790273122182L;
		
		private String uid;
		private String msgId;
		private String mobile;
		private SmsState status;
		private String statusDesc;
		private String reportTime;
		private String notifyType;
	}
}
