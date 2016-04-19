package com.mendale.app.ui;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.Bmob;

import com.mendale.app.R;
import com.mendale.app.constants.URLS;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.ui.login.LoginActivity;
import com.mendale.app.utils.ExitApplication;

/**
 * 初始启动页（渐变的动画效果）
 * 
 */
public class MJAPPActivity extends BaseActivity {

	private Handler handler = new Handler();
	private ImageView logopic;// 白屏图片
	private TextView title;// 标题

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		/**加入全局退出队列------------规范：都要加*/
		ExitApplication.getInstance().addAllActivity(this);
		// 初始化 Bmob SDK
        // 使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
        Bmob.initialize(this, "12c117029928b92959b85e6dae4441d9");
		/** 动画 */
		title = (TextView) findViewById(R.id.title);// 标题title
		title.setText(URLS.CURR_VERSION);// 显示是什么版本
		logopic = (ImageView) findViewById(R.id.logopic);// 找到需要进行动画的图片id
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				MJAPPActivity.this, R.drawable.logo_show);// 载入动画
		logopic.startAnimation(hyperspaceJumpAnimation);// 开始动画
		// 关闭通知栏的提示
		NotificationManager mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// 关闭通知栏
		if (mManager != null) {
			mManager.cancel(0);
		}
		/** 等同于TimeTask的定时跳转 */
		handler.postDelayed(new Runnable() {// 主屏幕跳动同时执行线程

					public void run() {
						startBPSActivity();// 屏幕跳转
					}
				}, 2000L);// 定义跳转屏持续时间
	}

	/**
	 * 屏幕跳转
	 */
	private void startBPSActivity() {
		Intent intent = new Intent(this, LoginActivity.class);
		// 打开新的Activity
		startActivity(intent);
		this.finish();
	}
}