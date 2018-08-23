package org.jupiter.mybatis;

import org.jupiter.mybatis.extension.entity.DBEntityFactory;
import org.jupiter.util.lang.CollectionUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoScannerInitializer {

	/**
	 * 该 bean 不能和 @EnableTransactionManagement 一起使用，因为该 bean 是一个spring的生命周期bean
	 * 有特殊的初始化
	 * @return
	 */
	@Bean
	public DaoScanner daoScanner(DBConfig dbConfig) {
		DaoScanner scanner = new DaoScanner();
		DBEntityFactory entityFactory = new DBEntityFactory(dbConfig.getDialect());
		DaoAccessor daoAccessor = new DaoAccessor();
		daoAccessor.setEntityFactory(entityFactory);
		scanner.setDaoAccessor(daoAccessor);
		if (!CollectionUtil.isEmpty(dbConfig.getBasePackage()))
			scanner.setBasePackage(CollectionUtil.toString(dbConfig.getBasePackage(), ";"));
		return scanner;
	}
}
