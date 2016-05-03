package com.mendale.app.vo;

import cn.bmob.v3.BmobObject;

/**
 * 详细信息的步骤
 * @author zx
 *
 */
public class Step extends BmobObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String pic;
	public String content;
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	

}
