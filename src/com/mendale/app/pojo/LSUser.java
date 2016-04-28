package com.mendale.app.pojo;

import cn.bmob.v3.BmobObject;

/**
 * 登陆的用户名和密码
 * @author zhangxue 
   @date 2016年4月9日
 */
public class LSUser extends BmobObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;
	private String name;
	private String birthy;
	private String phone;
	private String email;
	
	
	
	
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


	
	public String getPhone() {
		return phone;
	}


	
	public void setPhone(String phone) {
		this.phone = phone;
	}


	
	public String getEmail() {
		return email;
	}


	
	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}

	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
