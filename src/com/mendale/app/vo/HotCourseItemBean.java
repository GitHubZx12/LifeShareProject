package com.mendale.app.vo;

/**
 * 首页-热门教程的实体类
 * 
 * @author Administrator
 * 
 */
public class HotCourseItemBean {

	private static final long serialVersionUID = 1L;
	private int hand_id;// 详情页的id
	private String host_pic;// 图片
	private String subject;// 标题
	private String user_name;// 用户昵称
	private int step_count;// 步骤

	public HotCourseItemBean() {
	}

	public HotCourseItemBean(int hand_id, String host_pic, String subject,
			String user_name, int step_count) {
		super();
		this.hand_id = hand_id;
		this.host_pic = host_pic;
		this.subject = subject;
		this.user_name = user_name;
		this.step_count = step_count;
	}

	public int getHand_id() {
		return hand_id;
	}

	public void setHand_id(int hand_id) {
		this.hand_id = hand_id;
	}

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

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public int getStep_count() {
		return step_count;
	}

	public void setStep_count(int step_count) {
		this.step_count = step_count;
	}

}
