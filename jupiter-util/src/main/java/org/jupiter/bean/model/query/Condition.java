package org.jupiter.bean.model.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;

@Getter
public class Condition implements Serializable {
	
	private static final long serialVersionUID = 7167607188227959520L;
	
	private String col;
	private Object value;
	private int comparison;
	
	public Condition() {}
	
	public Condition(String col, Comparison comparison) {
		this(col, comparison, null);
	}
	
	public Condition(String col, Comparison comparison, Object value) {
		this.col = col;
		this.value = value;
		this.comparison = comparison.mark();
	}
	
	public static final Condition isnull(String col) {
		return new Condition(col, Comparison.isnull);
	}
	
	public static final Condition notnull(String col) {
		return new Condition(col, Comparison.notnull);
	}
	
	public static final Condition eq(String col, Object value) {
		return new Condition(col, Comparison.eq, value);
	}
	
	public static final Condition neq(String col, Object value) {
		return new Condition(col, Comparison.neq, value);
	}
	
	public static final Condition lt(String col, Object value) {
		return new Condition(col, Comparison.lt, value);
	}
	
	public static final Condition lte(String col, Object value) {
		return new Condition(col, Comparison.lte, value);
	}
	
	public static final Condition gt(String col, Object value) {
		return new Condition(col, Comparison.gt, value);
	}
	
	public static final Condition gte(String col, Object value) {
		return new Condition(col, Comparison.gte, value);
	}
	
	public static final Condition like(String col, Object value) {
		return new Condition(col, Comparison.like, value);
	}
	
	public static final Condition in(String col, Object... values) {
		Set<Object> set = new HashSet<Object>();
		for (Object value : values)
			set.add(value);
		return new Condition(col, Comparison.in, set);
	}
	
	public static final Condition in(String col, Collection<?> values) {
		return new Condition(col, Comparison.in, values);
	}
	
	public static final Condition nin(String col, Object... values) {
		List<Object> list = new ArrayList<Object>();
		for (Object value : values)
			list.add(value);
		return new Condition(col, Comparison.nin, list);
	}
	
	public static final Condition nin(String col, Collection<?> values) {
		return new Condition(col, Comparison.nin, values);
	}
	
	public static final Condition between(String col, Object lower, Object upper) {
		Object[] arr = new Object[] {lower, upper};
		return new Condition(col, Comparison.between, arr);
	}
}
