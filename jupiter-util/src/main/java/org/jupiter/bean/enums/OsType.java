package org.jupiter.bean.enums;

import org.jupiter.bean.IEnum;

public enum OsType implements IEnum<Integer> {
	
	// 苹果手机操作系统
	IOS(1),
	// 其他操作系统
	OTHER(2),
	// 苹果笔记本操作系统
	MAC_OS(3),
	// 安卓操作系统
	ANDROID(4),
	// windows操作系统
	WINDOWS(5),
	// windows phone 操作系统
	WINDOWS_PHONE(6);
	
	private int mark;
	
	private OsType(int mark) {
		this.mark = mark;
	}

	@Override
	public Integer mark() {
		return this.mark;
	}
}
