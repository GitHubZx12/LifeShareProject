package com.mendale.app.pojo;

import java.io.Serializable;

/**
 * 25,配置记录表
 * 
 * @author wenjian
 * 
 */
public class ProfileInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8720242084119892687L;
	private String id;// id
	private String mainPage;// 初始页配置
	private String auart;// 优先业务配置 安检/维修/置换/零星/零星一站式/零散/零散一站式/移装 (业务类型编号)
	private String currentVer;// 当前APP版本
	private String newVer;// 最新版本号
	private String platformAccount;// 平台账号
	private String platformPass;// 平台密码
	private String sapAccount;// sap账号
	private String sapPass;// sap密码
	private String patternsPass;// 图案密码
	private String userName;// 用户姓名   --此处保存用户编号
	private String userTel;// 用户电话
	private String skins;// 皮肤选择
	private String searTime;// 轮询时间1
	private String poolTime;// 消息保留时间2
	private String scheduleTime;// 定时时间3
	private String lastSyncDate;// 消息上一次获取时间
	private String ext1;// 预留字段1 新版本是否清除数据
	private String ext2;// 预留字段2
	private String ext3;// 预留字段3

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMainPage() {
		return mainPage;
	}

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}

	public String getAuart() {
		return auart;
	}

	public void setAuart(String auart) {
		this.auart = auart;
	}

	public String getCurrentVer() {
		return currentVer;
	}

	public void setCurrentVer(String currentVer) {
		this.currentVer = currentVer;
	}

	public String getNewVer() {
		return newVer;
	}

	public void setNewVer(String newVer) {
		this.newVer = newVer;
	}

	public String getPlatformAccount() {
		return platformAccount;
	}

	public void setPlatformAccount(String platformAccount) {
		this.platformAccount = platformAccount;
	}

	public String getPlatformPass() {
		return platformPass;
	}

	public void setPlatformPass(String platformPass) {
		this.platformPass = platformPass;
	}

	public String getSapAccount() {
		return sapAccount;
	}

	public void setSapAccount(String sapAccount) {
		this.sapAccount = sapAccount;
	}

	public String getSapPass() {
		return sapPass;
	}

	public void setSapPass(String sapPass) {
		this.sapPass = sapPass;
	}

	public String getPatternsPass() {
		return patternsPass;
	}

	public void setPatternsPass(String patternsPass) {
		this.patternsPass = patternsPass;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getSkins() {
		return skins;
	}

	public void setSkins(String skins) {
		this.skins = skins;
	}

	public String getSearTime() {
		return searTime;
	}

	public void setSearTime(String searTime) {
		this.searTime = searTime;
	}

	public String getPoolTime() {
		return poolTime;
	}

	public void setPoolTime(String poolTime) {
		this.poolTime = poolTime;
	}

	public String getScheduleTime() {
		return scheduleTime;
	}

	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	public String getLastSyncDate() {
		return lastSyncDate;
	}

	public void setLastSyncDate(String lastSyncDate) {
		this.lastSyncDate = lastSyncDate;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ProfileInfo(String id, String mainPage, String auart, String currentVer, String newVer,
			String platformAccount, String platformPass, String sapAccount, String sapPass, String patternsPass,
			String userName, String userTel, String skins, String searTime, String poolTime, String scheduleTime,
			String lastSyncDate, String ext1, String ext2, String ext3) {
		super();
		this.id = id;
		this.mainPage = mainPage;
		this.auart = auart;
		this.currentVer = currentVer;
		this.newVer = newVer;
		this.platformAccount = platformAccount;
		this.platformPass = platformPass;
		this.sapAccount = sapAccount;
		this.sapPass = sapPass;
		this.patternsPass = patternsPass;
		this.userName = userName;
		this.userTel = userTel;
		this.skins = skins;
		this.searTime = searTime;
		this.poolTime = poolTime;
		this.scheduleTime = scheduleTime;
		this.lastSyncDate = lastSyncDate;
		this.ext1 = ext1;
		this.ext2 = ext2;
		this.ext3 = ext3;
	}

	public ProfileInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
}
