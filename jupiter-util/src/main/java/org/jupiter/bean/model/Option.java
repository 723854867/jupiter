package org.jupiter.bean.model;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.jupiter.bean.Identifiable;

import lombok.NonNull;

@SuppressWarnings("unchecked")
public class Option<KEY, VALUE> implements Identifiable<KEY> {

	private static final long serialVersionUID = -4622569287357931781L;
	
	protected KEY key;
	protected VALUE value;
	protected Class<VALUE> clazz;
	
	/**
	 * 仅供子类继承使用
	 * 
	 * @param key
	 */
	protected Option(@NonNull KEY key) {
		this.key = key;
		Type superType = getClass().getGenericSuperclass();   
		Type[] generics = ((ParameterizedType) superType).getActualTypeArguments();  
		this.clazz = (Class<VALUE>) generics[0];
	}
	
	public Option(@NonNull KEY key, @NonNull VALUE value) {
		this.key = key;
		this.value = value;
		this.clazz = (Class<VALUE>) value.getClass();
	}
	
	public Option(@NonNull KEY key, @NonNull Class<VALUE> clazz) {
		this.key = key;
		this.clazz = clazz;
	}
	
	@Override
	public KEY key() {
		return this.key;
	}
	
	public VALUE value() {
		return value;
	}
	
	public Class<VALUE> clazz() {
		return clazz;
	}
	
	public void setValue(VALUE value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Option<?, ?> other = (Option<?, ?>) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
}
