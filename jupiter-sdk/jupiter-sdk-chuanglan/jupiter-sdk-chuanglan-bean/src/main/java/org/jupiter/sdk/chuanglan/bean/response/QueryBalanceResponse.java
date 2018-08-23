package org.jupiter.sdk.chuanglan.bean.response;

import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryBalanceResponse extends ChuangLanResponse {

	private static final long serialVersionUID = -4009035778679207414L;
	
	@Expose
	private int balance;
}
