package com.bossky.data;

import java.util.Date;
import java.util.List;

import com.bossky.data.search.SearchCondition;

/**
 * 数据管理者
 * 
 * @author bo
 *
 */
public interface DataManager<E> {
	/**
	 * 管理者名
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 保存实体类
	 * 
	 * @param e
	 */
	public void save(E entity);

	/**
	 * 通过id获得实体类
	 * 
	 * @param id
	 */
	public E get(String id);

	/**
	 * 通过id移除实体类
	 * 
	 * @param id
	 * @return
	 */
	public E remove(String id);

	/**
	 * 移除对象类
	 * 
	 * @param e
	 */
	public boolean remove(E object);

	/**
	 * 列出id以perfix开头的实体类
	 * 
	 * @param perfix
	 *            　传null时拿全部　
	 * @return
	 */
	public List<E> list(String perfix);

	/**
	 * 搜索id>=b 并且id<=t的数据<br/>
	 * 参数为字符串，但比较的类型其实是主键的类型<br/>
	 * 即: 主键为字符串则会有 "2">"11" 主键为整数则会有 2<11
	 * 
	 * @param b
	 *            传null则不限,只有id<=t ,t也就null则拿全部　
	 * @param t
	 *            传null则不限,只有id>=b ,b也就null则拿全部　
	 * @return
	 */
	public List<E> searchRange(String b, String t);

	/**
	 * 搜索指定时间范围内类
	 * 
	 * @param start
	 *            　开始时间　
	 * @param end
	 *            　结束时间
	 * @param 时间属性名
	 *            默认为 time
	 * @return
	 */
	public List<E> searchRange(Date start, Date end, String fieldName);

	/**
	 * 条件搜索
	 * 
	 * @param conditions
	 *            条件　@see #RangeCondition and #SearchCondition
	 * @return
	 */
	public List<E> search(SearchCondition... conditions);

	/**
	 * 条件搜索
	 * 
	 * @param conditions
	 *            条件　@see #RangeCondition and #CompareCondition
	 * @return
	 */
	public List<E> search(List<SearchCondition> conditions);
}
