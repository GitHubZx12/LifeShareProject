package com.mendale.app.application;

import java.io.File;

import com.mendale.app.pojo.LoginUser;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.app.Application;
import android.content.Context;

/**
 * 全局变量
 * 
 * 
 * @author wenjian
 * 
 */
public class MobileApplication extends Application {

	private LoginUser mUserInfo;// 用户登录信息
	private String certUserName;// 账号
	private String certPassword;// 密码

	public LoginUser getmUserInfo() {
		return mUserInfo;
	}

	public void setmUserInfo(LoginUser mUserInfo) {
		this.mUserInfo = mUserInfo;
	}

	public String getCertUserName() {
		return certUserName;
	}

	public void setCertUserName(String certUserName) {
		this.certUserName = certUserName;
	}

	public String getCertPassword() {
		return certPassword;
	}

	public void setCertPassword(String certPassword) {
		this.certPassword = certPassword;
	}

	/***
	 * 清除配置文件中的用户
	 */
	public void clear() {
		this.mUserInfo = null;
		this.certUserName = "";
		this.certPassword = "";
	}

	@SuppressWarnings("unused")
	/** 
	 *
	 * 将在Application中注册未捕获异常处理器。 
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		Context context = getApplicationContext();
		//友盟分享
//		PlatformConfig.setSinaWeibo("1168751756","be298319508b86d096c5175fc4d2a47c");
//		CrashHandler crashHandler = CrashHandler.getInstance();
//		// 注册crashHandler
//		crashHandler.init(getApplicationContext());
		initImageLoader(getApplicationContext());
	}
	
	/**
	 * 初始化ImageLoader
	 * @param context
	 */
	public static void initImageLoader(Context context) {
		//缓存文件的目录
		File cacheDir = StorageUtils.getOwnCacheDirectory(context, "imageloader/Cache"); 
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())////将保存的时候的URI名称用MD5 加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.diskCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径  
				.build();
		// 初始化Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}
