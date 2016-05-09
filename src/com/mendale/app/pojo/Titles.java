package com.mendale.app.pojo;

import cn.bmob.v3.BmobObject;

public class Titles extends BmobObject{
	private String titles_name;
	private String title_description;
	public String getTitles_name() {
		return titles_name;
	}
	public void setTitles_name(String titles_name) {
		this.titles_name = titles_name;
	}
	public String getTitle_description() {
		return title_description;
	}
	public void setTitle_description(String title_description) {
		this.title_description = title_description;
	}
	
	
}
