package org.jupiter.sdk.chuanglan.api;

import org.jupiter.sdk.chuanglan.bean.notice.ReporterCommonNotice;
import org.jupiter.sdk.chuanglan.bean.request.SmsRequest;
import org.jupiter.sdk.chuanglan.bean.request.VarSmsRequest;

public interface ChuangLanService {
	
	/**
	 * 查询还有多少条短信
	 */
	int queryBalance();

	/**
	 * 拉取状态报告：
	 * 
	 * <pre>
	 * 注意主动拉取和回调只能选择一种，如果使用主动拉取，那么{@link #reporterNotice(ReporterCommonNotice)} 就会不执行。
	 * </pre>
	 */
	void pullReporter();

	/**
	 * 普通短信:返回msgId
	 */
	String sendSms(SmsRequest request);

	/**
	 * 变量短信
	 */
	String sendSms(VarSmsRequest request);

	/**
	 * 普通短信报告回调
	 */
	void reporterNotice(ReporterCommonNotice notice);
}
