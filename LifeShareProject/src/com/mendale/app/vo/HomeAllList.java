package com.mendale.app.vo;

import java.util.List;


/**
 * 首页
 * @author Administrator
 *
 */
public class HomeAllList {
	
	private List<HotCourseItemBean>courseData;
	private List<DarenItemBean>darenData;
	private List<TypeItemBean>typeData;
	
	public HomeAllList() {}
	public HomeAllList(List<HotCourseItemBean> courseData,
			List<DarenItemBean> darenData, List<TypeItemBean> typeData) {
		super();
		this.courseData = courseData;
		this.darenData = darenData;
		this.typeData = typeData;
	}
	public List<HotCourseItemBean> getCourseData() {
		return courseData;
	}
	public void setCourseData(List<HotCourseItemBean> courseData) {
		this.courseData = courseData;
	}
	public List<DarenItemBean> getDarenData() {
		return darenData;
	}
	public void setDarenData(List<DarenItemBean> darenData) {
		this.darenData = darenData;
	}
	public List<TypeItemBean> getTypeData() {
		return typeData;
	}
	public void setTypeData(List<TypeItemBean> typeData) {
		this.typeData = typeData;
	}
	

}
