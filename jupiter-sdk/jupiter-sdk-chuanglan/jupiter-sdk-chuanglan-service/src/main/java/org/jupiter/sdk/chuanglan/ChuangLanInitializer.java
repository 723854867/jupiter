package org.jupiter.sdk.chuanglan;

import org.jupiter.bean.enums.ColumnStyle;
import org.jupiter.util.spring.ConfigLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ChuangLanInitializer {
	
	@Bean
	public ChuangLanConfig chuangLanConfig() {
		return ConfigLoader.load("classpath:conf/chuanglan.properties").toBean(ChuangLanConfig.class, ColumnStyle.camel2dot);
	}
}
