package org.jupiter.bean.enums;

import org.jupiter.bean.IEnum;

/**
 * 用户名类型
 * 
 * @author lynn
 */
public enum UsernameType implements IEnum<Integer> {

	// 普通用户名
	COMMON(1),
	// 邮箱用户名
	EMAIL(2),
	// 手机号
	MOBILE(3);
	
	private int mark;
	
	private UsernameType(int mark) {
		this.mark = mark;
	}
	
	@Override
	public Integer mark() {
		return mark;
	}
}
