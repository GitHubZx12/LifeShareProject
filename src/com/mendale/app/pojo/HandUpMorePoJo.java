package com.mendale.app.pojo;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * 首页--手工达人--更多
 * 
 * @author zx
 *
 */
public class HandUpMorePoJo extends BmobObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String user_id;
	public String view;
	public String user_name;
	public String face_pic;
	public String hand_daren;
	public String level;
	public String scores;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
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
