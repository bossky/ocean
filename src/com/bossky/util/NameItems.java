package com.bossky.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * NameItem的集合
 * 
 * @author bo
 *
 */
public class NameItems implements List<NameItem> {
	/** 存储数组 */
	protected NameItem[] data;

	protected NameItems(NameItem[] data) {
		Arrays.sort(data, SORT_BY_ID);
		this.data = data;
	}

	/**
	 * 构造一个NameItems
	 * 
	 * @param ni
	 * @return
	 */
	public static NameItems valueOf(NameItem... ni) {
		return new NameItems(ni);
	}

	/**
	 * 通过id获取对象
	 * 
	 * @param id
	 * @return
	 */
	public NameItem load(int id) {
		int low = 0;
		int high = data.length - 1;
		int m;
		while (low <= high) {
			m = (high + low) >>> 1;
			if (data[m].id > id) {
				high = m - 1;
			} else if (data[m].id < id) {
				low = m + 1;
			} else {
				return data[m];
			}
		}
		return null;
	}

	/**
	 * 通过name获取对象
	 * 
	 * @param id
	 * @return
	 */
	public NameItem load(String name) {
		if (name != null) {
			Iterator<NameItem> it = iterator();
			while (it.hasNext()) {
				NameItem ni = it.next();
				if (null != ni && name.equals(ni.name)) {
					return ni;
				}
			}
		}
		return null;
	}

	/**
	 * 通过值获取对象
	 * 
	 * @param id
	 * @return
	 */
	public NameItem load(Object value) {
		if (value != null) {
			Iterator<NameItem> it = iterator();
			while (it.hasNext()) {
				NameItem ni = it.next();
				if (null != ni && value.equals(ni.value)) {
					return ni;
				}
			}
		}
		return null;
	}

	static Comparator<NameItem> SORT_BY_ID = new Comparator<NameItem>() {

		@Override
		public int compare(NameItem o1, NameItem o2) {
			return o1.getId() - o2.getId();
		}

	};

	@Override
	public int size() {
		return data.length;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean contains(Object o) {
		Iterator<NameItem> it = iterator();
		if (null == o) {
			while (it.hasNext()) {
				if (it.next() == null) {
					return true;
				}
			}
		} else {
			while (it.hasNext()) {
				if (o.equals(it.next())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Iterator<NameItem> iterator() {
		return new Itr();
	}

	@Override
	public Object[] toArray() {
		return data;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		int l = a.length < data.length ? a.length : data.length;
		System.arraycopy(data, 0, a, 0, l);
		return a;
	}

	@Override
	public boolean add(NameItem e) {
		NameItem[] newdata = new NameItem[size() + 1];
		System.arraycopy(data, 0, newdata, 0, data.length);
		newdata[data.length] = e;
		Arrays.sort(newdata, SORT_BY_ID);
		data = newdata;
		return true;
	}

	@Override
	public boolean remove(Object o) {
		Iterator<NameItem> it = iterator();
		if (null == o) {
			while (it.hasNext()) {
				if (it.next() == null) {
					it.remove();
					return true;
				}
			}
		} else {
			while (it.hasNext()) {
				if (o.equals(it.next())) {
					it.remove();
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		Iterator<?> it = c.iterator();
		while (it.hasNext()) {
			if (!contains(it.next())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends NameItem> c) {
		NameItem[] arr = (NameItem[]) c.toArray();
		NameItem[] newdata = new NameItem[arr.length + data.length];
		System.arraycopy(data, 0, newdata, 0, data.length);
		System.arraycopy(arr, 0, newdata, data.length, arr.length);
		Arrays.sort(newdata, SORT_BY_ID);
		data = newdata;
		return true;
	}

	@Override
	public boolean addAll(int index, Collection<? extends NameItem> c) {
		throw new UnsupportedOperationException("不支持添加到指定位置");
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		Iterator<?> it = c.iterator();
		boolean result = true;
		while (it.hasNext()) {
			if (!remove(it.next())) {
				result = false;
			}
		}
		return result;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean modified = false;
		Iterator<NameItem> e = iterator();
		while (e.hasNext()) {
			if (!c.contains(e.next())) {
				e.remove();
				modified = true;
			}
		}
		return modified;
	}

	@Override
	public void clear() {
		data = new NameItem[0];
	}

	@Override
	public NameItem get(int index) {
		return data[index];
	}

	@Override
	public NameItem set(int index, NameItem element) {
		throw new UnsupportedOperationException("不支持添加到指定位置");

	}

	@Override
	public void add(int index, NameItem element) {
		throw new UnsupportedOperationException("不支持添加到指定位置");
	}

	@Override
	public NameItem remove(int index) {
		NameItem var = data[index];
		NameItem[] newdata = new NameItem[data.length - 1];
		System.arraycopy(data, 0, newdata, 0, index - 1);
		System.arraycopy(data, index, newdata, index - 1, newdata.length
				- index + 1);
		// Arrays.sort(newdata, C);
		data = newdata;
		return var;
	}

	@Override
	public int indexOf(Object o) {
		if (null == o) {
			for (int i = 0; i < data.length; i++) {
				if (data[i] == null) {
					return i;
				}
			}
		} else {
			for (int i = 0; i < data.length; i++) {
				if (o.equals(data[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		if (null == o) {
			for (int i = data.length - 1; i >= 0; i--) {
				if (data[i] == null) {
					return i;
				}
			}
		} else {
			for (int i = data.length - 1; i >= 0; i--) {
				if (o.equals(data[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	@Override
	public ListIterator<NameItem> listIterator() {
		return new Itr();
	}

	@Override
	public ListIterator<NameItem> listIterator(int index) {
		return new Itr(index);
	}

	@Override
	public List<NameItem> subList(int fromIndex, int toIndex) {
		NameItem[] newdata = new NameItem[toIndex - fromIndex];
		System.arraycopy(data, fromIndex, newdata, 0, newdata.length);
		return new NameItems(newdata);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for (int i = 0; i < data.length; i++) {
			sb.append(data[i]);
			if (i == data.length - 1) {
				return sb.append("}").toString();
			}
			sb.append(",");
		}
		return sb.toString();
	}

	class Itr implements ListIterator<NameItem> {
		private int cursor;

		Itr() {

		}

		Itr(int cursor) {
			this.cursor = cursor;
		}

		@Override
		public boolean hasNext() {
			return cursor < size();
		}

		@Override
		public NameItem next() {
			return data[cursor++];
		}

		@Override
		public void remove() {
			NameItem[] newdata = new NameItem[data.length - 1];
			System.arraycopy(data, 0, newdata, 0, cursor - 1);
			System.arraycopy(data, cursor, newdata, cursor - 1, newdata.length
					- cursor + 1);
			// Arrays.sort(newdata, C);
			data = newdata;
		}

		@Override
		public boolean hasPrevious() {
			return cursor > 0;
		}

		@Override
		public NameItem previous() {
			return data[cursor--];
		}

		@Override
		public int nextIndex() {
			return cursor + 1;
		}

		@Override
		public int previousIndex() {
			return cursor - 1;
		}

		@Override
		public void set(NameItem e) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(NameItem e) {
			throw new UnsupportedOperationException();
		}

	}

}
