package com.bossky.ocean.ext;

import java.util.Iterator;
import java.util.List;

import com.bossky.util.Util;

public class ResultPage<E> implements Iterator<E>, Iterable<E> {
	public static final int LIMIT_NONE = Integer.MAX_VALUE;
	public static final int LIMIT_DEFAULT = -1;
	public static final int MAX_PAGE_SIZE = Util.toInt(
			System.getProperty("tern.ResultPage.maxPageSize"), -1);
	protected List<E> a;
	protected int m_PageSize = 10;

	protected int m_PageCount = -1;
	protected int m_CurrentPage;
	protected int m_CurrentPos;
	protected int m_CurrentPageEnd;
	protected int m_CurrentPageBegin;

	@SuppressWarnings("unchecked")
	public ResultPage(List<? super E> paramList) {
		this.a = (List<E>) paramList;
	}

	protected final E get(int paramInt) {
		return this.a.get(paramInt);
	}

	public final int getCount() {
		return this.a.size();
	}

	public int getPageCount() {
		if (-1 == this.m_PageCount) {
			reinit();
		}
		return this.m_PageCount;
	}

	public int getPageSize() {
		return this.m_PageSize;
	}

	public void setPage(int paramInt) {
		if (!(gotoPage(paramInt)))
			throw new IndexOutOfBoundsException("over page " + paramInt
					+ " at 1~" + getPageCount());
	}

	public boolean gotoPage(int paramInt) {
		if ((paramInt <= 0) || (paramInt > getPageCount())) {
			return false;
		}

		this.m_CurrentPageBegin = (this.m_CurrentPos = (paramInt - 1)
				* getPageSize());
		if (paramInt == this.m_CurrentPage) {
			return true;
		}

		this.m_CurrentPage = paramInt;

		this.m_CurrentPageEnd = (this.m_CurrentPos + getPageSize());
		if (this.m_CurrentPageEnd > getCount()) {
			this.m_CurrentPageEnd = getCount();
		}

		return true;
	}

	public static final void pageSizeCheck(int paramInt) {
		if ((MAX_PAGE_SIZE > 0) && (paramInt > MAX_PAGE_SIZE))
			throw new IllegalArgumentException("超出页的最大项数：" + paramInt + "/"
					+ MAX_PAGE_SIZE);
	}

	public void setPageSize(int paramInt) {
		pageSizeCheck(paramInt);
		if ((this.m_PageSize != paramInt) && (paramInt > 0)) {
			this.m_PageSize = ((-1 == paramInt) ? getCount() : paramInt);
			reinit();
		}
	}

	public boolean hasNext() {
		return (this.m_CurrentPos < this.m_CurrentPageEnd);
	}

	public E next() {
		if (hasNext())
			return get(this.m_CurrentPos++);
		return null;
	}

	public void remove() {
		throw new UnsupportedOperationException("此功能不支持");
	}

	public boolean hasPrev() {
		return (this.m_CurrentPos > this.m_CurrentPageBegin);
	}

	public E prev() {
		if (hasPrev())
			return get(--this.m_CurrentPos);
		return null;
	}

	public E move(int paramInt) {
		if ((paramInt >= 0)
				&& (paramInt < this.m_CurrentPageEnd - this.m_CurrentPageBegin)) {
			this.m_CurrentPos = (this.m_CurrentPageBegin + paramInt);
			return get(this.m_CurrentPos);
		}
		return null;
	}

	public int getPage() {
		return this.m_CurrentPage;
	}

	public Iterator<E> iterator() {
		return this;
	}

	protected void reinit() {
		this.m_PageCount = (getCount() / this.m_PageSize);
		if (getCount() % this.m_PageSize > 0) {
			this.m_PageCount += 1;
		}
		this.m_CurrentPage = 0;
		this.m_CurrentPos = 0;
		this.m_CurrentPageBegin = 0;
		this.m_CurrentPageEnd = 0;
	}

	public static void main(String[] args) {
		System.out.println(Integer.MAX_VALUE);
	}
}
