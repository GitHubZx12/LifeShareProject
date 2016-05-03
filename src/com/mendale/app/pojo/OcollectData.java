package com.mendale.app.pojo;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 发布記錄
 * @author zx
 *
 */
public class OcollectData extends BmobObject{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<RecordItemBean> list;
     private String lastid;
	public List<RecordItemBean> getList() {
		return list;
	}
	public void setList(List<RecordItemBean> list) {
		this.list = list;
	}
	public String getLastid() {
		return lastid;
	}
	public void setLastid(String lastid) {
		this.lastid = lastid;
	}
     
     

}
