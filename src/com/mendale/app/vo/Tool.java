package com.mendale.app.vo;

import cn.bmob.v3.BmobObject;

/**
 * 详情页的工具
 * @author zx
 *
 */
public class Tool extends BmobObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String num;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	

}
