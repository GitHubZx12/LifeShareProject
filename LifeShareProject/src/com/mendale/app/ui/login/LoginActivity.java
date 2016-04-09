package com.mendale.app.ui.login;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mendale.app.R;
import com.mendale.app.application.MobileApplication;
import com.mendale.app.pojo.LoginUser;
import com.mendale.app.pojo.ProfileInfo;
import com.mendale.app.services.ProfileInfoService;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.ui.home.MainPageActivity;
import com.mendale.app.ui.mycenter.setting.MarkManActivity;
import com.mendale.app.utils.ClearEditText;
import com.mendale.app.utils.ExitApplication;
import com.mendale.app.utils.NetWorkUtils;
import com.mendale.app.utils.ViewUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.AvoidXfermode.Mode;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;

/**
 * 登录
 * 
 * @author wenjian
 * 
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

	/** 用户名 */
	private ClearEditText username;
	/** 密码 */
	private ClearEditText password;
	/** 登录 */
	private Button login;
	private LinearLayout qqlogin;
	private LinearLayout wxlogin;
	private LinearLayout sinalogin;
	/** 提示正在登录的对话框 */
	private Dialog loading;
	/** 第三方 */
	private UMShareAPI mShareAPI = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		// 加入全局退出队列
		ExitApplication.getInstance().addAllActivity(this);
		initHeaderView();
		initView();
		initData();
		// 初始化
		mShareAPI = UMShareAPI.get(this);
	}

	/**
	 * 公共的header
	 */
	private void initHeaderView() {
		setNavigationTitle("登录");
		setNavigationLeftBtnText("返回");
		setNavigationLeftBtnImage(R.drawable.icon_back);
		setNavigationRightBtnText("注册");
	}

	@Override
	public void leftButtonOnClick() {
		LoginActivity.this.finish();
		super.leftButtonOnClick();
	}

	@Override
	public void rightButtonOnClick() {
		startActivity(RegisterActivity.class);
		finish();
		super.rightButtonOnClick();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		Intent intent = getIntent();
		if (intent.getExtras() != null) {
			username.setText(intent.getStringExtra("username"));
			password.setText(intent.getStringExtra("password"));
		}
	}

	/**
	 * 绑定控件
	 */
	private void initView() {
		username = (ClearEditText) findViewById(R.id.login_et_username);
		password = (ClearEditText) findViewById(R.id.login_et_password);
		login = (Button) findViewById(R.id.login_btn_login);
		qqlogin = (LinearLayout) findViewById(R.id.qqlogin);
		wxlogin = (LinearLayout) findViewById(R.id.wxlogin);
		sinalogin = (LinearLayout) findViewById(R.id.sinalogin);
		login.setOnClickListener(new MyOnClickListener());
		sinalogin.setOnClickListener(this);
	}

	public class MyOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.login_btn_login:
					// 过滤重复点击
					if (ViewUtil.isFastDoubleClick()) {
						// 重复点击了
						return;
					}
					// 检查网络
					if (!NetWorkUtils.isNetworkAvailable(LoginActivity.this)) {
						// 无网络不操作
						showToast("网络不存在,无法操作!");
						return;
					}
					// 用户名密码输入状态
					if (username.getText().toString().equals("")) {
						username.setError("用户名不能为空!");
					}
					else if (password.getText().toString().equals("")) {
						password.setError("密码不能为空!");
					}
					else {
						showProgressDialog("正在登陆中...", "登录提示");
						loginM();
					}
					break;
				default:
					break;
			}
		}
	};

	public void onClickAuth(View view) {
		SHARE_MEDIA platform = null;
		if (view.getId() == R.id.sinalogin) {// 新浪授权
			platform = SHARE_MEDIA.SINA;
		}
		else if (view.getId() == R.id.wxlogin) {// 微信
			platform = SHARE_MEDIA.RENREN;
		}
		else if (view.getId() == R.id.qqlogin) {// qq
			platform = SHARE_MEDIA.DOUBAN;
		}
		/** 开始授权 **/
		mShareAPI.doOauthVerify(LoginActivity.this, platform, umAuthListener);
		/** 获取个人信息 */
		mShareAPI.getPlatformInfo(LoginActivity.this, platform, umAuthListener);
	}

	/**
	 * 授权的 callback interface
	 */
	private UMAuthListener umAuthListener = new UMAuthListener() {

		@Override
		public void onError(SHARE_MEDIA arg0, int arg1, Throwable arg2) {
			Toast.makeText(getApplicationContext(), "授权失败", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
			Toast.makeText(getApplicationContext(), "授权成功", Toast.LENGTH_SHORT).show();
			if (data != null) {
				Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(LoginActivity.this, MarkManActivity.class);
				intent.putExtra("data", data.toString());
				startActivity(intent);
			}
		}

		@Override
		public void onCancel(SHARE_MEDIA arg0, int arg1) {
			Toast.makeText(getApplicationContext(), "授权取消", Toast.LENGTH_SHORT).show();
		}
	};
	/**
	 * 删除授权的 callback interface
	 * 
	 */
	private UMAuthListener umdelAuthListener = new UMAuthListener() {

		@Override
		public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
			Toast.makeText(getApplicationContext(), "delete Authorize succeed", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onError(SHARE_MEDIA platform, int action, Throwable t) {
			Toast.makeText(getApplicationContext(), "delete Authorize fail", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel(SHARE_MEDIA platform, int action) {
			Toast.makeText(getApplicationContext(), "delete Authorize cancel", Toast.LENGTH_SHORT).show();
		}
	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mShareAPI.onActivityResult(requestCode, resultCode, data);
	};

	/**
	 * 登录方法 (请求网路的)
	 */
	// private void loginM() {
	// // 用户信息上传，封装到map集合，传递到接口中
	// final Map<String, String> map2 = new HashMap<String, String>();
	// map2.put("loginUpload.bname", username.getText().toString());
	// map2.put("loginUpload.password", password.getText().toString());
	// Thread thread = new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// // 输入客户信息并登录
	// LoginTask loginTask = new LoginTask(LoginActivity.this);
	// String logReturn = loginTask.sendLogin(map2, "UTF-8");
	//// String logReturn = "S";
	// if (logReturn.equals("S")) {
	// Message msg = handler.obtainMessage();
	// msg.what = 1;
	// msg.sendToTarget();
	// }
	// else {
	// Message msg = handler.obtainMessage();
	// msg.what = 2;
	// Bundle bundle = new Bundle();
	// bundle.putString("prompt", logReturn);
	// msg.setData(bundle);
	// msg.sendToTarget();
	// }
	// }
	// });
	// thread.start();
	// }
	/**
	 * 登陆方法，先用bmob模拟一下
	 */
	private void loginM() {
		
		//查找Person表里面id为6b6c11c537的数据
		BmobQuery<LoginUser> bmobQuery = new BmobQuery<LoginUser>();
		bmobQuery.getObject(this, username.getText().toString(), new GetListener<LoginUser>() {
		    @Override
		    public void onSuccess(LoginUser object) {
		    	Message msg = handler.obtainMessage();
				// msg.what = 1;
				// msg.sendToTarget();
				Toast.makeText(LoginActivity.this, "查询成功", Toast.LENGTH_SHORT).show();
		    }

		    @Override
		    public void onFailure(int code, String msg) {
		    	// Message msg = handler.obtainMessage();
				// msg.what = 2;
				// Bundle bundle = new Bundle();
				// bundle.putString("prompt", "不存在此用户");
				// msg.setData(bundle);
				// msg.sendToTarget();
				Toast.makeText(LoginActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
		    }
		});
		
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					// 成功
					dismissProgressDialog();
					showToast("登陆成功!");
					// 保存用户信息
					String sapaccount = username.getText().toString();// 用户名
					String psd = password.getText().toString();// 密码
					SharedPreferences sp = LoginActivity.this.getSharedPreferences("user", MODE_PRIVATE);
					String name = sp.getString("userName", null);
					String pwd = sp.getString("password", null);
					// 没有,则保存
					if (TextUtils.isEmpty(name) && TextUtils.isEmpty(pwd)) {
						// 获取application
						Application application = LoginActivity.this.getApplication();
						MobileApplication mApplication = (MobileApplication) application;
						// 得到平台账号密码
						LoginUser loginUser = new LoginUser();
						loginUser.setUserName(name);
						loginUser.setPassword(pwd);
						Editor edit = sp.edit();
						edit.putString("userName", name);
						edit.putString("password", pwd);
						// 插入后取出存application
						mApplication.setmUserInfo(loginUser);
					}
					else {
						// 存入application;
						Application application = LoginActivity.this.getApplication();
						MobileApplication mApplication = (MobileApplication) application;
						// 如果数据库里有这个账号的信息 则取出存入application;
						LoginUser loginUser = new LoginUser();
						loginUser.setUserName(name);
						loginUser.setPassword(pwd);
						mApplication.setmUserInfo(loginUser);
					}
					// 跳转到主页
					startActivity(MainPageActivity.class);
					LoginActivity.this.finish();
					break;
				case 2:
					// 失败
					dismissProgressDialog();
					Bundle bundle = msg.getData();
					String promptTitle = bundle.getString("prompt");
					if (isAPPRunning(LoginActivity.this)) {
						// 解决弹框无界面报错问题
						showPopDialog("登录失败:" + promptTitle);
					}
					else {
						// 当前界面不是弹框界面
						showToast("登录失败:" + promptTitle);
					}
					break;
				default:
					dismissProgressDialog();
					break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.sinalogin:
				onClickAuth(v);
				break;
			case R.id.webView:
				onClickAuth(v);
				break;
			case R.id.qqlogin:
				onClickAuth(v);
				break;
		}
	}
}
