package org.jupiter.mail;

import java.util.List;
import java.util.Properties;

import org.jupiter.bean.enums.ColumnStyle;
import org.jupiter.util.reflect.EntityField;
import org.jupiter.util.reflect.FieldHelper;
import org.jupiter.util.spring.ConfigLoader;
import org.jupiter.util.spring.ConfigMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailInitializer {
	
	@Bean
	public MailConfig mailConfig() {
		ConfigMap map = ConfigLoader.load("classpath:conf/mail.properties");
		MailConfig config = map.toBean(MailConfig.class, ColumnStyle.camel2dot);
		List<EntityField> fields = FieldHelper.getAll(MailConfig.class);
		fields.forEach(field -> map.remove(field.getName()));
		Properties properties = new Properties();
		map.entrySet().forEach(entry -> properties.setProperty(entry.getKey(), entry.getValue().toString().trim()));
		config.setProperties(properties);
		return config;
	}

	@Bean
	public JavaMailSender javaMailSender(MailConfig mailConfig) {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(mailConfig.getHost());
		sender.setPort(mailConfig.getPort());
		sender.setProtocol(mailConfig.getProtocol());
		sender.setUsername(mailConfig.getUsername());
		sender.setPassword(mailConfig.getPassword());
		sender.setDefaultEncoding(mailConfig.getEncoding());
		sender.setJavaMailProperties(mailConfig.getProperties());
		return sender;
	}
}
