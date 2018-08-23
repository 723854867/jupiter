package org.jupiter.sdk.chuanglan.bean.model;

import java.io.Serializable;

import org.jupiter.util.PhoneUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
public class VarSmsReceiver implements Serializable {

	private static final long serialVersionUID = -6243890554687872511L;

	private String mobile;
	@Setter
	private String[] params;
	
	public void setMobile(String mobile) {
		this.mobile = String.valueOf(PhoneUtil.getNationalNumber(mobile));
	}
}
