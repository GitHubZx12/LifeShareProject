package com.mendale.app.pojo;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 发布記錄
 * @author zx
 *
 */
public class OpusData extends BmobObject{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CourseListPojo> list;
     private String lastid;
	public List<CourseListPojo> getList() {
		return list;
	}
	public void setList(List<CourseListPojo> list) {
		this.list = list;
	}
	public String getLastid() {
		return lastid;
	}
	public void setLastid(String lastid) {
		this.lastid = lastid;
	}
     
     

}
