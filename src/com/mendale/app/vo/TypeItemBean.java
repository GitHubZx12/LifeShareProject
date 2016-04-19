package com.mendale.app.vo;

import java.util.List;

/**
 * 首页-热门类别的实体类
 * 
 * @author Administrator
 * 
 */
public class TypeItemBean {

	private int cate_id;// 详情页的id
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

	public TypeItemBean(int cate_id, List<String> pic, String cate_name) {
		super();
		this.cate_id = cate_id;
		this.pic = pic;
		this.cate_name = cate_name;
	}
	
}
