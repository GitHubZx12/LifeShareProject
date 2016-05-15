package com.mendale.app.vo;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 详细信息的步骤
 * @author zx
 *
 */
public class Step extends BmobObject{
	
	@Override
	public String toString() {
		return "Step [url=" + url + ", content=" + content + ", img=" + img + "]";
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String url;
	public String content;
	public BmobFile img;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public BmobFile getImg() {
		return img;
	}
	public void setImg(BmobFile img) {
		this.img = img;
	}
	
	

}
