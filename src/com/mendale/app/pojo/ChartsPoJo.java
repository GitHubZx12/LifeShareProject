package com.mendale.app.pojo;

import java.io.Serializable;

/**
 * 排行榜
 * 
 * @author zhangxue
 * @date 2016年4月17日
 */
public class ChartsPoJo implements Serializable{
	

	@Override
	public String toString() {
		return "ChartsPoJo [hand_id=" + hand_id + ", subject=" + subject + ", host_pic=" + host_pic + ", comment="
				+ comment + ", collect=" + collect + ", laud=" + laud + ", view=" + view + ", user_id=" + user_id
				+ ", step_count=" + step_count + ", user_name=" + user_name + ", face_pic=" + face_pic + ", hand_daren="
				+ hand_daren + ", level=" + level + ", scores=" + scores + "]";
	}
	private String hand_id;
	private String subject;
	private String host_pic;
	private String comment;
	private String collect;
	private String laud;
	private String view;
	private String user_id;
	private String step_count;
	private String user_name;
	private String face_pic;
	private String hand_daren;
	private String level;
	private String scores;
	
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
	
	public String getHand_daren() {
		return hand_daren;
	}
	
	public void setHand_daren(String hand_daren) {
		this.hand_daren = hand_daren;
	}
	
	public String getLevel() {
		return level;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getScores() {
		return scores;
	}
	
	public void setScores(String scores) {
		this.scores = scores;
	}
	
	
}
