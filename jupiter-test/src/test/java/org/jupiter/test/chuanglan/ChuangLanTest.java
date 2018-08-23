package org.jupiter.test.chuanglan;

import javax.annotation.Resource;

import org.junit.Test;
import org.jupiter.sdk.chuanglan.api.ChuangLanService;
import org.jupiter.sdk.chuanglan.bean.request.SmsRequest;
import org.jupiter.sdk.chuanglan.bean.request.VarSmsRequest;
import org.jupiter.test.JupiterTest;

public class ChuangLanTest extends JupiterTest {

	@Resource
	private ChuangLanService chuangLanService;
	
	@Test
	public void testQueryBalance() {
		int balance = chuangLanService.queryBalance();
		System.out.println(balance);
	}
	
	@Test
	public void testSendSms() {
		SmsRequest request = new SmsRequest();
		request.appendPhone("15888837752");
		request.msg("hello world");
		String msgId = chuangLanService.sendSms(request);
		System.out.println(msgId);
	}
	
	@Test
	public void testSendVarSms() {
		VarSmsRequest request = new VarSmsRequest();
		request.msg("尊敬的{$var},你好,您的密码是：{$var},{$var}分钟内有效");
		request.addReceiver("15888837752", "张先生", "12345", "2");
		request.addReceiver("15888837752", "樊先生", "12345", "2");
		String msgId = chuangLanService.sendSms(request);
		System.out.println(msgId);
	}
	
	@Test
	public void testPullReporter() {
		chuangLanService.pullReporter();
	}
}
