package com.mendale.app.pojo;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 教程数据
 * @author Administrator
 *
 */
public class CoursePoJo extends BmobObject implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private List<CourseChildPojo> child;
	private String ico;

	public String getId() {
		return id;
	}

	
	public void setId(String id) {
		this.id = id;
	}

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

	@Override
	public String toString() {
		return "CoursePoJo [id=" + id + ", name=" + name + ", child=" + child
				+ ", ico=" + ico + "]";
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CourseChildPojo> getChild() {
		return child;
	}

	public void setChild(List<CourseChildPojo> child) {
		this.child = child;
	}

	
	
}
