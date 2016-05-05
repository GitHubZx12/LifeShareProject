package com.mendale.app.vo;

import cn.bmob.v3.BmobObject;

/**
 * 评论列表
 * 
 * @author zx
 *
 */
public class CommentList extends BmobObject {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
@Override
	public String toString() {
		return "CommentList [add_time=" + add_time + ", comment=" + comment + ", user_id=" + user_id + ", user_name="
				+ user_name + ", face_pic=" + face_pic + "]";
	}

	//	private String _id;//自己的id
	private String add_time;//评论的时间
	private String comment;//评论
	private String user_id;//用户id
	private String user_name;//用户名
	private String face_pic;//用户图片
	private String hand_id;

	public String getHand_id() {
		return hand_id;
	}

	public void setHand_id(String hand_id) {
		this.hand_id = hand_id;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
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

}
