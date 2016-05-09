package com.mendale.app.pojo;

import cn.bmob.v3.BmobObject;

/**
 * 求教程实体类
 * @author zx
 *
 */
public class HelpCoursePoJo extends BmobObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;//标题
	private String content;//描述
	private String phone;//手机号
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

}
