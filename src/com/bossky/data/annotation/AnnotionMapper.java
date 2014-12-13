package com.bossky.data.annotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bossky.data.Mapper;
import com.bossky.data.Meta;
import com.bossky.util.Util;

/**
 * 注释的映射类
 * 
 * @author bo
 *
 */
public class AnnotionMapper<E> implements Mapper<E> {
	/** 日志 */
	Logger _Logger = LoggerFactory.getLogger(AnnotionMapper.class);
	/** 属性值 */
	protected Map<String, Meta> MetaMap = new HashMap<String, Meta>();
	/** 构造对象 */
	protected Constructor<E> constructor;
	/** 类名 */
	protected String name;
	/** 主键 */
	protected Meta key;
	/** 生成构造对象的默认参数 */
	protected Object defaultConstructorArgs[];

	protected AnnotionMapper(String name, Meta key, Constructor<E> constructor,
			Map<String, Meta> map) {
		this.name = name;
		this.key = key;
		this.constructor = constructor;
		this.MetaMap = map;
	}

	@Override
	public Collection<Meta> metas() {
		return MetaMap.values();
	}

	@Override
	public Meta getMeta(String fieldName) {
		return MetaMap.get(fieldName);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Meta getKey() {
		return key;
	}

	@Override
	public E getInsantce() {
		try {
			if (!constructor.isAccessible()) {
				constructor.setAccessible(true);
			}
			if (Util.isEmpty(defaultConstructorArgs)) {
				return constructor.newInstance();
			} else {
				return constructor.newInstance(defaultConstructorArgs);
			}
		} catch (InstantiationException e) {
			_Logger.warn("实例化对象出错", e);
		} catch (IllegalAccessException e) {
			_Logger.warn("实例化对象出错", e);
		} catch (IllegalArgumentException e) {
			_Logger.warn("实例化对象出错", e);
		} catch (InvocationTargetException e) {
			_Logger.warn("实例化对象出错", e);
		}
		return null;
	}

	/**
	 * 生成基本Annotion的对象Mapper
	 * 
	 * @param clazz
	 *            要生成mapper的类
	 * @param objs
	 *            默认的构造参数
	 * @return
	 */
	public static <E extends Object> AnnotionMapper<E> valueOf(Class<E> clazz,
			Object... objs) {
		String name = clazz.getSimpleName();
		Constructor<E> constructor = getConstructor(clazz, objs);
		// 获取主键和属性对象
		Meta key = null;
		Map<String, Meta> map = new HashMap<String, Meta>();
		Class<?> cl = clazz;
		do {
			for (Field f : cl.getDeclaredFields()) {
				if (AnnotionParser.isId(f)) {
					if (null != key) {
						throw new IllegalArgumentException("类" + name
								+ "存在两个用@Id注解的主键");
					}
					Meta meta = AnnotionMeta.valueOf(f);
					key = meta;
				} else if (AnnotionParser.isResource(f)) {
					Meta meta = AnnotionMeta.valueOf(f);
					map.put(meta.getName(), meta);
				}
			}
			if (AnnotionParser.isSubClass(cl)) {
				cl = cl.getSuperclass();
			} else {
				cl = null;
			}
		} while (null != cl);
		AnnotionMapper<E> mapper = new AnnotionMapper<E>(name, key,
				constructor, map);
		mapper.defaultConstructorArgs = objs;
		return mapper;
	}

	public static <E> Constructor<E> getConstructor(Class<E> clazz,
			Object... objects) {
		if (Util.isEmpty(objects)) {
			try {
				return clazz.getDeclaredConstructor();
			} catch (NoSuchMethodException e) {
				throw new IllegalArgumentException("类" + clazz.getName()
						+ "没有空的构造函数");
			} catch (SecurityException e) {
				throw new IllegalArgumentException("获取类" + clazz.getName()
						+ "空构造函数时出错了", e);
			}
		} else if (objects.length == 1) {
			Class<?> objecClass = objects[0].getClass();
			try {
				return getConstructor(clazz, objects[0].getClass());
			} catch (NoSuchMethodException e) {
				throw new IllegalArgumentException("类" + clazz.getName()
						+ "没有带" + objecClass.getName() + "参数的构造函数");
			} catch (SecurityException e) {
				throw new IllegalArgumentException("获取类" + clazz.getName()
						+ "带" + objecClass.getName() + "参数的构造函数时出错了", e);
			}
		} else {
			for (Object o : objects) {
				try {
					return getConstructor(clazz, o.getClass());
				} catch (NoSuchMethodException e) {
					//
				}
			}
			throw new IllegalArgumentException("类" + clazz.getName()
					+ "没有带对应参数的构造函数");
		}

	}

	/**
	 * 获取对象的构造
	 * 
	 * @param clazz
	 * @param objclazz
	 * @return
	 * @throws NoSuchMethodException
	 */
	public static <E> Constructor<E> getConstructor(Class<E> clazz,
			Class<?> objclazz) throws NoSuchMethodException {
		Constructor<E> c;
		try {
			c = clazz.getConstructor(objclazz);
			return c;
		} catch (SecurityException e) {
			//
		} catch (NoSuchMethodException e) {
			//
		}
		// 接口对应的构造
		for (Class<?> cl : objclazz.getInterfaces()) {
			try {
				c = clazz.getConstructor(cl);
				return c;
			} catch (SecurityException e) {
				//
			} catch (NoSuchMethodException e) {
				//
			}
		}
		// 拿父类的构造
		Class<?> subClass = objclazz;
		Class<?> superClass = subClass.getSuperclass();
		while (null != superClass && AnnotionParser.isSubClass(subClass)) {
			try {
				c = clazz.getConstructor(superClass);
				return c;
			} catch (SecurityException e) {
				//
			} catch (NoSuchMethodException e) {
				//
			}
		}
		throw new NoSuchMethodException();

	}

	@Override
	public String toString() {
		return name + "[" + metas().toString() + "]";
	}

}
