package org.jupiter.util.lang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static final String EMPTY = "";

	/**
	 * 驼峰表示法
	 */
	private static final Pattern CAMEL = Pattern.compile("[A-Z]([a-z\\d]+)?");
	private static final Pattern UNDERLINE = Pattern.compile("([A-Za-z\\d]+)(_)?");
	private static final Pattern DOT = Pattern.compile("([A-Za-z\\d]+)(\\.)?");

	public static final boolean hasText(CharSequence str) {
		if (!hasLength(str))
			return false;
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i)))
				return true;
		}
		return false;
	}

	public static final boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}

	public static final String trimIncludeNull(String content) {
		return !hasText(content) ? EMPTY : content.trim();
	}

	/**
	 * 替换字符串
	 */
	public static String replace(String string, String oldPattern, String newPattern) {
		if (!hasLength(string) || !hasLength(oldPattern) || newPattern == null)
			return string;
		StringBuilder sb = new StringBuilder();
		int pos = 0;
		int index = string.indexOf(oldPattern);
		int patLen = oldPattern.length();
		while (index >= 0) {
			sb.append(string.substring(pos, index));
			sb.append(newPattern);
			pos = index + patLen;
			index = string.indexOf(oldPattern, pos);
		}
		sb.append(string.substring(pos));
		return sb.toString();
	}

	/**
	 * 驼峰转下划线
	 */
	public static final String camel2Underline(String line) {
		if (!StringUtil.hasText(line))
			return line;
		line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
		StringBuilder builder = new StringBuilder();
		Matcher matcher = CAMEL.matcher(line);
		while (matcher.find()) {
			String word = matcher.group();
			builder.append(word.toLowerCase());
			builder.append(matcher.end() == line.length() ? StringUtil.EMPTY : "_");
		}
		return builder.toString();
	}

	/**
	 * 下划线转驼峰
	 * 
	 * @param line
	 * @param smallCamel 是否是小驼峰：小驼峰首字母第一个字符小写。默认为小驼峰
	 * @return
	 */
	public static final String underline2Camel(String line, boolean... smallCamel) {
		if (!StringUtil.hasText(line))
			return line;
		StringBuilder sb = new StringBuilder();
		Matcher matcher = UNDERLINE.matcher(line);
		while (matcher.find()) {
			String word = matcher.group();
			if ((smallCamel.length == 0 || smallCamel[0]) && matcher.start() == 0)
				sb.append(Character.toLowerCase(word.charAt(0)));
			else 
				sb.append(Character.toUpperCase(word.charAt(0)));
			int index = word.lastIndexOf('_');
			sb.append(index > 0 ? word.substring(1, index).toLowerCase() : word.substring(1).toLowerCase());
		}
		return sb.toString();
	}
	
	/**
	 * 驼峰转圆点表示法
	 * 
	 * @param line
	 * @return
	 */
	public static final String camel2dot(String line) { 
		if (!StringUtil.hasText(line))
			return line;
		line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
		StringBuilder builder = new StringBuilder();
		Matcher matcher = CAMEL.matcher(line);
		while (matcher.find()) {
			String word = matcher.group();
			builder.append(word.toLowerCase());
			builder.append(matcher.end() == line.length() ? StringUtil.EMPTY : ".");
		}
		return builder.toString();
	}
	
	/**
	 * 圆点表示法转驼峰
	 * 
	 * @param line
	 * @param smallCamel 是否是小驼峰：小驼峰首字母第一个字符小写。默认为小驼峰
	 * @return
	 */
	public static final String dot2Camel(String line, boolean... smallCamel) {
		if (!StringUtil.hasText(line))
			return line;
		StringBuilder sb = new StringBuilder();
		Matcher matcher = DOT.matcher(line);
		while (matcher.find()) {
			String word = matcher.group();
			if ((smallCamel.length == 0 || smallCamel[0]) && matcher.start() == 0)
				sb.append(Character.toLowerCase(word.charAt(0)));
			else 
				sb.append(Character.toUpperCase(word.charAt(0)));
			int index = word.lastIndexOf('.');
			sb.append(index > 0 ? word.substring(1, index).toLowerCase() : word.substring(1).toLowerCase());
		}
		return sb.toString();
	}
	
	/**
	 * 脱敏
	 */
	public static final String mask(String content, int top, int tail) {
		int len = content.length() - top - tail;
		if (len <= 0)
			return content;
		StringBuilder builder = new StringBuilder();
		int index = 0;
		while (top-- > 0)
			builder.append(content.charAt(index++));
		while (len-- > 0) {
			builder.append("*");
			index++;
		}
		while (tail-- > 0)
			builder.append(content.charAt(index++));
		return builder.toString();
	}

	public static final String link(String seperator, Object... parts) {
		int len = seperator.length();
		StringBuilder builder = new StringBuilder();
		for (Object object : parts)
			builder.append(object.toString()).append(seperator);
		builder.deleteCharAt(builder.length() - len);
		return builder.toString();
	}

	/**
	 * 匹配数字或带小数的数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumberOrPoint(String str) {
		Pattern pattern = Pattern.compile("([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])");
		Matcher isNumP = pattern.matcher(str);
		if (!isNumP.matches())
			return false;
		return true;
	}
}
