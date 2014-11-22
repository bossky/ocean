package com.bossky.data.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

import com.bossky.data.Meta;

/**
 * 基于注解的元信息
 * 
 * @author bo
 *
 */
public class AnnotionMeta implements Meta {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setValue(Object obj, Object value) {
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}
		try {
			field.set(obj, value);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public String toString() {
		return "AnnotionMeta{name=" + name + ",type=" + type + ",filed="
				+ field + ",annotations=" + Arrays.toString(annotations) + ","
				+ annotations.length + "}";
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

}
