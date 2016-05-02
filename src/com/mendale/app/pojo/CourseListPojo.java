package com.mendale.app.pojo;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class CourseListPojo extends BmobObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String hand_id;
	public String subject;
	public String host_pic;
	public String comment;
	public String collect;
	public String laud;
	public String view;
	public String user_id;
	public String step_count;
	public String user_name;
	public String face_pic;
	
	public String getHand_id() {
		return hand_id;
	}
	
	public void setHand_id(String hand_id) {
		this.hand_id = hand_id;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getHost_pic() {
		return host_pic;
	}
	
	public void setHost_pic(String host_pic) {
		this.host_pic = host_pic;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getCollect() {
		return collect;
	}
	
	public void setCollect(String collect) {
		this.collect = collect;
	}
	
	public String getLaud() {
		return laud;
	}
	
	public void setLaud(String laud) {
		this.laud = laud;
	}
	
	public String getView() {
		return view;
	}
	
	public void setView(String view) {
		this.view = view;
	}
	
	public String getUser_id() {
		return user_id;
	}
	
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public String getStep_count() {
		return step_count;
	}
	
	public void setStep_count(String step_count) {
		this.step_count = step_count;
	}
	
	public String getUser_name() {
		return user_name;
	}
	
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	public String getFace_pic() {
		return face_pic;
	}
	
	public void setFace_pic(String face_pic) {
		this.face_pic = face_pic;
	}

	@Override
	public String toString() {
		return "CourseListPojo [hand_id=" + hand_id + ", subject=" + subject + ", host_pic=" + host_pic + ", comment="
				+ comment + ", collect=" + collect + ", laud=" + laud + ", view=" + view + ", user_id=" + user_id
				+ ", step_count=" + step_count + ", user_name=" + user_name + ", face_pic=" + face_pic + "]";
	}
	
	
}
