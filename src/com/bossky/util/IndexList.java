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
	final static int MAX_INDEX = 100000;

	protected IndexList() {
		data = new Object[10];
	}

	protected IndexList(int init) {
		data = new Object[init];
	}

	/**
	 * 获取一个索引列表
	 * 
	 * @param Object
	 * @return
	 */
	public static <E extends Indexable> IndexList<E> getInstance(Class<E> Object) {
		return new IndexList<E>();
	}

	/**
	 * 获取一个索引列表
	 * 
	 * @param initcaption
	 *            初始容量
	 * @return
	 */
	public static <E extends Indexable> IndexList<E> getInstance(int initcaption) {
		return new IndexList<E>(initcaption);
	}

	/**
	 * 存入
	 * 
	 * @param e
	 * @return　返回元素
	 */
	public E put(E e) {
		if (null == e) {
			throw new IllegalArgumentException("不能存入null值");
		}
		int index = e.index();
		if (index > MAX_INDEX) {
			throw new IllegalArgumentException("index:" + index + "不能大于"
					+ MAX_INDEX);
		}
		sureRange(index);
		E original = (E) data[index];
		if (null == original) {
			// 原来是没有的然后放入则size加1
			size++;
		}
		if (index > maxIndex) {
			maxIndex = index;
		}
		data[index] = e;
		return original;
	}

	/**
	 * 通过index 获取元素
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

	/**
	 * 列表大小
	 * 
	 * @return
	 */
	public int size() {
		return size;
	}

	/**
	 * 确保存储数据的容量
	 * 
	 * @param i
	 */
	private void sureRange(int i) {
		if (i > data.length) {
			Object[] newdata = new Object[i + (i >> 1)];
			System.arraycopy(data, 0, newdata, 0, data.length);
			data = newdata;
		}
	}

	/**
	 * 转换成数组
	 * 
	 * @return
	 */
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
