package com.bossky.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 平常的工具类
 * 
 * @author bo
 *
 */
public class CommonUtil {
	/** 记录日志 */
	final static Logger _Logger = LoggerFactory.getLogger(CommonUtil.class);

	/**
	 * 将字串转换成short，转换失败返回0
	 * 
	 * @param var
	 * @return
	 */
	public static short toShort(String var) {
		return toShort(var, (short) 0);
	}

	/**
	 * 将字串转换成short，转换失败返回i
	 * 
	 * @param var
	 * @param i
	 * @return
	 */
	public static short toShort(String var, short i) {
		if (isEmpty(var)) {
			return i;
		}
		try {
			Short.parseShort(var);
		} catch (Exception e) {
			_Logger.warn("将[" + var + "]转换成short时出错", e);
		}
		return i;
	}

	/**
	 * 将字符串转换成整数,转换失败返回0
	 * 
	 * @param var
	 *            要转换的字符串
	 * @return
	 */
	public static int toInt(String var) {
		return toInt(var, 0);
	}

	/**
	 * 将字符串转换成整数
	 * 
	 * @param var
	 *            要转换的字符串
	 * @param i
	 *            转换失败返回的默认值
	 * @return
	 */
	public static int toInt(String var, int i) {
		if (isEmpty(var)) {
			return i;
		}
		try {
			Integer.parseInt(var);
		} catch (Throwable e) {
			_Logger.warn("将[" + var + "]转换成int时出错", e);
		}
		return i;
	}

	/**
	 * 将字符串转换成长整数,转换失败返回0
	 * 
	 * @param var
	 *            要转换的字符串
	 * @return
	 */
	public static long toLong(String var) {
		return toLong(var, 0);
	}

	/**
	 * 将字符串转换成长整数
	 * 
	 * @param var
	 *            要转换的字符串
	 * @param i
	 *            转换失败返回的默认值
	 * @return
	 */
	public static long toLong(String var, long i) {
		if (isEmpty(var)) {
			return i;
		}
		try {
			Long.parseLong(var);
		} catch (Throwable e) {
			_Logger.warn("将[" + var + "]转换成long时出错", e);
		}
		return i;
	}

	/**
	 * 将字符串转换成float,转换失败返回的0
	 * 
	 * @param var
	 *            要转换的字符串
	 * @return
	 */
	public static float toFloat(String var) {
		return toFloat(var, 0);
	}

	/**
	 * 将字符串转换成float,转换失败返回的i
	 * 
	 * @param var
	 *            要转换的字符串
	 * @param i
	 *            转换失败返回的默认值
	 * @return
	 */
	public static float toFloat(String var, float i) {
		if (isEmpty(var)) {
			return i;
		}
		try {
			Float.parseFloat(var);
		} catch (Throwable e) {
			_Logger.warn("将[" + var + "]转换成float时出错", e);
		}
		return i;
	}

	/**
	 * 将字符串转换成double,转换失败返回的0
	 * 
	 * @param var
	 *            要转换的字符串
	 * @return
	 */
	public static double toDouble(String var) {
		return toDouble(var, 0);
	}

	/**
	 * 将字符串转换成double,转换失败返回的i
	 * 
	 * @param var
	 *            要转换的字符串
	 * @param i
	 *            转换失败返回的默认值
	 * @return
	 */
	public static double toDouble(String var, double i) {
		if (isEmpty(var)) {
			return i;
		}
		try {
			Double.parseDouble(var);
		} catch (Throwable e) {
			_Logger.warn("将[" + var + "]转换成double时出错", e);
		}
		return i;
	}

	/**
	 * 将日期转换成yyyy-MM-dd HH:mm:ss格式的字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String toString(Date date) {
		synchronized (FORMAT) {
			return FORMAT.format(date);
		}
	}

	/**
	 * 将日期转换成format格式的字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String toString(Date date, String format) {
		SimpleDateFormat p = new SimpleDateFormat(format);
		String v = p.format(date);
		p = null;// gc
		return v;
	}

	/**
	 * 将对象转换成字符串,对象为null时返回""
	 * 
	 * @param var
	 * @return
	 */
	public static String toString(Object var) {
		return toString(var, "");
	}

	/**
	 * 将对象转换成字符串,对象为null时返回defaultvar
	 * 
	 * @param var
	 * @param defaultvar
	 * @return
	 */
	public static String toString(Object var, String defaultvar) {
		return var == null ? defaultvar : var.toString().trim();
	}

	static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 将yyyy-MM-dd HH:mm:ss的字符串转换成日期对象
	 * 
	 * @param var
	 * @return
	 */
	public static Date parseDate(String var) {
		try {
			synchronized (FORMAT) {
				return FORMAT.parse(var);
			}
		} catch (Exception e) {
			_Logger.warn("将[" + var + "]转换成日期时出错", e);
		}
		return null;
	}

	/**
	 * 将format格式的日期的字符串var转换成日期对象
	 * 
	 * @param var
	 * @param format
	 * @return
	 */
	public static Date parseDate(String var, String format) {
		SimpleDateFormat p = null;
		try {
			p = new SimpleDateFormat(format);
			return p.parse(var);
		} catch (Exception e) {
			_Logger.warn("将[" + var + "]转换成日期时出错", e);
		} finally {
			if (null != p) {
				p = null;// gc
			}
		}
		return null;
	}

	/**
	 * 是字符是否为空串，全空格字串符也算空串
	 * 
	 * @param var
	 * @return
	 */
	public static boolean isEmpty(String var) {
		return var == null ? true : var.trim().length() == 0 ? true : false;
	}

	/**
	 * 是否为数字组成的字符串
	 * 
	 * @param var
	 * @return
	 */
	public static boolean isNumber(String var) {
		// 0-9对应的码 48 49 50 51 52 53 54 55 56 57
		for (int i = 0; i < var.length(); i++) {
			int v = var.charAt(i);
			if (v < 48 || v > 57) {
				return false;
			}
		}
		return true;
	}

}