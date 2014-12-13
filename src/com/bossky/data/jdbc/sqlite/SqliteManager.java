package com.bossky.data.jdbc.sqlite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bossky.data.DataManager;
import com.bossky.data.Mapper;
import com.bossky.data.Meta;
import com.bossky.data.annotation.Id;
import com.bossky.data.jdbc.util.SQLUtil;
import com.bossky.data.search.CompareCondition;
import com.bossky.data.search.SearchCondition;

/**
 * sqlite管理者
 * 
 * @author bo
 *
 * @param <E>
 */
public class SqliteManager<E> implements DataManager<E> {
	final static Logger _Logger = LoggerFactory.getLogger(SqliteManager.class);

	SqliteFactory factory;
	/** 管理者名称 */
	protected String name;
	/** 类的mapper */
	protected Mapper<E> mapper;

	protected SqliteManager(SqliteFactory factory, Mapper<E> mapper) {
		this(factory, mapper.getName(), mapper);
	}

	public SqliteManager(SqliteFactory factory, String name, Mapper<E> mapper) {
		this.name = name;
		this.mapper = mapper;
		this.factory = factory;
		init();
	}

	/**
	 * 初始化表
	 */
	protected void init() {
		Connection cc = null;
		try {
			cc = factory.connect();
			String sql = getCreateTableSql(name);
			executeUpdate(sql);
			_Logger.info("成功注册表" + name);
		} catch (SQLException e) {
			_Logger.error("注册" + name + "出错", e);
		} finally {
			factory.close(cc);
		}

	}

	/**
	 * 创建表语句
	 * 
	 * @param tablename
	 * @param mapper
	 * @return
	 */
	protected String getCreateTableSql(String tablename) {
		StringBuilder sb = new StringBuilder();
		sb.append("create table  if not exists `").append(tablename)
				.append("` (");
		Collection<Meta> col = mapper.metas();
		Iterator<Meta> it = col.iterator();
		String key = null;
		while (it.hasNext()) {
			Meta m = it.next();
			if (m.isAnnotationPresent(Id.class)) {
				if (key != null) {
					throw new IllegalArgumentException("一个实体类只能有一个主键");
				}
				key = m.getName();
				continue;
			}
			sb.append(" `").append(m.getName()).append("` ");
			sb.append(m.getType()).append(" ");
			sb.append(" , ");
		}
		sb.append(" `").append(key).append("` ");
		sb.append("String").append(" ");
		if (null == key) {
			throw new IllegalArgumentException("一个实体类必须要有一个主键");
		}
		sb.append(",primary key(`").append(key).append("`)");
		sb.append(");");
		return sb.toString();
	}

	/**
	 * 删除表语句　
	 * 
	 * @param tablename
	 * @return
	 */
	protected String getDropTableSql() {
		return "drop table " + name + " ;";
	}

	/**
	 * 执行更新语句
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	protected int executeUpdate(String sql) throws SQLException {
		Connection cc = null;
		try {
			cc = factory.connect();
			Statement st;
			st = cc.createStatement();
			int result = st.executeUpdate(sql);
			return result;
		} finally {
			factory.close(cc);
		}
	}

	/**
	 * 执行查询语句
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	protected ResultSet executeQuery(Connection cc, String sql)
			throws SQLException {
		Statement st;
		st = cc.createStatement();
		return st.executeQuery(sql);
	}

	/** 格式化日期 */
	SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/** 格式化日期为　yyyy-MM-dd HH:mm:ss格式 */
	String format(Date date) {
		synchronized (FORMAT) {
			return FORMAT.format(date);
		}
	}

	/** 转换字符串yyyy-MM-dd HH:mm:ss格式,为日期 */
	Date parse(String date) {
		synchronized (FORMAT) {
			try {
				return FORMAT.parse(date);
			} catch (ParseException e) {
				return null;
			}
		}
	}

	/**
	 * 插入语句
	 * 
	 * @param tablename
	 * @param mapper
	 * @return
	 */
	protected String getInsertSql(Object obj) {
		StringBuilder sb = new StringBuilder();
		sb.append("insert into ").append(name).append("(");
		Collection<Meta> col = mapper.metas();
		Iterator<Meta> it = col.iterator();
		while (it.hasNext()) {
			Meta m = it.next();
			sb.append(m.getName()).append(" ");
			if (it.hasNext()) {
				sb.append(" , ");
			}
		}
		sb.append(") ");
		it = col.iterator();
		sb.append("values(");
		while (it.hasNext()) {
			Meta m = it.next();
			Object value = m.getValue(obj);
			if (value instanceof Date) {
				Date date = (Date) value;
				value = format(date);// 格式化日期
			}
			sb.append("'").append(SQLUtil.escape(value)).append("'");
			if (it.hasNext()) {
				sb.append(" , ");
			}
		}
		sb.append(");");
		return sb.toString();
	}

	/**
	 * 更新语句
	 * 
	 * @param obj
	 * @return
	 */
	protected String getUpdateSql(Object obj) {
		StringBuilder sb = new StringBuilder();
		sb.append("update  ").append(name).append(" set  ");
		Collection<Meta> col = mapper.metas();
		Iterator<Meta> it = col.iterator();
		Meta key = mapper.getKey();
		Object keyval = null;
		while (it.hasNext()) {
			Meta m = it.next();
			String value = getValue(m, obj);
			if (m.getName().equals(key.getName())) {
				keyval = value;
				continue;
			}
			sb.append("`");
			sb.append(m.getName()).append("`=' ");
			sb.append(SQLUtil.escape(value)).append("'");
			if (it.hasNext()) {
				sb.append(" , ");
			}
		}
		sb.append("  where `").append(key.getName());
		sb.append("`='").append(keyval).append("'");
		return sb.toString();
	}

	/**
	 * 获取对象值
	 * 
	 * @param m
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getValue(Meta m, Object obj) {
		Object o = m.getValue(obj);
		if (null == o) {
			return null;
		}
		String s;
		if (o instanceof Date) {
			Date date = (Date) o;
			s = format(date);// 格式化日期
		} else if (o instanceof List) {
			StringBuilder sb = new StringBuilder();
			List<Object> list = (List<Object>) o;
			Iterator<Object> it = list.iterator();
			while (it.hasNext()) {
				sb.append(it.next());
				if (it.hasNext()) {
					sb.append(";");
				}
			}
			s = sb.toString();
		} else {
			s = o.toString();
		}
		return s;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void save(E objcet) {
		String sql = getInsertSql(objcet);
		try {
			executeUpdate(sql);
		} catch (SQLException e) {
			sql = getUpdateSql(objcet);
			try {
				executeUpdate(sql);
			} catch (SQLException ee) {
				_Logger.error("执行" + sql + "出错", ee);
			}
		}
	}

	@Override
	public E get(String id) {
		Meta key = mapper.getKey();
		String sql = "select * from " + name + " where `" + key.getName()
				+ "`='" + SQLUtil.escape(id) + "';";
		Connection cc = null;
		try {
			cc = factory.connect();
			ResultSet rs = executeQuery(cc, sql);
			if (rs.next()) {
				E e = mapper.getInsantce();
				for (Meta m : mapper.metas()) {
					Object o = getObject(rs, m);
					m.setValue(e, o);
				}
				return e;
			}
			return null;
		} catch (SQLException e) {
			_Logger.error("执行" + sql + "出错", e);
		} finally {
			factory.close(cc);
		}
		return null;
	}

	private Object getObject(ResultSet rs, Meta m) throws SQLException {
		String name = m.getName();
		String type = m.getType();
		if (type.equals(String.class.getSimpleName())) {
			return rs.getString(name);
		} else if (type.equals(Date.class.getSimpleName())) {
			String date = rs.getString(name);
			return parse(date);
		} else if (type.equals(Long.class.getSimpleName())
				|| type.equals(long.class.getSimpleName())) {
			return rs.getLong(name);
		} else if (type.equals(Integer.class.getSimpleName())
				|| type.equals(int.class.getSimpleName())) {
			return rs.getInt(name);
		} else if (type.equals(Short.class.getSimpleName())
				|| type.equals(short.class.getSimpleName())) {
			return rs.getShort(name);
		} else if (type.equals(char.class.getSimpleName())
				|| type.equals(Character.class.getSimpleName())) {
			// TODO
		} else if (type.equals(List.class.getSimpleName())) {
			String s = rs.getString(name);
			if (null == s) {
				return null;
			}
			String arr[] = s.split(";");
			return Arrays.asList(arr);
		}
		return rs.getObject(name);
	}

	@Override
	public E remove(String id) {
		Meta key = mapper.getKey();
		String sql = "select * from " + name + " where `" + key.getName()
				+ "`='" + SQLUtil.escape(id) + "'";
		Connection cc = null;
		try {
			cc = factory.connect();
			ResultSet rs = executeQuery(cc, sql);
			E e = null;
			if (rs.next()) {
				e = mapper.getInsantce();
				for (Meta m : mapper.metas()) {
					Object o = getObject(rs, m);
					m.setValue(e, o);
				}
				sql = "delete from " + name + " where `" + key.getName() + "`="
						+ id;
				executeUpdate(sql);
			}
			return e;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(cc);
		}
		return null;
	}

	@Override
	public List<E> list(String perfix) {
		Meta key = mapper.getKey();
		String sql = "select * from "
				+ name
				+ (null == perfix ? " " : " where `" + key.getName()
						+ "` like '" + SQLUtil.escape(perfix) + "%' ");
		Connection cc = null;
		try {
			cc = factory.connect();
			ResultSet rs = executeQuery(cc, sql);
			List<E> list = new ArrayList<E>();
			while (rs.next()) {
				E e = mapper.getInsantce();
				for (Meta m : mapper.metas()) {
					Object o = getObject(rs, m);
					m.setValue(e, o);
				}
				list.add(e);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(cc);
		}
		return Collections.emptyList();
	}

	@Override
	public List<E> searchRange(String b, String t) {
		Meta key = mapper.getKey();
		return search(b, t, key.getName());
	}

	@Override
	public List<E> searchRange(Date start, Date end, String fieldName) {
		String b = start == null ? null : format(start);
		String t = end == null ? null : format(end);
		return search(b, t, fieldName);
	}

	/**
	 * 范围搜索
	 * 
	 * @param b
	 * @param t
	 * @param fieldName
	 * @return
	 */
	public List<E> search(String b, String t, String fieldName) {
		String sql;
		if (null != b && null != t) {
			sql = "select * from " + name + " where `" + fieldName + "`>='" + b
					+ "' and `" + fieldName + "`<='" + t + "'";
		} else if (null == b) {
			sql = "select * from " + name + " where `" + fieldName + "`<='" + t
					+ "'";
		} else if (null == t) {
			sql = "select * from " + name + " where `" + fieldName + "`>='" + b
					+ "' ";
		} else {
			sql = "select * from " + name;
		}
		Connection cc = null;
		try {
			cc = factory.connect();
			ResultSet rs = executeQuery(cc, sql);
			List<E> list = new ArrayList<E>();
			while (rs.next()) {
				E e = mapper.getInsantce();
				for (Meta m : mapper.metas()) {
					Object o = getObject(rs, m);
					m.setValue(e, o);
				}
				list.add(e);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close(cc);
		}
		return Collections.emptyList();
	}

	@Override
	public List<E> search(SearchCondition... conditions) {
		return s(Arrays.asList(conditions));
	}

	@Override
	public List<E> search(List<SearchCondition> conditions) {
		return s(conditions);
	}

	public List<E> s(Iterable<SearchCondition> iterable) {
		Iterator<SearchCondition> it = iterable.iterator();
		StringBuilder sb = new StringBuilder();
		sb.append("select * from " + name);
		if (it.hasNext()) {
			sb.append(" where ");
		}
		while (it.hasNext()) {
			SearchCondition next = it.next();
			if (next instanceof CompareCondition) {
				CompareCondition s = (CompareCondition) next;
				sb.append('`').append(s.getFieldName()).append('`');
				Object value = SQLUtil.escape(s.getValue());
				if (s.getOption() == CompareCondition.COMP_OPTION_EQUALS) {
					sb.append("='").append(value).append("'");
				} else if (s.getOption() == CompareCondition.COMP_OPTION_NO_EQUALS) {
					sb.append("!='").append(value).append("'");
				} else if (s.getOption() == CompareCondition.COMP_OPTION_GREATER) {
					sb.append(">'").append(value).append("'");
				} else if (s.getOption() == CompareCondition.COMP_OPTION_LESS) {
					sb.append("<'").append(value).append("'");
				} else if (s.getOption() == (CompareCondition.COMP_OPTION_GREATER & CompareCondition.COMP_OPTION_EQUALS)) {
					sb.append(">='").append(value).append("'");
				} else if (s.getOption() == (CompareCondition.COMP_OPTION_LESS & CompareCondition.COMP_OPTION_EQUALS)) {
					sb.append("<='").append(value).append("'");
				} else if (s.getOption() == CompareCondition.COMP_OPTION_STARTWITH) {
					sb.append(" like '").append(value).append("%'");
				} else if (s.getOption() == CompareCondition.COMP_OPTION_ENDWITH) {
					sb.append(" like '%").append(value).append("'");
				} else if (s.getOption() == CompareCondition.COMP_OPTION_LIKE) {
					sb.append(" like '%").append(value).append("%'");
				} else {
					throw new IllegalArgumentException("不支持的筛选方式"
							+ s.getOption());
				}
				if (it.hasNext()) {
					sb.append(" and ");
				}
			}
			String sql = sb.toString();
			Connection cc = null;
			try {
				cc = factory.connect();
				ResultSet rs = executeQuery(cc, sql);
				List<E> list = new ArrayList<E>();
				while (rs.next()) {
					E e = mapper.getInsantce();
					for (Meta m : mapper.metas()) {
						Object o = getObject(rs, m);
						m.setValue(e, o);
					}
					list.add(e);
				}
				return list;
			} catch (Exception e) {
				_Logger.error("执行" + sql + "出错", e);
			} finally {
				factory.close(cc);
			}
		}
		return Collections.emptyList();
	}

	@Override
	public boolean remove(E object) {
		Meta key = mapper.getKey();
		Object id = key.getValue(object);
		String sql = "delete from " + name + " where `" + key.getName() + "`="
				+ id;
		try {
			return executeUpdate(sql) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
