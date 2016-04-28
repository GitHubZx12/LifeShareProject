package com.mendale.app.vo;

import cn.bmob.v3.BmobObject;

/**
 * 记录
 * @author Administrator
 *
 */
public class RecordItemBean extends BmobObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String opus_id;
	private String subject;
	private String uid;
	private String host_pic;//内容图片
	private String content;//内容
	private String user_name;//用户昵称
	private String face_pic;//用户头像
	private String view;//人气
	private String collect;//收藏
	private String comment;//评论
	private String laud;//赞
	public String getOpus_id() {
		return opus_id;
	}
	public void setOpus_id(String opus_id) {
		this.opus_id = opus_id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getHost_pic() {
		return host_pic;
	}
	public void setHost_pic(String host_pic) {
		this.host_pic = host_pic;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public String getCollect() {
		return collect;
	}
	public void setCollect(String collect) {
		this.collect = collect;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getLaud() {
		return laud;
	}
	public void setLaud(String laud) {
		this.laud = laud;
	}
	
	

}
