package com.hua.dev.base.po;


/**
 * 基本分页po
 * @author:tjhua
 * @date:2015-9-24 上午10:47:59
 * <p>description:</p>
 */
public class BaseListPo<T> extends BasePo {

	private Pager<T> data;

	public Pager getData() {
		return data;
	}

	public void setData(Pager data) {
		this.data = data;
	}
}
