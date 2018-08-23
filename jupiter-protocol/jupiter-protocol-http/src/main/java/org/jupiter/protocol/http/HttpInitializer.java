package org.jupiter.protocol.http;

import org.jupiter.bean.enums.ColumnStyle;
import org.jupiter.util.spring.ConfigLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class HttpInitializer {

	@Bean
	public HttpConfig httpConfig() {
		return ConfigLoader.load("classpath*:conf/http.properties").toBean(HttpConfig.class, ColumnStyle.camel2dot);
	}
}
