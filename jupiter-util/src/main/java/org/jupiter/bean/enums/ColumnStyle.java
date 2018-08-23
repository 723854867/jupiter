package org.jupiter.bean.enums;

import org.jupiter.util.lang.StringUtil;

public enum ColumnStyle {

	// 原值
	normal,
	camel2dot {
		@Override
		public String convert(String value) {
			return StringUtil.camel2dot(value);
		}
	},
	// 驼峰转下划线
	camel2underline {
		@Override
		public String convert(String value) {
			return StringUtil.camel2Underline(value);
		}
	},
	// 转换为大写
	uppercase {
		@Override
		public String convert(String value) {
			return value.toUpperCase();
		}
	},
	// 转换为小写
	lowercase {
		@Override
		public String convert(String value) {
			return value.toLowerCase();
		}
	},
	// 驼峰转下划线大写形式
	camel2underlineAndUppercase {
		@Override
		public String convert(String value) {
			return StringUtil.camel2Underline(value).toUpperCase();
		}
	},
	// 驼峰转下划线小写形式
	camel2underlineAndLowercase {
		@Override
		public String convert(String value) {
			return StringUtil.camel2Underline(value).toLowerCase();
		}
	};
	
	public String convert(String value) {
		return value;
	}
}
