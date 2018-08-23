package org.jupiter.sdk.chuanglan.bean.enums;

import org.jupiter.bean.IEnum;

public enum SmsState implements IEnum<Integer> {
	
	// 短信发送成功
	DELIVRD(1),
	// 未知短信状态
	UNKNOWN(2),
	// 短信被短信中心拒绝
	REJECTD(3),
	// 目的号码是黑名单号码
	MBBLACK(4),
	// 审核驳回
	REJECT(5),
	// 其他状态
	OTHER(6);
	
	private int mark;
	
	private SmsState(int mark) {
		this.mark = mark;
	}

	@Override
	public Integer mark() {
		return this.mark;
	}
}
