package org.jupiter.bean.enums;

import org.jupiter.bean.IEnum;

public enum ClientType implements IEnum<Integer> {

	// 自身sdk调用
	SDK(1),
	// 其他
	OTHER(2),
	// 浏览器客户端
	BROWSER(3),
	// 原生客户端
	ORIGINAL(4);
	
	private int mark;
	
	private ClientType(int mark) {
		this.mark = mark;
	}

	@Override
	public Integer mark() {
		return mark;
	}
}
