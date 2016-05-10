package com.mendale.app.pojo;

import cn.bmob.v3.BmobUser;

/**
 * 登陆的用户名和密码
 * @author zhangxue 
   @date 2016年4月9日
 */
public class MyUser extends BmobUser{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String birthy;
	
	
	public String getBirthy() {
		return birthy;
	}
	
	public void setBirthy(String birthy) {
		this.birthy = birthy;
	}

}
