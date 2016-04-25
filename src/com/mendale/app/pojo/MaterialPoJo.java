package com.mendale.app.pojo;

import java.io.Serializable;

/**
 * 上传教程--材料
 * @author zx
 *
 */
public class MaterialPoJo implements Serializable{
	
	private String name;
	private String desc;
	private boolean flag;
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
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "MaterialPoJo [name=" + name + ", desc=" + desc + ", flag=" + flag + "]";
	}
	
	
	

}
