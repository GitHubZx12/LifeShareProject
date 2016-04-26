package com.mendale.app.pojo;

import java.io.Serializable;

/**
 * 上传教程--步骤
 * @author zx
 *
 */
public class ReplayPoJo implements Serializable{
	
	private String path;
	private String desc;
	private boolean flag;
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "ReplayPoJo [path=" + path + ", desc=" + desc + ", flag=" + flag + "]";
	}
	
	
	

}
