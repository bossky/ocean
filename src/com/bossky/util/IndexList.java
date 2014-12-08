package com.bossky.util;

import java.util.Iterator;

/**
 * 权限集合
 * 
 * @author bo
 * @param <E>
 *
 */
@SuppressWarnings("unchecked")
public class IndexList<E extends Indexable> implements Iterable<E> {
	/** 数据 */
	protected Object[] data;
	/** 个数 */
	protected int size;
	/** 最大索引位置 */
	protected int maxIndex;
	/** 支持的最大索引 */
	static int MAX_INDEX = 100000;

	protected IndexList() {
		data = new Object[10];
	}

	protected IndexList(int init) {
		data = new Object[init];
	}

	public static <E extends Indexable> IndexList<E> getInstance(Class<E> Object) {
		return new IndexList<E>();
	}

	public static <E extends Indexable> IndexList<E> getInstance(int initcaption) {
		return new IndexList<E>(initcaption);
	}

	/**
	 * 存入权限，已存在会替换
	 * 
	 * @param r
	 * @return　返回旧的角色
	 */
	public E put(E r) {
		if (null == r) {
			throw new IllegalArgumentException("不能存入null值");
		}
		int index = r.index();
		if (index > MAX_INDEX) {
			throw new IllegalArgumentException("index:" + index + "不能大于"
					+ MAX_INDEX);
		}
		sureRange(index);
		E original = (E) data[index];
		if (null == original) {
			// 原来是没有的然后放入新的个数加1
			size++;
		}
		if (index > maxIndex) {
			maxIndex = index;
		}
		data[index] = r;
		return original;
	}

	/**
	 * 通过id获取角色
	 * 
	 * @param id
	 * @return
	 */
	public E get(int id) {
		if (id < 0 || id > data.length - 1) {
			return null;
		}
		return (E) data[id];
	}

	public int size() {
		return size;
	}

	private void sureRange(int i) {
		if (i > data.length) {
			Object[] newdata = new Object[i + (i >> 1)];
			System.arraycopy(data, 0, newdata, 0, data.length);
			data = newdata;
		}
	}

	public E[] toArray() {
		Object[] newData = new Object[size];
		Iterator<E> it = iterator();
		for (int i = 0; i < newData.length; i++) {
			newData[i] = it.next();
		}
		return (E[]) newData;
	}

	@Override
	public Iterator<E> iterator() {
		return new Itr();
	}

	@Override
	public String toString() {

		Iterator<E> it = iterator();
		if (!it.hasNext()) {
			return "{}";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		while (it.hasNext()) {
			sb.append(it.next());
			if (!it.hasNext()) {
				return sb.append("}").toString();
			}
			sb.append(",");
		}
		return sb.toString();
	}

	class Itr implements Iterator<E> {
		private int cursor;

		Itr() {

		}

		@Override
		public boolean hasNext() {
			return cursor < maxIndex + 1;
		}

		@Override
		public E next() {
			while (cursor < data.length) {
				E r = (E) data[cursor++];
				if (r != null) {
					return r;
				}
			}
			return null;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
