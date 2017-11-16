package com.hua.dev.base.po;

import java.util.List;

/**
 * 分页对象
 * 
 * @author btwo
 * 
 * @param <T>
 */
public class Pager<T> {

	/**
	 * 上一页
	 */
	private long preIndex;
	/**
	 * 当前页
	 */
	private long pageIndex;
	/**
	 * 下一页
	 */
	private long nextIndex;
	/**
	 * 每页条数
	 */
	private long pageSize;
	/**
	 * 总条数
	 */
	private long rowsCount;

	/**
	 * 总页数
	 */
	private long pagesCount;
	/**
	 * 分页数据
	 */
	private List<T> items;

	public long getPreIndex() {
		return preIndex;
	}

	public void setPreIndex(long preIndex) {
		this.preIndex = preIndex;
	}

	public long getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(long pageIndex) {
		this.pageIndex = pageIndex;
	}

	public long getNextIndex() {
		return nextIndex;
	}

	public void setNextIndex(long nextIndex) {
		this.nextIndex = nextIndex;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getRowsCount() {
		return rowsCount;
	}

	public void setRowsCount(long rowsCount) {
		this.rowsCount = rowsCount;
	}

	public long getPagesCount() {
		return pagesCount;
	}

	public void setPagesCount(long pagesCount) {
		this.pagesCount = pagesCount;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}
}
