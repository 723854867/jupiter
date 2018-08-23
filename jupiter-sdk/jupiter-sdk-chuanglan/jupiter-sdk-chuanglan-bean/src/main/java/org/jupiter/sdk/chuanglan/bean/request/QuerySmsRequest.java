package org.jupiter.sdk.chuanglan.bean.request;

import org.jupiter.sdk.chuanglan.bean.response.QuerySmsResponse;

import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

/**
 * 短信下发成功，可调用此接口拉取短信状态报告。
 * 
 * 1、拉取过的内容不会再重复返回，可以理解为消息队列机制，请用户及时存储。
 * 2、此功能需注意账号需要开启主动拉取功能(在创蓝后台设置或者联系创蓝客服)。
 * 3、客户发送短信时report设置为true方可拉取到状态报告，否则为空数组
 * 
 * @author lynn
 */
@Getter
@Setter
public class QuerySmsRequest extends ChuangLanRequest<QuerySmsResponse> {

	private static final long serialVersionUID = 2336722928058980504L;

	// 拉取个数(最大100，默认20)
	@Expose
	private int count = 20;
	
	public QuerySmsRequest() {
		super("msg/pull/report ");
	}
}
