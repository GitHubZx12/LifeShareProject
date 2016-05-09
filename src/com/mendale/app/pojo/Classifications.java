package com.mendale.app.pojo;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Classifications extends BmobObject{
	private String classify;
	private String tips;
	private BmobFile coverage;
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public String getTips() {
		return tips;
	}
	public void setTips(String tips) {
		this.tips = tips;
	}
	public BmobFile getCoverage() {
		return coverage;
	}
	public void setCoverage(BmobFile coverage) {
		this.coverage = coverage;
	}

}
