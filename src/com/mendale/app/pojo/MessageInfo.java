package com.mendale.app.pojo;

import java.io.Serializable;

/**
 * 26,消息记录表
 * 
 * @author wenjian
 * 
 */
public class MessageInfo implements Serializable {

	private static final long serialVersionUID = 8720242084119892687L;
	private String id;// id
	private String msgCode;// 消息编号
	private String msgDate;// 消息日期 yyyy-MM-dd HH:mm
	private String msgContent;// 消息内容
	private String msgReceiver;// 接收者 员工编号 可能是工厂
	private String msgTypeDesc;// 消息类型描述
	private String msgType;// 消息类型
	private String viewStatus;// 查看状态 已读1/未读0
	private String taskNo;// 单号
	private String taskType;// 单据类型 工单/通知单G/T
	private String pernr;// 用户编码 一个sap账号可能会有多个员工编号
	private String updateTable;// (基础下载)更新的表名 改一个表产生一条消息

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public String getMsgDate() {
		return msgDate;
	}

	public void setMsgDate(String msgDate) {
		this.msgDate = msgDate;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getMsgReceiver() {
		return msgReceiver;
	}

	public void setMsgReceiver(String msgReceiver) {
		this.msgReceiver = msgReceiver;
	}

	public String getMsgTypeDesc() {
		return msgTypeDesc;
	}

	public void setMsgTypeDesc(String msgTypeDesc) {
		this.msgTypeDesc = msgTypeDesc;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getViewStatus() {
		return viewStatus;
	}

	public void setViewStatus(String viewStatus) {
		this.viewStatus = viewStatus;
	}

	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getPernr() {
		return pernr;
	}

	public void setPernr(String pernr) {
		this.pernr = pernr;
	}

	public String getUpdateTable() {
		return updateTable;
	}

	public void setUpdateTable(String updateTable) {
		this.updateTable = updateTable;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public MessageInfo(String id, String msgCode, String msgDate, String msgContent, String msgReceiver,
			String msgTypeDesc, String msgType, String viewStatus, String taskNo, String taskType, String pernr,
			String updateTable) {
		super();
		this.id = id;
		this.msgCode = msgCode;
		this.msgDate = msgDate;
		this.msgContent = msgContent;
		this.msgReceiver = msgReceiver;
		this.msgTypeDesc = msgTypeDesc;
		this.msgType = msgType;
		this.viewStatus = viewStatus;
		this.taskNo = taskNo;
		this.taskType = taskType;
		this.pernr = pernr;
		this.updateTable = updateTable;
	}

	public MessageInfo(String msgDate, String msgContent, String msgTypeDesc) {
		super();
		this.msgDate = msgDate;
		this.msgContent = msgContent;
		this.msgTypeDesc = msgTypeDesc;
	}

	public MessageInfo(String id, String msgDate, String msgContent, String msgTypeDesc) {
		super();
		this.id = id;
		this.msgDate = msgDate;
		this.msgContent = msgContent;
		this.msgTypeDesc = msgTypeDesc;
	}

	public MessageInfo() {
		super();
	}
}
