package com.mendale.app.pojo;

import cn.bmob.v3.BmobObject;

/**
 * 登陆的用户名和密码
 * @author zhangxue 
   @date 2016年4月9日
 */
public class LoginUser extends BmobObject{
	
	private String userName;
	private String password;
	
	
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
