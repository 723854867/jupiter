package org.jupiter.util.lang;

import org.jupiter.bean.IEnum;

public class EnumUtil {
	
	public static <MARK, E extends Enum<?> & IEnum<MARK>> E valueOf(Class<E> enumClass, Object code) {
		E[] enumConstants = enumClass.getEnumConstants();
		for (E e : enumConstants) {
			String mark = e.mark().toString();
			if (code.toString().equals(mark))
				return e;
		}
		return null;
	}
}
