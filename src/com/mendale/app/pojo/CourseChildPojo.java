package com.mendale.app.pojo;

import java.io.Serializable;

/**
 * 教程——child
 * @author Administrator
 *
 */
public class CourseChildPojo implements Serializable{
	
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

}
