package org.jupiter.sdk.chuanglan.bean.request;

import org.jupiter.sdk.chuanglan.bean.response.QueryBalanceResponse;

/**
 * 查询账户余额
 * <pre>
 * 注：客户发送的账号不是253平台登录账号，是短信接口API账号
 * </pre>
 * 
 * @author lynn
 */
public class QueryBalanceRequest extends ChuangLanRequest<QueryBalanceResponse> {

	private static final long serialVersionUID = -871589302241546997L;
	
	public QueryBalanceRequest() {
		super("msg/balance/json");
	}
}
