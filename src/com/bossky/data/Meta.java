package com.bossky.data;

import java.lang.annotation.Annotation;

/**
 * 属性映射类
 * 
 * @author bo
 *
 */
public interface Meta {
	/**
	 * 名称
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 类型
	 * 
	 * @return
	 */
	public String getType();

	/**
	 * 获对象的属性值
	 * 
	 * @param obj
	 * @return
	 */
	public Object getValue(Object obj);

	/**
	 * 设置对象的属性值
	 * 
	 * @param obj
	 * @param value
	 * @return 返回是否设置成功
	 */
	public boolean setValue(Object obj, Object value);

	/**
	 * 是否包括注释
	 * 
	 * @param annotationClass
	 * @return
	 */
	public boolean isAnnotationPresent(
			Class<? extends Annotation> annotationClass);

}
