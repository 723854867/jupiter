package org.jupiter.mybatis.bean.enums;

public enum Dialect {

	mysql {
		@Override
		public String keyWordWrapper() {
			return "`{0}`";
		}
	},
	oracle {
		@Override
		public String keyWordWrapper() {
			return "\"{0}\"";
		}
	},
	sqlserver {
		@Override
		public String keyWordWrapper() {
			return "[{0}]";
		}
	};
	
	public abstract String keyWordWrapper();
}
