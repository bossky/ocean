package com.bossky.util;

/**
 * 简单的id-名称-值的封装
 * 
 * @author bo
 *
 */
public class NameItem {
	/** id */
	public int id;
	/** 名称 */
	public String name;
	/** 值 */
	public Object value;

	protected NameItem(int id, String name, Object value) {
		this.id = id;
		this.name = name;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "[id=" + id + ",name=" + name + ",value="
				+ (value == this ? "this" : value.toString()) + "]";
	}

	@Override
	public int hashCode() {
		return id + name.hashCode() * 11 + value.hashCode() * 23;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof NameItem) {
			NameItem ni = (NameItem) obj;
			return id == ni.id && name.equals(ni.name) && value.equals(value);
		}
		return false;
	}

	public static NameItem valueOf(int id, String name) {
		return new NameItem(id, name, name);
	}

	public static NameItem valueOf(int id, Object value) {
		return new NameItem(id, String.valueOf(id), value);
	}

	public static NameItem valueOf(String name, Object value) {
		return new NameItem(name.hashCode(), name, value);
	}

	public static NameItem valueOf(int id, String name, Object value) {
		return new NameItem(id, name, value);
	}
}
