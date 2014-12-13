package com.bossky.data.jdbc.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bossky.data.DataFactory;
import com.bossky.data.DataManager;
import com.bossky.data.Mapper;
import com.bossky.data.annotation.AnnotionMapper;

/**
 * sqlite工厂
 * 
 * @author bo
 *
 */
@SuppressWarnings("unchecked")
public class SqliteFactory implements DataFactory {
	final static Logger _Logger = LoggerFactory.getLogger(SqliteFactory.class);
	Map<String, DataManager<? extends Object>> map = new HashMap<String, DataManager<? extends Object>>();
	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			_Logger.error("加载类org.sqlite.JDBC出错", e);
		}
	}
	private String url;

	public SqliteFactory(String url) {
		// url="/home/bo/test.db"
		this.url = url;
	}

	@Override
	public synchronized <E> SqliteManager<E> createDataManage(Class<E> clazz,
			Object... objs) {
		SqliteManager<E> m;
		m = (SqliteManager<E>) map.get(clazz.getSimpleName());
		if (m != null) {
			return m;
		}
		AnnotionMapper<E> mapper = AnnotionMapper.valueOf(clazz, objs);
		m = new SqliteManager<E>(this, mapper);
		map.put(clazz.getSimpleName(), m);
		return m;
	}

	@Override
	public synchronized <E> SqliteManager<E> createDataManage(Mapper<E> mapper) {
		SqliteManager<E> m;
		m = (SqliteManager<E>) map.get(mapper.getName());
		if (m != null) {
			return m;
		}
		m = new SqliteManager<E>(this, mapper);
		return m;
	}

	/**
	 * 连接数据库
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Connection connect() throws SQLException {
		Connection cc = DriverManager.getConnection("jdbc:sqlite:" + url);
		return cc;
	}

	/**
	 * 关闭连接
	 * 
	 * @param cc
	 */
	public void close(Connection cc) {
		try {
			if (null != cc && !cc.isClosed()) {
				cc.close();
			}
		} catch (Exception e) {
			_Logger.error("关闭" + cc + "出错", e);
		}
	}

	@Override
	public <E> DataManager<E> get(String name) {
		return (DataManager<E>) map.get(name);
	}

}
