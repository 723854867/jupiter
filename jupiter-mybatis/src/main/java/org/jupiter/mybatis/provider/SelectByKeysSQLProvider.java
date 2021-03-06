package org.jupiter.mybatis.provider;

import org.apache.ibatis.mapping.MappedStatement;
import org.jupiter.mybatis.DaoAccessor;
import org.jupiter.mybatis.SqlBuilder;
import org.jupiter.mybatis.extension.entity.DBEntity;

public class SelectByKeysSQLProvider extends SQLProvider<String> {

	public SelectByKeysSQLProvider(Class<?> mapperClass, DaoAccessor daoAccessor) {
		super(mapperClass, daoAccessor);
	}

	@Override
	public String effectiveSQL(MappedStatement ms) {
		DBEntity entity = getEntityTable(ms);
		setResultType(ms, entity);
		StringBuilder sql = new StringBuilder();
		sql.append(SqlBuilder.selectAllColumns(entity));
		sql.append(SqlBuilder.fromTable(entity));
		sql.append(SqlBuilder.whereColumnIn(entity));
		return sql.toString();
	}
}
