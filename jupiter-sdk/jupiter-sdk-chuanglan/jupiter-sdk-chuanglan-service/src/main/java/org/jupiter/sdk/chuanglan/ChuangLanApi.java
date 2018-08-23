package org.jupiter.sdk.chuanglan;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.jupiter.protocol.http.HttpClient;
import org.jupiter.sdk.chuanglan.bean.entity.LogSms;
import org.jupiter.sdk.chuanglan.bean.enums.SmsState;
import org.jupiter.sdk.chuanglan.bean.model.VarSmsReceiver;
import org.jupiter.sdk.chuanglan.bean.notice.ReporterCommonNotice;
import org.jupiter.sdk.chuanglan.bean.request.ChuangLanRequest;
import org.jupiter.sdk.chuanglan.bean.request.QueryBalanceRequest;
import org.jupiter.sdk.chuanglan.bean.request.QuerySmsRequest;
import org.jupiter.sdk.chuanglan.bean.request.SmsRequest;
import org.jupiter.sdk.chuanglan.bean.request.VarSmsRequest;
import org.jupiter.sdk.chuanglan.bean.response.QueryBalanceResponse;
import org.jupiter.sdk.chuanglan.bean.response.QuerySmsResponse;
import org.jupiter.sdk.chuanglan.bean.response.QuerySmsResponse.SmsDetail;
import org.jupiter.sdk.chuanglan.bean.response.SmsResponse;
import org.jupiter.sdk.chuanglan.bean.response.VarSmsResponse;
import org.jupiter.sdk.chuanglan.manager.ChuangLanManager;
import org.jupiter.sdk.chuanglan.mybatis.EntityGenerator;
import org.jupiter.util.lang.CollectionUtil;
import org.jupiter.util.protocol.bean.HttpResonse;
import org.jupiter.util.protocol.bean.RequestFailure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.Response;

@Slf4j
@Component
public class ChuangLanApi {
	
	private Gson gson;
	@Resource
	private HttpClient httpClient;
	@Resource
	private ChuangLanConfig chuangLanConfig;
	@Autowired(required = false)
	private ChuangLanManager chuangLanManager;
	
	public ChuangLanApi() {
		this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.registerTypeAdapter(SmsState.class, new SmsStateSerializer()).create();
	}
	
	public QueryBalanceResponse queryBalance() {
		return _request(new QueryBalanceRequest());
	}
	
	public SmsResponse sendSms(SmsRequest request) {
		if (chuangLanConfig.isReporterPullEnable())
			request.report(true);
		Set<String> receivers = CollectionUtil.splitToStringSet(request.getPhone(), ",");
		SmsResponse response = _request(request);
		if (chuangLanConfig.isLogEnable() && null != chuangLanManager) {
			List<LogSms> logs = EntityGenerator.newLogSms(request, receivers, response);
			chuangLanManager.logSms(logs);
		}
		return response;
	}
	
	public VarSmsResponse sendSms(VarSmsRequest request) {
		if (chuangLanConfig.isReporterPullEnable())
			request.report(true);
		Collection<VarSmsReceiver> receivers = request.getReceivers().values();
		request.rebuild();
		VarSmsResponse response = _request(request);
		if (chuangLanConfig.isLogEnable() && null != chuangLanManager) {
			List<LogSms> logs = EntityGenerator.newLogSms(request, receivers, response);
			chuangLanManager.logSms(logs);
		}
		return response;
	}
	
	public void reporterNotice(ReporterCommonNotice notice) { 
		if (null != chuangLanManager)
			chuangLanManager.reporterNotice(notice);
	}
	
	public void pullReporter() {
		QuerySmsResponse response = _request(new QuerySmsRequest());
		List<SmsDetail> details = response.getResult();
		if (!CollectionUtil.isEmpty(details) && null != chuangLanManager)
			chuangLanManager.syncLogSms(details);
	}

	private <RESPONSE extends HttpResonse> RESPONSE _request(ChuangLanRequest<RESPONSE> request) {
		String path = request.path();
		request.account(chuangLanConfig.getAccount());
		request.password(chuangLanConfig.getPassword());
		HttpUrl.Builder builder = new HttpUrl.Builder();
		builder.scheme("http");
		builder.addPathSegments(path);
		builder.host(chuangLanConfig.getHost());
		String content = gson.toJson(request);
		log.info("创蓝请求：{}", content);
		Response response = httpClient.postJson(builder.build(), content);
		RESPONSE resp;
		try {
			resp = gson.fromJson(response.body().string(), request.responseClass());
		} catch (JsonSyntaxException | IOException e) {
			throw new RequestFailure(e);
		}
		log.info("创蓝响应：{}", gson.toJson(resp));
		resp.verify();
		return resp;
	}
	
	public static final SmsState state(String value) {
		switch (value) {
		case "DELIVRD":
			return SmsState.DELIVRD;
		case "UNKNOWN":
			return SmsState.UNKNOWN;
		case "REJECTD":
			return SmsState.REJECT;
		case "MBBLACK":
			return SmsState.MBBLACK;
		case "REJECT":
			return SmsState.REJECT;
		default:
			return SmsState.OTHER;
		}
	}
}
