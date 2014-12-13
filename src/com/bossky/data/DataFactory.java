package com.bossky.data;

/**
 * 数据管理者工厂　
 * 
 * @author bo
 *
 */
public interface DataFactory {
	/**
	 * 获取数据管理者
	 * 
	 * @param name
	 * @return
	 */
	public <E> DataManager<E> get(String name);

	/**
	 * 创建数据管理者
	 * 
	 * @param clazz
	 *            　数据管理类
	 * @param objs
	 *            类默认的构造对象
	 * @return
	 */
	public <E> DataManager<E> createDataManage(Class<E> clazz, Object... objs);

	/**
	 * 创建数据管理者
	 * 
	 * @param mapper
	 * @return
	 */
	public <E> DataManager<E> createDataManage(Mapper<E> mapper);

}
