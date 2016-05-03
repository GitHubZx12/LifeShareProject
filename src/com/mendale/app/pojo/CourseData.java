package com.mendale.app.pojo;

import java.io.Serializable;
import java.util.List;

import com.mendale.app.vo.HomeHotCoursePoJo;

import cn.bmob.v3.BmobObject;

public class CourseData extends BmobObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<HomeHotCoursePoJo>list;
    private String lastid;
	public List<HomeHotCoursePoJo> getList() {
		return list;
	}
	public void setList(List<HomeHotCoursePoJo> list) {
		this.list = list;
	}
	public String getLastid() {
		return lastid;
	}
	public void setLastid(String lastid) {
		this.lastid = lastid;
	}
	@Override
	public String toString() {
		return "CourseData [list=" + list + ", lastid=" + lastid + "]";
	}
	
	
    
    
    
    

}
