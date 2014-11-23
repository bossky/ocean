package com.bossky.data.jdbc.util;

public class SQLUtil {
	/**
	 * 转义特殊字符
	 * 
	 * @param val
	 * @return
	 */
	public static String escape(Object obj) {
		if (null == obj) {
			return null;
		}
		String val = obj.toString();
		return val.replace("'", "''");
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

	public static void main(String[] args) {
		String sql = " hello'  dfdf";
		System.out.println(escape(sql));
	}
}
