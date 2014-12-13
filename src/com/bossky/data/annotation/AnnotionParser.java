package com.bossky.data.annotation;

import java.lang.reflect.Field;

import javax.annotation.Resource;

/**
 * 注解解析器
 * 
 * @author bo
 *
 */
public class AnnotionParser {
	/**
	 * 类是否为子类
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isSubClass(Class<?> clazz) {
		return !clazz.isAnnotationPresent(BanRecursive.class)
				&& clazz.getSuperclass() != null;
	}

	/**
	 * 属性是否为id
	 * 
	 * @param field
	 * @return
	 */
	public static boolean isId(Field field) {
		return field.isAnnotationPresent(Id.class);
	}

	/**
	 * 属性是否为可用资源
	 * 
	 * @param field
	 * @return
	 */
	public static boolean isResource(Field field) {
		return field.isAnnotationPresent(Resource.class);
	}
}
