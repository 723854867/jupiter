package org.jupiter.bean.model.query;

/**
 * 比较符
 */
public enum Comparison {

	// 小于
	lt(1),
	// 小于等于
	lte(2),
	// 大于
	gt(3),
	// 大于等于
	gte(4),
	// 等于
	eq(5),
	// 不等于
	neq(6),
	// like 运算
	like(7),
	// in
	in(8),
	// not in
	nin(9),
	isnull(10),
	notnull(11),
	// 区间
	between(12);
	
	private int mark;
	
	private Comparison(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
	
	public static final Comparison match(int type) {
		for (Comparison temp : Comparison.values()) {
			if (temp.mark == type)
				return temp;
		}
		return null;
	}
}
