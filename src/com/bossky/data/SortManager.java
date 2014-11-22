package com.bossky.data;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.bossky.data.search.CompareCondition;
import com.bossky.data.search.SortType;

/**
 * 支持排序的实体管理者　
 * 
 * @author bo
 *
 */
public interface SortManager<E> extends DataManager<E> {
	/**
	 * 列出id以perfix开头的实现类
	 * 
	 * @param perfix
	 *            传null时拿全部　
	 * @param sorts
	 *            排序
	 * @return
	 */
	public List<E> list(String perfix, SortType... sorts);

	/**
	 * 搜索id>=b 并且id<=t的数据
	 * 
	 * @param b
	 *            传null则不限,只有id<=t ,t也就null则拿全部　
	 * @param t
	 *            传null则不限,只有id>=b ,b也就null则拿全部　
	 * @param sorts
	 *            排序方式
	 * @return
	 */
	public List<E> searchRange(String b, String t, SortType... sorts);

	/**
	 * 搜索指定时间范围内更新过的实体类
	 * 
	 * @param start
	 *            　开始时间　
	 * @param end
	 *            　结束时间
	 * @param sorts
	 *            排序方式
	 * @return
	 */
	public List<E> searchUpdateTimeRange(Date start, Date end,
			SortType... sorts);

	/**
	 * 搜索指定时间范围内创建的实体类
	 * 
	 * @param start
	 *            开始时间　
	 * @param end
	 *            　结束时间　
	 * @param sorts
	 *            排序方式
	 * @return
	 */
	public List<E> searchCreateTimeRange(Date start, Date end,
			SortType... sorts);

	/**
	 * 条件搜索
	 * 
	 * @param conditions
	 *            条件　@see #RangeCondition and #CompareCondition
	 * @param sorts
	 *            排序方式
	 * @return
	 */
	public List<E> search(Collection<CompareCondition> conditions,
			SortType... sorts);
}
