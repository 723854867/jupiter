package org.jupiter.sdk.chuanglan.mybatis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.jupiter.sdk.chuanglan.bean.entity.LogSms;
import org.jupiter.sdk.chuanglan.bean.enums.SmsState;
import org.jupiter.sdk.chuanglan.bean.model.VarSmsReceiver;
import org.jupiter.sdk.chuanglan.bean.request.SmsRequest;
import org.jupiter.sdk.chuanglan.bean.request.VarSmsRequest;
import org.jupiter.sdk.chuanglan.bean.response.SmsResponse;
import org.jupiter.sdk.chuanglan.bean.response.VarSmsResponse;
import org.jupiter.util.lang.DateUtil;
import org.jupiter.util.lang.StringUtil;

public class EntityGenerator {

	public static final List<LogSms> newLogSms(SmsRequest request, Set<String> receivers, SmsResponse response) {
		List<LogSms> list = new ArrayList<LogSms>();
		for (String receiver : receivers) {
			LogSms instance = new LogSms();
			instance.setMobile(receiver);
			instance.setState(SmsState.UNKNOWN);
			instance.setContent(request.getMsg());
			instance.setMsgId(response.getMsgId());
			instance.setSendTime(StringUtil.hasText(request.getSendtime()) ? request.getSendtime() : StringUtil.EMPTY);
			int time = DateUtil.current();
			instance.setCreated(time);
			instance.setUpdated(time);
			list.add(instance);
		}
		return list;
	}
	
	public static final List<LogSms> newLogSms(VarSmsRequest request, Collection<VarSmsReceiver> receivers, VarSmsResponse response) {
		List<LogSms> list = new ArrayList<LogSms>();
		for (VarSmsReceiver receiver : receivers) {
			LogSms instance = new LogSms();
			String msg = request.getMsg();
			for (String param : receiver.getParams()) 
				msg = msg.replaceFirst("\\{\\$var\\}", param);
			instance.setContent(msg);
			instance.setState(SmsState.UNKNOWN);
			instance.setMsgId(response.getMsgId());
			instance.setMobile(receiver.getMobile());
			instance.setSendTime(StringUtil.hasText(request.getSendtime()) ? request.getSendtime() : StringUtil.EMPTY);
			int time = DateUtil.current();
			instance.setCreated(time);
			instance.setUpdated(time);
			list.add(instance);
		}
		return list;
	}
}
