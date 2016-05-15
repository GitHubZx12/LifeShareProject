package com.mendale.app.pojo;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 登陆的用户名和密码
 * @author zhangxue 
   @date 2016年4月9日
 */
public class MyUser extends BmobUser implements Serializable{
	
	
	@Override
	public String toString() {
		return "MyUser [birthy=" + birthy + ", url=" + url + ", img=" + img + ", sex=" + sex + ", sign=" + sign
				+ ", city=" + city + "]";
	}

	private static final long serialVersionUID = 1L;
	private String birthy;
	private String url;
	private BmobFile img;
	private String sex;
	private String sign;
	private String city;
	

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

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

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}


	public String getBirthy() {
		return birthy;
	}
	
	public void setBirthy(String birthy) {
		this.birthy = birthy;
	}

}
