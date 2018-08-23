package org.jupiter.sdk.chuanglan;

import org.jupiter.bean.enums.ColumnStyle;
import org.jupiter.util.spring.ConfigLoader;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class LogEnableCondition implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		ChuangLanConfig config = ConfigLoader.load("classpath:conf/chuanglan.properties").toBean(ChuangLanConfig.class, ColumnStyle.camel2dot);
		return config.isLogEnable();
	}
}
