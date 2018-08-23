package org.jupiter.sdk.chuanglan;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChuangLanConfig implements Serializable {

	private static final long serialVersionUID = -6819201518067061332L;
	
	private String host;
	private String account;
	private String password;
	@SerializedName("log.enable")
	private boolean logEnable;
	@SerializedName("reporter.pull.enable")
	private boolean reporterPullEnable;
}
