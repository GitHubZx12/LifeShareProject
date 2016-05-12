package com.mendale.app.pojo;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 登陆的用户名和密码
 * @author zhangxue 
   @date 2016年4月9日
 */
public class MyUser extends BmobUser{
	
	
	private static final long serialVersionUID = 1L;
	private String birthy;
	private String url;
	private BmobFile img;
	private String sex;
	private String name;
	
	
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public BmobFile getImg() {
		return img;
	}

	public void setImg(BmobFile img) {
		this.img = img;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getBirthy() {
		return birthy;
	}
	
	public void setBirthy(String birthy) {
		this.birthy = birthy;
	}

}
