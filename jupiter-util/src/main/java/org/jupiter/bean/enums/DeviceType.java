package org.jupiter.bean.enums;

import org.jupiter.bean.IEnum;

public enum DeviceType implements IEnum<Integer> {
	
	// 其他
	OTHER(1),
	// 平板
	TABLET(2),
	// 手机
	MOBILE(3),
	// 电脑
	COMPUTER(4);
	
	private int mark;
	
	private DeviceType(int mark) {
		this.mark = mark;
	}

	@Override
	public Integer mark() {
		return this.mark;
	}
}
