package com.bossky.util;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 平常的工具类
 * 
 * @author bo
 *
 */
public class Util {
	/** 记录日志 */
	final static Logger _Logger = LoggerFactory.getLogger(Util.class);

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
			return Short.parseShort(var);
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
			return Integer.parseInt(var);
		} catch (Throwable e) {
			_Logger.warn("将[" + var + "]转换成int时出错", e);
		}
		return i;
	}

	/**
	 * 将16进制的字符串转换成整数,转换失败返回0
	 * 
	 * @param var
	 *            要转换的字符串
	 * @return
	 */
	public static int toInt16(String var) {
		return toInt16(var, 0);
	}

	/**
	 * 将16进制的字符串转换成整数
	 * 
	 * @param var
	 *            要转换的字符串
	 * @param i
	 *            转换失败返回的默认值
	 * @return
	 */
	public static int toInt16(String var, int i) {
		if (isEmpty(var)) {
			return i;
		}
		try {
			char c1 = var.charAt(0);
			char c2 = var.charAt(1);
			if (c1 == '0' && (c2 == 'x' || c2 == 'X')) {
				var = var.substring(2);
			}
			return Integer.parseInt(var, 16);
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
			return Long.parseLong(var);
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
			return Float.parseFloat(var);
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
			return Double.parseDouble(var);
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
	 * 判断数组是否为空
	 * 
	 * @param arr
	 * @return
	 */
	public static <E> boolean isEmpty(E[] arr) {
		return null == arr ? true : arr.length == 0 ? true : false;
	}

	/**
	 * 判断集合是否为空
	 * 
	 * @param collection
	 * @return
	 */
	public static <E> boolean isEmpty(Collection<E> collection) {
		return null == collection ? true : collection.size() == 0 ? true
				: false;

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

	static MessageDigest MD5;
	static {
		try {
			MD5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {

		}
	}

	public static String MD5(String var) {
		try {
			byte[] b;
			synchronized (MD5) {
				MD5.update(var.getBytes());
				b = MD5.digest();
			}
			int i;
			StringBuilder buf = new StringBuilder("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append('0');
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (Exception e) {
			// Can't be
		}
		return null;
	}

	/**
	 * 比较两个字符串是否相等
	 * 
	 * @param var
	 * @param otherval
	 * @return
	 */
	public static boolean eq(String var, String otherval) {
		if (var == null) {
			if (otherval == null) {
				return true;
			} else {
				return false;
			}
		}
		if (otherval == null) {
			return false;
		}
		return var.equals(otherval);
	}

	/**
	 * 比较两个字符串是否相等,忽略大小写
	 * 
	 * @param var
	 * @param otherval
	 * @return
	 */
	public static boolean eqIgnoreCase(String var, String otherval) {
		if (var == null) {
			if (otherval == null) {
				return true;
			} else {
				return false;
			}
		}
		if (otherval == null) {
			return false;
		}
		return var.equalsIgnoreCase(otherval);
	}

	/**
	 * 比较两个字符串是否相等，忽略前后的空格
	 * 
	 * @param var
	 * @param otherval
	 * @return
	 */
	public static boolean eqIgnoreBlank(String var, String otherval) {
		if (var == null) {
			if (otherval == null) {
				return true;
			} else {
				return false;
			}
		}
		if (otherval == null) {
			return false;
		}
		int l1 = var.length();
		int l2 = otherval.length();
		int i1 = -1;
		int i2 = -1;
		char c1;
		char c2;
		do {
			i1++;
			c1 = var.charAt(i1);
		} while (c1 == ' ' && i1 < l1);
		do {
			i2++;
			c2 = otherval.charAt(i2);
		} while (c2 == ' ' && i2 < l2);

		while (i1 < l1 && i2 < l2) {
			if (i1 == l1) {
				while (i2 < l2) {
					c2 = otherval.charAt(i2);
					if (c2 != ' ') {
						return false;
					}
				}
			} else if (i2 == l2) {
				while (i1 < l1) {
					c1 = var.charAt(i1);
					if (c1 != ' ') {
						return false;
					}
				}
			} else {
				c1 = var.charAt(i1++);
				c2 = otherval.charAt(i2++);
				if (c1 != c2) {
					return false;
				}
			}
		}
		return true;
	}

}