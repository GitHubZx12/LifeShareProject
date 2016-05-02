package com.mendale.app.vo;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 首页-热门类别的实体类
 * 
 * @author Administrator
 * 
 */
public class HomeTypePoJo extends BmobObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer cate_id;// 详情页的id
	private List<String> pic;// 标题
	private String cate_name;// 用户昵称
	
	public int getCate_id() {
		return cate_id;
	}

	public void setCate_id(int cate_id) {
		this.cate_id = cate_id;
	}

	public List<String> getPic() {
		return pic;
	}

	public void setPic(List<String> pic) {
		this.pic = pic;
	}

	public String getCate_name() {
		return cate_name;
	}

	public void setCate_name(String cate_name) {
		this.cate_name = cate_name;
	}

	
}
