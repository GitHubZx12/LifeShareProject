package com.mendale.app.vo;

import cn.bmob.v3.BmobObject;

/**
 * 详情页的材料
 * @author zx
 *
 */
public class Material extends BmobObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String desc;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	

}
