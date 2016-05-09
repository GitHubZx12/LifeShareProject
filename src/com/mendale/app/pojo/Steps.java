package com.mendale.app.pojo;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Steps extends BmobObject{
	/***
	 * 图片说明
	 */
	private String content;
	/***
	 *图片 
	 */
	private BmobFile picture;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public BmobFile getPicture() {
		return picture;
	}
	public void setPicture(BmobFile picture) {
		this.picture = picture;
	}

}
