package com.mendale.app.exception;

import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.mendale.app.R;
import com.mendale.app.application.MobileApplication;
import com.mendale.app.pojo.Logs;
import com.mendale.app.services.LogsService;
import com.mendale.app.utils.ExitApplication;

/**
 * 异常处理类 线程未捕获异常控制器 UncaughtException处理类,当程序发生Uncaught异常的时候,由该类来接管程序,并记录发送错误报告.
 * 
 * UncaughtExceptionHandler底层类
 * @author wenjian
 * 
 */
public class CrashHandler implements UncaughtExceptionHandler {

	/** Debug Log Tag */
	public static final String TAG = "CrashHandler";
	/** 是否开启日志输出, 在Debug状态下开启, 在Release状态下关闭以提升程序性能 */
	public static final boolean DEBUG = true;
	/** CrashHandler实例 */
	private static CrashHandler INSTANCE;
	/** 程序的Context对象 */
	private Context mContext;
	/** 系统默认的UncaughtException处理类 */
	private Thread.UncaughtExceptionHandler mDefaultHandler;

	/** 保证只有一个CrashHandler实例 */
	private CrashHandler() {
	}

	/** 获取CrashHandler实例 ,单例模式 */
	public static CrashHandler getInstance() {
		if (INSTANCE == null)
			INSTANCE = new CrashHandler();
		return INSTANCE;
	}

	/**
	 * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
	 * 
	 * @param ctx
	 */
	public void init(Context ctx) {
		mContext = ctx;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			mDefaultHandler.uncaughtException(thread, ex);
		}
		else {
			// Sleep一会后结束程序
			// 来让线程停止一会是为了显示Toast信息给用户，然后Kill程序
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Log.e(TAG, "Error : ", e);
			}
			// 全局推出
			ExitApplication.getInstance().exitAll();
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 可以根据自己的情况来自定义异常处理逻辑
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return true;
		}
		/*错误信息*/
		String msg1 = "";
		if (ex.getStackTrace() != null) {
			msg1 += "异常信息:[";
			int kk = ex.getStackTrace().length;
			for (int i = 0; i < kk; i++) {
				msg1 += ex.getStackTrace()[i] + "\r\n";
			}
			msg1 += " .]" + "\r\n";
		}
		if (ex.getLocalizedMessage() != null) {
			msg1 += "出现错误:[" + ex.getLocalizedMessage() + " .]" + "\r\n";
		}
		if (ex.getCause() != null) {
			msg1 += "错误原因:[" + ex.getCause() + " \r\n.]";
		}
		msg1 += "捕获异常:[" + ex.toString() + " .]" + "\r\n";
		// 收集设备信息
		msg1 += "\r\n" + collectCrashDeviceInfo(mContext);
		System.out.println(msg1);
		// 保存到数据库
		saveCrashInfoToDB(msg1);
		// 执行
		final String msg = msg1;
		// 使用Toast来显示异常信息
		new Thread() {

			@Override
			public void run() {
				// 方案1 Toast 显示需要出现在一个线程的消息队列中
				Looper.prepare();
				showToast(msg);
				Looper.loop();
			}
		}.start();
		// 保存错误报告信息
		return true;
	}

	/**
	 * 自定义弹出toast
	 * 
	 * @param text
	 */
	public void showToast(String text) {
		Toast toast = new Toast(mContext);
		TextView textView = new TextView(mContext);
		textView.setText("抱歉,程序出错,即将关闭!错误报告将发送给后台管理员!");
		textView.setTextSize(16);
		textView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.toast_bg));
		textView.setTextColor(Color.WHITE);
		textView.setGravity(Gravity.LEFT);
		textView.setPadding(15, 15, 15, 15);
		toast.setView(textView);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.show();
	}

	/**
	 * 收集程序崩溃的设备信息
	 * 
	 * @param ctx
	 */
	public String collectCrashDeviceInfo(Context ctx) {
		String tagString = "";
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				tagString += "app版本号:";
				tagString += pi.versionName == null ? "not set" : pi.versionName + "\n";
				tagString += "app版本:" + pi.versionCode + "\n";
			}
		} catch (NameNotFoundException e) {
			Log.e(TAG, "Error while collect package info", e);
		}
		// 使用反射来收集设备信息.在Build类中包含各种设备信息,
		// 例如: 系统版本号,设备生产商 等帮助调试程序的有用信息
		// 具体信息请参考后面的截图
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				tagString += field.getName() + ":" + field.get(null) + "\n";
				if (DEBUG) {
					Log.d(TAG, field.getName() + " : " + field.get(null));
				}
			} catch (Exception e) {
				Log.e(TAG, "Error while collect crash info", e);
			}
		}
		return tagString;
	}

	/**
	 * 保存错误信息到数据库中
	 * 
	 * @param ex
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	private boolean saveCrashInfoToDB(String ex) {
		boolean tag = false;
		Logs logs = new Logs();
		MobileApplication mobileApplication = (MobileApplication) mContext.getApplicationContext();
		if (mobileApplication.getmUserInfo() == null) {
			logs.setSapAccount("");// 调用者账号
		}
		else {
			logs.setSapAccount(mobileApplication.getmUserInfo().getUsername());// 调用者账号
		}
		logs.setSendsys("PDA");// 发送系统
		logs.setReceivesys("MID"); // 接收系统
		ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
		ComponentName cn = null;
		try {
			cn = am.getRunningTasks(1).get(0).topActivity;
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		logs.setApiname(cn.getClassName() + ""); // 接口名称
		logs.setStarttime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));// 开始时间
		logs.setEndtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));// 结束时间
		logs.setData("");// 传输数据
		logs.setRes("E");// 处理结果
		logs.setErrorinfo("程序异常退出:" + ex);// 异常信息
		logs.setUploadStatus("N");// 提交状态 Y/N
		try {
			// 防止记日志时出错 如果报错,则不保存 避免因为初始化db造成的异常无法处理的情况
			tag = LogsService.getInstance(mContext).insert(logs);
		} catch (Exception e) {
			tag = true;
		}
		return tag;
	}
}