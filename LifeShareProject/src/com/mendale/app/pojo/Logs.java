package com.mendale.app.pojo;

import java.io.Serializable;

/**
 * 24,日志信息表
 * 
 * @author wenjian
 * 
 */
public class Logs implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;// id
	private String sapAccount;// 调用者账号
	private String sendsys;// 发送系统 PDA
	private String receivesys;// 接收系统 MID
	private String apiname;// 接口名称
	private String starttime;// 开始时间
	private String endtime;// 结束时间
	private String data;// 传输数据
	private String res;// 处理结果 E
	private String errorinfo;// 异常信息
	private String uploadStatus;// 提交状态 Y/N

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSapAccount() {
		return sapAccount;
	}

	public void setSapAccount(String sapAccount) {
		this.sapAccount = sapAccount;
	}

	public String getSendsys() {
		return sendsys;
	}

	public void setSendsys(String sendsys) {
		this.sendsys = sendsys;
	}

	public String getReceivesys() {
		return receivesys;
	}

	public void setReceivesys(String receivesys) {
		this.receivesys = receivesys;
	}

	public String getApiname() {
		return apiname;
	}

	public void setApiname(String apiname) {
		this.apiname = apiname;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public String getErrorinfo() {
		return errorinfo;
	}

	public void setErrorinfo(String errorinfo) {
		this.errorinfo = errorinfo;
	}

	public String getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Logs(String id, String sapAccount, String sendsys, String receivesys, String apiname, String starttime,
			String endtime, String data, String res, String errorinfo, String uploadStatus) {
		super();
		this.id = id;
		this.sapAccount = sapAccount;
		this.sendsys = sendsys;
		this.receivesys = receivesys;
		this.apiname = apiname;
		this.starttime = starttime;
		this.endtime = endtime;
		this.data = data;
		this.res = res;
		this.errorinfo = errorinfo;
		this.uploadStatus = uploadStatus;
	}

	public Logs() {
		super();
	}
}
