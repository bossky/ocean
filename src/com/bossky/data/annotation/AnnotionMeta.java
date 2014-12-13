package com.bossky.data.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bossky.data.Meta;
import com.bossky.util.Util;

/**
 * 基于注解的元信息
 * 
 * @author bo
 *
 */
public class AnnotionMeta implements Meta {
	Logger _Logger = LoggerFactory.getLogger(AnnotionMeta.class);
	/** 名称 */
	protected String name;
	/** 类型 */
	protected String type;
	/** 属性 */
	protected Field field;
	/** 注释 */
	protected Annotation[] annotations;

	protected AnnotionMeta(String name, String type, Field field,
			Annotation[] annotations) {
		this.name = name;
		this.type = type;
		this.field = field;
		this.annotations = annotations;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public Object getValue(Object obj) {
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}
		try {
			return field.get(obj);
		} catch (IllegalArgumentException e) {
			_Logger.warn("获取对象" + obj + "的" + field.getName() + "出错");
		} catch (IllegalAccessException e) {
			_Logger.warn("获取对象" + obj + "的" + field.getName() + "出错");
		}
		return null;
	}

	@Override
	public boolean setValue(Object obj, Object value) {
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}
		try {
			field.set(obj, value);
			return true;
		} catch (IllegalArgumentException e) {
			_Logger.warn("设置对象" + obj + "的" + field.getName() + "出错");
		} catch (IllegalAccessException e) {
			_Logger.warn("设置对象" + obj + "的" + field.getName() + "出错");
		}
		return false;

	}

	public static AnnotionMeta valueOf(Field f) {
		String name = f.getName();
		String type = f.getType().getSimpleName();
		return new AnnotionMeta(name, type, f, f.getAnnotations());
	}

	@Override
	public boolean isAnnotationPresent(
			Class<? extends Annotation> annotationClass) {
		for (Annotation a : annotations) {
			if (a.annotationType().equals(annotationClass)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Meta) {
			Meta m = (Meta) obj;
			return Util.eq(m.getName(), name) && Util.eq(m.getType(), type);
		}
		return false;
	}

	@Override
	public String toString() {
		return "AnnotionMeta{name=" + name + ",type=" + type + ",filed="
				+ field + ",annotations=" + Arrays.toString(annotations) + ","
				+ annotations.length + "}";
	}

}
