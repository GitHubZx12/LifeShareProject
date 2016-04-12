package com.mendale.app.constants;

import android.annotation.SuppressLint;

import com.mendale.app.database.SdCardPathUtil;

/**
 * 接口请求的地址等一些基本信息
 * 
 * @author wenjian
 * 
 */
@SuppressLint("SdCardPath")
public class URLS {

	/**
	 * SD卡根目录
	 */
	public static String SDCARD_DIR = SdCardPathUtil.getSDPath() + "/";
	/**
	 * 当前APK的版本号
	 */
	public static String APK_CODE = "";
	/**
	 * 当前APK的编号
	 */
	public static String APK_VERSION = "";
	// /////////////////////////////////////////////////////////////////////////////
	/**
	 * 当前数据库DB的版本 初始是2
	 */
	public static int DB_VERSION = 2;
	/**
	 * 数据库名称
	 */
	// public static final String DATABASE_NAME = "dev_db";//开发
	public static final String DATABASE_NAME = "test_db1";// 测试
	// public static final String DATABASE_NAME = "db";//生产
	/**
	 * 软件标识
	 */
	// public static final String CURR_VERSION = "开发版1";//开发
	public static final String CURR_VERSION = "测试版1";// 测试
	// public static final String CURR_VERSION = "正式版1";//生产
	/**
	 * 检查APK新版本的url
	 */
	// public static String APK_CHECK_URL="http://...";//开发
	public static String APK_CHECK_URL = "http://...";// 测试
	// public static String APK_CHECK_URL="http://...";//生产
	/**
	 * 新版本apk下载url
	 */
	// public static String APK_DOWNLOAD_URL="http://...";//开发
	public static String APK_DOWNLOAD_URL = "http://...";// 测试
	// public static String APK_DOWNLOAD_URL="http://...";//生产
	/**
	 * 请求服务的IP地址
	 */
	// public static String SERVER_IP="10.0.0.1";//服务器地址（开发）
	public static String SERVER_IP = "10.0.0.1";// PC地址（测试）
	// public static String SERVER_IP="10.0.0.1";//PC地址（生产）
	/**
	 * 请求服务的端口号
	 */
	// public static String SERVER_PORT="8080";//服务器端口（开发）
	public static String SERVER_PORT = "8080";// 服务器端口（测试）
	// public static String SERVER_PORT="8080";//服务器端口（生产）
	// /////////////////////////////////////////////////////////////////////////////
	/**
	 * 请求服务的前缀
	 */
	public static String SERVER_PRE = "http://";
	/**
	 * 请求服务的服务名
	 */
	public static String SERVER_NAME = "pda";// 服务器地址

	/**
	 * 拼接请求服务器地址的方法
	 */
	public static String getRequestServerUrl() {
		StringBuffer buffer = new StringBuffer("");
		buffer.append(SERVER_PRE);
		buffer.append(SERVER_IP);
		buffer.append(":");
		buffer.append(SERVER_PORT);
		buffer.append("/");
		buffer.append(SERVER_NAME);
		buffer.append("/");
		return buffer.toString();
	}

	/******************************* 请求服务的模块名以及方法名 ******************************/
	/**
	 * sap账号密码登录
	 */
	public static String LOGIN_ACTION = "login";
	/**
	 * sap密码修改
	 */
	public static String PASSWORD_ACTION = "passwordmodify";
	/****************************** 其它相关地址 *****************************/
	
	
	
	/******************************* 一些常量信息 ******************************/
	/**
	 * 请求成功
	 */
	public static String REQUEST_SUCCESS = "S";
	/**
	 * 请求失败
	 */
	public static String REQUEST_FAILED = "E";
	/**
	 * 请求返回值--成功
	 */
	public static int RETURN_SUCCESS = 1;
	/**
	 * 请求返回值--失败
	 */
	public static int RETURN_ERROR = -1;
	/******************************* 发送短信倒计时 *************************************/
	/**
	 * 倒计时长 60秒
	 */
	public static int TIME_COUNT = 60000;
	
	/******************************* 记录日志类 *************************************/
	/**
	 * 消息查询 日志
	 */
	public static boolean FLAG_MESSAGE_EXCEPTION_LOG = false;

}
