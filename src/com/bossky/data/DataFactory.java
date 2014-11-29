package com.bossky.data;

import com.bossky.data.business.Di;
import com.bossky.data.jdbc.sqlite.SqliteManager;

/**
 * 数据管理者工厂　
 * 
 * @author bo
 *
 */
public interface DataFactory {
	/**
	 * 创建数据管理者
	 * 
	 * @param clazz
	 * @return
	 */
	public <E> SqliteManager<E> createDataManage(Class<E> clazz);

	/**
	 * 创建数据管理者
	 * 
	 * @param clazz
	 * @param di
	 * @return
	 */
	public <E> SqliteManager<E> createDataManage(Class<E> clazz, Di di);

	/**
	 * 获取数据库者
	 * 
	 * @param name
	 * @return
	 */
	public <E> DataManager<E> get(String name);
}
