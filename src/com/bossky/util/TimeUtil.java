package com.bossky.util;

import java.util.Date;

/**
 * 时间工具类
 * 
 * @author bo
 *
 */
public class TimeUtil {
	/** 一天的毫秒数 */
	public final static long ONE_DAY_MILLISECOND = 24 * 3600 * 1000;
	/** 一小时的毫秒数 */
	public final static long ONE_HOUR_MILLISECOND = 3600000;

	/**
	 * ２个日期是否为同一个
	 * 
	 * @param date
	 * @param otherdate
	 * @return
	 */
	public static boolean isSameDay(Date date, Date otherdate) {
		return calDay(date, otherdate) == 0;
	}

	/**
	 * 计算２个日期相差的天数 如果 date比otherdate返回正数 date比otherdate大返回负数
	 * 
	 * @param date
	 * @param otherdate
	 * @return
	 */
	public static int calDay(Date date, Date otherdate) {
		long offset = otherdate.getTime() - date.getTime();
		return (int) (offset / ONE_DAY_MILLISECOND);
	}

}
