package com.mendale.app.vo;

import java.util.List;


/**
 * 首页
 * @author Administrator
 *
 */
public class HomeAllList {
	
	private List<HomeHotCoursePoJo>courseData;
	private List<HomeDarenPoJo>darenData;
	private List<HomeTypePoJo>typeData;
	
	public HomeAllList() {}
	public HomeAllList(List<HomeHotCoursePoJo> courseData,
			List<HomeDarenPoJo> darenData, List<HomeTypePoJo> typeData) {
		super();
		this.courseData = courseData;
		this.darenData = darenData;
		this.typeData = typeData;
	}
	public List<HomeHotCoursePoJo> getCourseData() {
		return courseData;
	}
	public void setCourseData(List<HomeHotCoursePoJo> courseData) {
		this.courseData = courseData;
	}
	public List<HomeDarenPoJo> getDarenData() {
		return darenData;
	}
	public void setDarenData(List<HomeDarenPoJo> darenData) {
		this.darenData = darenData;
	}
	public List<HomeTypePoJo> getTypeData() {
		return typeData;
	}
	public void setTypeData(List<HomeTypePoJo> typeData) {
		this.typeData = typeData;
	}
	

}
