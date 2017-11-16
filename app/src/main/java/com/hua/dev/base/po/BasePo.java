package com.hua.dev.base.po;

import java.io.Serializable;

/**
 * 基类
 */
public class BasePo implements Serializable {

	/**
	 * 返回码
	 */
	private Integer rcode ;
	/**
	 * 返回信息
	 */
	private String rinfo ;

	public Integer getRcode() {
		return rcode;
	}

	public void setRcode(Integer rcode) {
		this.rcode = rcode;
	}

	public String getRinfo() {
		return rinfo;
	}

	public void setRinfo(String rinfo) {
		this.rinfo = rinfo;
	}
}
