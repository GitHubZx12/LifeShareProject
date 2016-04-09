package com.mendale.app.vo;

import java.io.Serializable;

import com.google.gson.JsonObject;

/**
 * 服务器返回数据格式的公共实体
 * @author jsx
 *
 */
public class ResponseBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * 登录
	 * 
	 */
	private String status;// 0失败  1成功
	private String msg;// 提示信息
	private Object data;// 返回的数据
	
	public ResponseBean(){
		super();
	}
	
	public ResponseBean(String status, String msg, Object data) {
		super();
		this.status = status;
		this.msg = msg;
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

}
