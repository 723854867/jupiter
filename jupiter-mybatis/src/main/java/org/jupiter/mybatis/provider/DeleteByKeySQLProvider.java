package org.jupiter.mybatis.provider;

import org.apache.ibatis.mapping.MappedStatement;
import org.jupiter.mybatis.DaoAccessor;
import org.jupiter.mybatis.SqlBuilder;
import org.jupiter.mybatis.extension.entity.DBEntity;

public class DeleteByKeySQLProvider extends SQLProvider<String> {

	public DeleteByKeySQLProvider(Class<?> mapperClass, DaoAccessor daoAccessor) {
		super(mapperClass, daoAccessor);
	}

	@Override
	public String effectiveSQL(MappedStatement ms) {
		DBEntity entity = getEntityTable(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlBuilder.deleteFromTable(entity));
        sql.append(SqlBuilder.wherePKColumn(entity));
        return sql.toString();
	}
}
