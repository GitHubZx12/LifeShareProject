package com.mendale.app.vo;

import java.util.List;

public class CourseDetailsBean {
	
	public String host_pic;//图片
	public String subject;//标题
	public String hand_id;//详情页的id
	
	public String face_pic;//做着的图像
	public List<Step>step;
	public String summary;//简介
	public String view;//人气
	public String laud;//赞
	public String collect;//收藏
	public String comment_count;//评论
	public String getHost_pic() {
		return host_pic;
	}
	public void setHost_pic(String host_pic) {
		this.host_pic = host_pic;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getHand_id() {
		return hand_id;
	}
	public void setHand_id(String hand_id) {
		this.hand_id = hand_id;
	}
	public String getFace_pic() {
		return face_pic;
	}
	public void setFace_pic(String face_pic) {
		this.face_pic = face_pic;
	}
	public List<Step> getStep() {
		return step;
	}
	public void setStep(List<Step> step) {
		this.step = step;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public String getLaud() {
		return laud;
	}
	public void setLaud(String laud) {
		this.laud = laud;
	}
	public String getCollect() {
		return collect;
	}
	public void setCollect(String collect) {
		this.collect = collect;
	}
	public String getComment_count() {
		return comment_count;
	}
	public void setComment_count(String comment_count) {
		this.comment_count = comment_count;
	}
}
