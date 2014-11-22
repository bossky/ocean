package com.bossky.data.annotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.bossky.data.Mapper;
import com.bossky.data.Meta;

/**
 * 注释的映射类
 * 
 * @author bo
 *
 */
public class AnnotionMapper<E> implements Mapper<E> {
	/** 属性值 */
	protected Map<String, Meta> map = new HashMap<String, Meta>();
	/** 空构造对象 */
	protected Constructor<E> constructor;
	/** 类名 */
	protected String name;
	/** 主键 */
	protected Meta key;

	protected AnnotionMapper(String name, Meta key, Constructor<E> constructor,
			Map<String, Meta> map) {
		this.name = name;
		this.key = key;
		this.constructor = constructor;
		this.map = map;
	}

	@Override
	public Collection<Meta> metas() {
		return map.values();
	}

	@Override
	public Meta getMeta(String fieldName) {
		return map.get(fieldName);
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
		if (!constructor.isAccessible()) {
			constructor.setAccessible(true);
		}
		try {
			return constructor.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static <E extends Object> AnnotionMapper<E> valueOf(Class<E> clazz) {
		Constructor<E> c;
		try {
			c = clazz.getDeclaredConstructor();
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("类" + clazz.getName()
					+ "没有空的构造函数");
		} catch (SecurityException e) {
			throw new IllegalArgumentException("获取类" + clazz.getName()
					+ "空构造函数时出错了", e);
		}
		Meta key = null;
		Map<String, Meta> map = new HashMap<String, Meta>();
		for (Field f : clazz.getDeclaredFields()) {
			if (f.isAnnotationPresent(Resource.class)) {
				Meta meta = AnnotionMeta.valueOf(f);
				map.put(meta.getName(), meta);
				if (f.isAnnotationPresent(Id.class)) {
					if (null != key) {
						throw new IllegalArgumentException("类"
								+ clazz.getName() + "存在两个用@Id注解的主键");
					}
					key = meta;
				}
			}
		}
		return new AnnotionMapper<E>(clazz.getSimpleName(), key, c, map);
	}

	@Override
	public String toString() {
		return metas().toString();
	}

}
