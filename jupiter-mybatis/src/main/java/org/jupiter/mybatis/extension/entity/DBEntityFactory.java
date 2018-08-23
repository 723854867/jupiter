package org.jupiter.mybatis.extension.entity;

import javax.persistence.Column;
import javax.persistence.Id;

import org.jupiter.bean.enums.ColumnStyle;
import org.jupiter.mybatis.bean.enums.Dialect;
import org.jupiter.util.reflect.EntityFactory;
import org.jupiter.util.reflect.EntityField;

public class DBEntityFactory extends EntityFactory<DBEntity, DBEntityCol> {

	public DBEntityFactory() {
		this(Dialect.mysql);
	}
	
	public DBEntityFactory(Dialect dialect) {
		this.reservedWordWrapper = dialect.keyWordWrapper();
	}
	
	@Override
	protected DBEntityCol processField(DBEntity entity, ColumnStyle style, EntityField field) {
		DBEntityCol col = super.processField(entity, style, field);
		if (field.isAnnotationPresent(Id.class))
			col.setId(true);
		if (field.isAnnotationPresent(Column.class)) {
			Column column = field.getAnnotation(Column.class);
			col.setUpdatable(column.updatable());
			col.setInsertable(column.insertable());
		}
		return col;
	}
}
