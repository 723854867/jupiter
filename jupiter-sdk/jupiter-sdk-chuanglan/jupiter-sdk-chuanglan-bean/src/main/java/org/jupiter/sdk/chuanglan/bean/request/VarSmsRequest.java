package org.jupiter.sdk.chuanglan.bean.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jupiter.sdk.chuanglan.bean.model.VarSmsReceiver;
import org.jupiter.sdk.chuanglan.bean.response.VarSmsResponse;
import org.jupiter.util.lang.StringUtil;

import com.google.gson.annotations.Expose;

import lombok.Getter;

/**
 * 变量短信
 * <pre>
 * 注：客户发送短信的账号不是253平台登录账号，是短信接口API账号 。
 * msg字段最多支持20个变量。params字段最多不超过1000个参数组。格式不符的参数，系统自动会过滤。
 * 变量短信第一个变量默认为手机号码，后面的变量依次匹配短信内容中{$var}所代表的变量，且变量符只能为{$var}，变量之间英文逗号隔开，变量参数组之间用英文分号隔开 。
 * 
 * 例子：
 * msg = 【253】尊敬的{$var},你好,您的密码是：{$var},{$var}分钟内有效
 * params = 15800000000,先生,1234,3;13800000000,女士,5678,5
 * </pre>
 * 
 * @author lynn
 */
@Getter
public class VarSmsRequest extends ChuangLanRequest<VarSmsResponse> {

	private static final long serialVersionUID = -8243806773963442668L;

	// 短信内容。长度不能超过536个字符
	@Expose
	private String msg;
	// 该条短信在客户业务系统内的ID，客户自定义（选填参数）
	@Expose
	private String uid;
	// 手机号码和变量参数，多组参数使用英文分号;区分
	@Expose
	private String params;
	// 用户自定义扩展码，纯数字，建议1-3位（选填参数）
	@Expose
	private String extend;
	// 是否需要状态报告（默认为false）（选填参数）
	@Expose
	private boolean report;
	// 定时发送短信时间。格式为yyyyMMddHHmm，值小于或等于当前时间则立即发送，不填则默认为立即发送（选填参数）
	@Expose
	private String sendtime;
	private Map<String, VarSmsReceiver> receivers;
	
	public VarSmsRequest() {
		super("msg/variable/json");
		this.receivers = new HashMap<String, VarSmsReceiver>();
	}
	
	public VarSmsRequest msg(String msg) {
		this.msg = msg;
		return this;
	}
	
	public VarSmsRequest uid(String uid) {
		this.uid = uid;
		return this;
	}
	
	public VarSmsRequest report(boolean report) { 
		this.report = report;
		return this;
	}
	
	public VarSmsRequest extend(String extend) {
		this.extend = extend;
		return this;
	}
	
	public VarSmsRequest sendtime(String sendtime) { 
		this.sendtime = sendtime;
		return this;
	}
	
	public VarSmsRequest addReceiver(VarSmsReceiver receiver) { 
		this.receivers.put(receiver.getMobile(), receiver);
		return this;
	}
	
	public VarSmsRequest addReceiver(String mobile, String... params) { 
		VarSmsReceiver receiver = new VarSmsReceiver();
		receiver.setMobile(mobile);
		receiver.setParams(params);
		this.receivers.put(mobile, receiver);
		return this;
	}
	
	public VarSmsRequest addReceiver(String mobile, List<String> params) { 
		VarSmsReceiver receiver = new VarSmsReceiver();
		receiver.setMobile(mobile);
		receiver.setParams(params.toArray(new String[] {}));
		this.receivers.put(mobile, receiver);
		return this;
	}
	
	public VarSmsRequest addReceivers(List<VarSmsReceiver> receivers) { 
		receivers.forEach(item -> this.receivers.put(item.getMobile(), item));
		return this;
	}
	
	public void rebuild() {
		StringBuilder builder = new StringBuilder();
		for (VarSmsReceiver receiver : receivers.values()) {
			builder.append(receiver.getMobile());
			for (String param : receiver.getParams())
				builder.append(",").append(param);
			builder.append(";");
		}
		builder.deleteCharAt(builder.length() - 1);
		this.params = StringUtil.hasText(this.params) ? this.params + ";" + builder.toString() : builder.toString();
		this.receivers = null;
	}
}
