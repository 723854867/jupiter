package org.jupiter.protocol.http;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpConfig implements Serializable {

	private static final long serialVersionUID = -2680718825662934927L;

	// 连接读取超时时间，默认10秒
	@SerializedName("read.timeout")
	private int readTimeout = 10;
	// 连接超时时间，默认10秒
	@SerializedName("conn.timeout")
	private int connTimeout = 10;
	// 协议超时时间，默认10秒
	@SerializedName("write.timeout")
	private int writeTimeout = 10;
	// 最大空闲连接数，默认为5条
	@SerializedName("max.idle.conns")
	private int maxIdleConns = 5;
	// 连接最大空闲时间，超过该时间会被回收，默认300秒
	@SerializedName("conn.keepalive")
	private int connKeepAlive = 300;
}
