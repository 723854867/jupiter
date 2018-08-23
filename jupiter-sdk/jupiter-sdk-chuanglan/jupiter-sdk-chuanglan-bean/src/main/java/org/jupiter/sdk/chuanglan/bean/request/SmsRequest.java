package org.jupiter.sdk.chuanglan.bean.request;

import org.jupiter.sdk.chuanglan.bean.response.SmsResponse;
import org.jupiter.util.PhoneUtil;
import org.jupiter.util.lang.StringUtil;

import com.google.gson.annotations.Expose;

/**
 * 普通短信
 * <pre>
 * 注：客户发送短信的账号不是253平台登录账号，是短信接口API账号 
 * 
 * 发送的短信内容一定要加上签名，账号有报备过签名的可不用带上签名， 但如果您有多个短信签名，请将需要的短信签名放在短信内容前面 。
 * 例如您有”【253云通讯】”，”【通讯云】”两个签名，但是想以”【通讯云】”签名发送短信，则”msg”字段可赋值为：”【通讯云】你的验证码是xxxx”，不填默认第一个签名。
 * 
 * 用户自定义扩展码，发送短信显示号码，规则为：10690+xxx（通道号）+xxx(系统扩展码) +xxx（用户自定义扩展码）；扩展码的长度将直接影响短信上行接收的接收。如需要传扩展码参数时，请咨询客服相关设置问题 。
 * </pre>
 * @author lynn
 */
public class SmsRequest extends ChuangLanRequest<SmsResponse> {

	private static final long serialVersionUID = 4899276237389588666L;

	// 短信内容。长度不能超过536个字符
	@Expose
	private String msg;
	// 自助通系统内使用UID判断短信使用的场景类型，可重复使用，可自定义场景名称，示例如 VerificationCode（选填参数）
	@Expose
	private String uid;
	// 手机号码。多个手机号码使用英文逗号分隔
	@Expose
	private String phone;
	// 是否需要状态报告（默认为false）（选填参数）
	@Expose
	private boolean report;
	// 用户自定义扩展码，纯数字，建议1-3位（选填参数）
	@Expose
	private String extend;
	// 定时发送短信时间。格式为yyyyMMddHHmm，值小于或等于当前时间则立即发送，不填则默认为立即发送（选填参数）
	@Expose
	private String sendtime;
	
	public SmsRequest() {
		super("msg/send/json");
	}
	
	public String getMsg() {
		return msg;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getSendtime() {
		return sendtime;
	}
	
	public SmsRequest msg(String msg) {
		this.msg = msg;
		return this;
	}
	
	public SmsRequest uid(String uid) {
		this.uid = uid;
		return this;
	}
	
	public SmsRequest report(boolean report) { 
		this.report = report;
		return this;
	}
	
	public SmsRequest extend(String extend) {
		this.extend = extend;
		return this;
	}
	
	public SmsRequest sendtime(String sendtime) { 
		this.sendtime = sendtime;
		return this;
	}
	
	/**
	 * 可以发送多个手机号
	 */
	public SmsRequest appendPhone(String phone) {
		if (StringUtil.hasText(this.phone))
			this.phone = this.phone + "," + String.valueOf(PhoneUtil.getNationalNumber(phone));
		else
			this.phone = String.valueOf(PhoneUtil.getNationalNumber(phone));
		return this;
	}
}
