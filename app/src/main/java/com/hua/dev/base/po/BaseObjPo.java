package com.hua.dev.base.po;

/**
 * 基本objPo
 * @author tanjianhua
 * @date 2016-9-13 下午4:23:28
 */
public class BaseObjPo<T> extends BasePo {

	public T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
