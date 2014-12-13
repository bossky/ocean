package com.bossky.data;

import java.util.Collection;

/**
 * 对象映射类
 * 
 * @author bo
 *
 */
public interface Mapper<E> {
	/**
	 * 获得一个类对象,获取失败返回null
	 * 
	 * @return
	 */
	public E getInsantce();

	/**
	 * 获取名称
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 获得主键元素　
	 * 
	 * @return
	 */
	public Meta getKey();

	/**
	 * 类的元信息
	 * 
	 * @return
	 */
	public Collection<Meta> metas();

	/**
	 * 根据属性名获得属性的元信息
	 * 
	 * @param fieldName
	 * @return
	 */
	public Meta getMeta(String fieldName);

}
