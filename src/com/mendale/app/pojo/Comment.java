package com.mendale.app.pojo;

import cn.bmob.v3.BmobObject;

/**
 * 评论
 * @author zx
 *
 */
public class Comment extends BmobObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String content;//评论内容
	private MyUser user;//评论的用户，Pointer类型，一对一关系
	private Record post;//所评论的九路，这里体现一对多的关系，一个评论只能属于一个记录
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public MyUser getUser() {
		return user;
	}
	public void setUser(MyUser user) {
		this.user = user;
	}
	public Record getPost() {
		return post;
	}
	public void setPost(Record post) {
		this.post = post;
	}

	
	
}
