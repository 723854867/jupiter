package org.jupiter.util.spring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jupiter.bean.enums.ColumnStyle;
import org.jupiter.util.reflect.EntityField;
import org.jupiter.util.reflect.FieldHelper;
import org.jupiter.util.serializer.GsonSerializer;

@SuppressWarnings("unchecked")
public class ConfigMap extends HashMap<String, Object> {

	private static final long serialVersionUID = -2218917891980885781L;
	
	@Override
	public Object put(String key, Object value) {
		Object pre = get(key);
		if (null == pre) 
			super.put(key, value);
		else {
			if (pre instanceof String) {
				List<Object> list = new ArrayList<Object>();
				list.add(pre.toString());
				list.add(value);
				super.put(key, list);
			} else {
				List<Object> list = (List<Object>) pre;
				list.add(value);
			}
		}
		return null;
	}
	
	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		m.entrySet().forEach(item -> ConfigMap.this.put(item.getKey(), item.getValue()));
	}
	
	public <T> T toBean(Class<T> clazz, ColumnStyle style) {
		List<EntityField> fields = FieldHelper.getAll(clazz);
		fields.forEach(field -> {
			if (Collection.class.isAssignableFrom(field.getJavaType())) {
				String name = style.convert(field.getName());
				Object value = get(name);
				if (null != value && (value instanceof String)) {
					try {
						Collection<Object> collection = null;
						if (field.getJavaType() == Set.class) 
							collection = new HashSet<Object>();
						else if (field.getJavaType() == List.class) 
							collection = new ArrayList<Object>();
						else
							collection = (Collection<Object>) field.getJavaType().newInstance();
						collection.add(value);
						remove(name);
						put(name, collection);
					} catch (Exception e) {
						throw new RuntimeException(e);
					} 
				}
			}
		});
		return GsonSerializer.mapToBean(this, clazz);
	}
}
