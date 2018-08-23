package org.jupiter.sdk.chuanglan.service;

import javax.annotation.Resource;

import org.jupiter.sdk.chuanglan.ChuangLanApi;
import org.jupiter.sdk.chuanglan.api.ChuangLanService;
import org.jupiter.sdk.chuanglan.bean.notice.ReporterCommonNotice;
import org.jupiter.sdk.chuanglan.bean.request.SmsRequest;
import org.jupiter.sdk.chuanglan.bean.request.VarSmsRequest;
import org.springframework.stereotype.Service;

@Service("chuangLanService")
public class ChuangLanServiceImpl implements ChuangLanService {
	
	@Resource
	private ChuangLanApi chuangLanApi;

	@Override
	public int queryBalance() {
		return chuangLanApi.queryBalance().getBalance();
	}

	@Override
	public void pullReporter() {
		chuangLanApi.pullReporter();
	}

	@Override
	public String sendSms(SmsRequest request) {
		return chuangLanApi.sendSms(request).getMsgId();
	}

	@Override
	public String sendSms(VarSmsRequest request) {
		return chuangLanApi.sendSms(request).getMsgId();
	}

	@Override
	public void reporterNotice(ReporterCommonNotice notice) {
		chuangLanApi.reporterNotice(notice);
	}
}
