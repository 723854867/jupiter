package org.jupiter.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpUtil {

	private static Pattern pattern = Pattern.compile("^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$");

	public static boolean isIp(String input) {
		if (null == input)
			return false;
		Matcher m = pattern.matcher(input);
		return m.matches();
	}
	
	/**
	 * 将ip转换为int类型
	 * 
	 * @param strIp
	 * @return
	 */
	public static long ipToLong(String strIp) {
		long[] ip = new long[4];
		int position1 = strIp.indexOf(".");
		int position2 = strIp.indexOf(".", position1 + 1);
		int position3 = strIp.indexOf(".", position2 + 1);
		ip[0] = Long.parseLong(strIp.substring(0, position1));
		ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strIp.substring(position3 + 1));
		return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
	}

	/**
	 * int类型ip转为string类型
	 * 
	 * @param longIp
	 * @return
	 */
	public static String longToIP(long longIp) {
		StringBuilder sb = new StringBuilder("");
		sb.append(String.valueOf((longIp >>> 24)));
		sb.append(".");
		sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
		sb.append(".");
		sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
		sb.append(".");
		sb.append(String.valueOf((longIp & 0x000000FF)));
		return sb.toString();
	}
}
