package org.jupiter.mybatis.provider;

import java.util.Set;

import org.apache.ibatis.mapping.MappedStatement;
import org.jupiter.mybatis.DaoAccessor;
import org.jupiter.mybatis.extension.entity.DBEntity;
import org.jupiter.mybatis.extension.entity.DBEntityCol;

public class ReplaceMapSQLProvider extends SQLProvider<String> {
	
	private static final String foreachSuffix = "</foreach>";
	private static final String foreachPrefix = "<foreach item=\"value\" index=\"key\" collection=\"map\" separator=\",\">";

	public ReplaceMapSQLProvider(Class<?> mapperClass, DaoAccessor daoAccessor) {
		super(mapperClass, daoAccessor);
	}

	@Override
	public String effectiveSQL(MappedStatement ms) {
		DBEntity entity = getEntityTable(ms);
		StringBuilder builder = new StringBuilder("REPLACE INTO ").append(entity.getName()).append("(");
		Set<DBEntityCol> columns = entity.columns();
		for (DBEntityCol column : columns)
			builder.append(column.getName()).append(",");
		builder.deleteCharAt(builder.length() - 1).append(") VALUES").append(foreachPrefix).append("(");
		for (DBEntityCol column : columns) 
			builder.append("#{value.").append(column.getEntityField().getName()).append("},");
		builder.deleteCharAt(builder.length() - 1).append(")").append(foreachSuffix);
		return builder.toString();
	}
}
