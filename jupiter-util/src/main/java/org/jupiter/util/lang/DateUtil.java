package org.jupiter.util.lang;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
	
	public static final String yyyyMMddHHmmss			= "yyyyMMddHHmmss";
	public static final String YYYY_MM_DD_HH_MM_SS		= "yyyy-MM-dd HH:mm:ss";
	
	public static final TimeZone TIMEZONE_GMT_8			= TimeZone.getTimeZone("GMT+8:00");
	
	// *************************** 获取时间戳  *************************** 
	
	/**
	 * 获取系统当前时间戳(秒)
	 */
	public static int current() {
		return (int) (System.currentTimeMillis() / 1000);
	}
	
	/**
	 * 使用默认时区GMT+8:00将日期转换为时间戳(毫秒)
	 */
	public static final long getTime(Object date, String format) { 
		return getTime(date, format, TIMEZONE_GMT_8);
	}
	
	/**
	 * 使用指定时区将日期转换为时间戳(毫秒)
	 */
	public static final long getTime(Object date, String format, TimeZone zone) { 
		DateFormat df = new SimpleDateFormat(format);
		df.setTimeZone(zone);
		try {
			return df.parse(date.toString()).getTime();
		} catch (ParseException e) {
			return 0;
		}
	}
	
	// *************************** 时间格式化  *************************** 
	
	/**
	 * 使用默认时区GMT+8:00获取指定时间格式的系统当前时间
	 */
	public static final String getDate(String format) {
		return getDate(format, System.currentTimeMillis());
	}
	
	/**
	 * 使用默认时区GMT+8:00将时间转换为指定的时间格式
	 */
	public static final String getDate(Date date, String format) {
		return getDate(date, format, TIMEZONE_GMT_8);
	}
	
	/**
	 * 使用默认时区GMT+8:00将unix时间戳转换为指定时间格式的时间
	 */
	public static final String getDate(String format, long timestamp) {
		return getDate(format, timestamp, TIMEZONE_GMT_8);
	}
	
	/**
	 * 使用指定时区将时间转换为指定的时间格式
	 */
	public static final String getDate(Date date, String format, TimeZone timeZone) { 
		DateFormat df = new SimpleDateFormat(format);
		df.setTimeZone(timeZone);
		return df.format(date);
	}
	
	/**
	 * 使用指定时区将unix时间戳转换为指定时间格式的时间
	 */
	public static final String getDate(String format, long timestamp, TimeZone timeZone) {
		DateFormat df = new SimpleDateFormat(format.toString());
		df.setTimeZone(timeZone);
		return df.format(new Date(timestamp));
	}
	
	/**
	 * 使用默认时区GMT+8:00将 format 格式的时间 date转换为toFormat时间格式的时间
	 */
	public static final String getDate(String date, String format, String toFormat) {
		return getDate(date, format, toFormat, TIMEZONE_GMT_8);
	}
	
	/**
	 * 使用指定时区将 format 格式的时间 date转换为toFormat时间格式的时间
	 */
	public static final String getDate(String date, String format, String toFormat, TimeZone timeZone) {
		long time = getTime(date, format, timeZone);
		return getDate(toFormat, time);
	}
}
