package com.mendale.app.pojo;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * 教程——child
 * @author Administrator
 *
 */
public class CourseChildPojo extends BmobObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	
	public String getName() {
		return name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "CourseChildPojo [id=" + id + ", name=" + name + "]";
	}
	

}
