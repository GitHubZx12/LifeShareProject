package com.mendale.app.vo;

import cn.bmob.v3.BmobObject;

/**
 * 首页-手工达人的实体类
 * 
 * @author Administrator
 * 
 */
public class HomeDarenPoJo extends BmobObject{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int user_id;// 详情页的id
	private String face_pic;// 标题
	private String user_name;// 用户昵称

	public HomeDarenPoJo() {
	}
	public HomeDarenPoJo(int user_id, String face_pic, String user_name) {
		super();
		this.user_id = user_id;
		this.face_pic = face_pic;
		this.user_name = user_name;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getFace_pic() {
		return face_pic;
	}

	public void setFace_pic(String face_pic) {
		this.face_pic = face_pic;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	

	
}
