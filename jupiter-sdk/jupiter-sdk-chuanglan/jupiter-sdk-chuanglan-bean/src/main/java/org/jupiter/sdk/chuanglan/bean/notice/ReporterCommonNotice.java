package org.jupiter.sdk.chuanglan.bean.notice;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

/**
 * 普通短信报告回调
 * 
 * @author lynn
 */
@Getter
@Setter
public class ReporterCommonNotice implements Serializable {

	private static final long serialVersionUID = 7150212707275617497L;

	// 用户在提交短信时传入的uid参数，状态报告会原样返回此参数，未提交则无该参数
	private String uid;
	// 接收验证的密码，可以为空
	private String pswd;
	// 消息id
	@NotEmpty
	private String msgid;
	// 状态码
	@NotEmpty
	private String status;
	// 接收短信的手机号码
	@NotEmpty
	private String mobile;
	// 接受验证的用户名(不是账户名)，是按照用户要求配置的名称，可以为空
	private String receiver;
	// 状态说明，内容UTF-8编码
	private String statusDesc;
	// 253平台收到运营商回复状态报告的时间，格式yyMMddHHmmss
	private String notifyTime;
	// 用户服务器地址
	private String client_url;
	// 运营商返回的状态更新时间，格式yyMMddHHmm，其中yy=年份的最后两位（00-99）
	private String reportTime;
}
