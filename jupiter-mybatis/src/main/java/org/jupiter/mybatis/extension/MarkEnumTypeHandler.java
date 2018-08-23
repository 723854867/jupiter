package org.jupiter.mybatis.extension;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.jupiter.bean.IEnum;
import org.jupiter.util.lang.EnumUtil;

public class MarkEnumTypeHandler<MARK, E extends Enum<?> & IEnum<MARK>> extends BaseTypeHandler<E> {
	
	private Class<E> type;
	
	public MarkEnumTypeHandler(Class<E> type) {
		this.type = type;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
		ps.setObject(i, parameter.mark());
	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String str = rs.getString(columnName);
		return rs.wasNull() ? null : _parse(str);
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String str = rs.getString(columnIndex);
		return rs.wasNull() ? null : _parse(str);
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String str = cs.getString(columnIndex);
		return cs.wasNull() ? null : _parse(str);
	}
	
	private E _parse(String mark) { 
		return EnumUtil.valueOf(this.type, mark);
	}
}
