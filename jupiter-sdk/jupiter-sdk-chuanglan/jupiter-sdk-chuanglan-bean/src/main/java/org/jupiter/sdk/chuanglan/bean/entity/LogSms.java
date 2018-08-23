package org.jupiter.sdk.chuanglan.bean.entity;

import javax.persistence.Id;

import org.jupiter.bean.Identifiable;
import org.jupiter.sdk.chuanglan.bean.enums.SmsState;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogSms implements Identifiable<String> {

	private static final long serialVersionUID = 3695974720916472338L;
	
	private int created;
	private int updated;
	@Id
	private String msgId;
	private String mobile;
	private SmsState state;
	private String content;
	private String sendTime;

	@Override
	public String key() {
		return this.msgId;
	}
}
