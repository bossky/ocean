package com.bossky.data.jdbc.util;

public class SQLUtil {
	/**
	 * 转义特殊字符
	 * 
	 * @param val
	 * @return
	 */
	public static String escape(Object obj) {
		return escape(obj.toString());
	}

	/**
	 * 转义特殊字符
	 * 
	 * @param val
	 * @return
	 */
	public static String escape(String val) {
		if (null == val) {
			return "";
		} else {
			val = val.trim();
		}
		return val.replace("'", "''");
	}

}
