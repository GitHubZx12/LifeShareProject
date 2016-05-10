package com.mendale.app.ui.login;

import java.util.Map;
import java.util.Set;

import com.mendale.app.R;
import com.mendale.app.application.MobileApplication;
import com.mendale.app.pojo.MyUser;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.ui.home.MainPageActivity;
import com.mendale.app.utils.ClearEditText;
import com.mendale.app.utils.ExitApplication;
import com.mendale.app.utils.NetWorkUtils;
import com.mendale.app.utils.ViewUtil;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.bmob.v3.listener.SaveListener;

/**
 * 登录
 * 
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

	/** 用户名 */
	private ClearEditText etUsername;
	/** 密码 */
	private ClearEditText etPassword;
	/** 登录 */
	private Button btnLogin;
	private LinearLayout qqlogin;
	private LinearLayout wxlogin;
	private LinearLayout sinalogin;
	/** 提示正在登录的对话框 */
	private Dialog loading;
	/** 友盟第三方登录 */
	private UMSocialService mController;
	//
	private String username;
	private String password;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		ExitApplication.getInstance().addAllActivity(this);
		initHeaderView();
		initView();
		initData();
		mController = UMServiceFactory.getUMSocialService("com.umeng.login");
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
		if (intent.getStringExtra("username") != null) {
			etUsername.setText(intent.getStringExtra("username"));
			etPassword.setText(intent.getStringExtra("password"));
		}
	}

	/**
	 * 绑定控件
	 */
	private void initView() {
		etUsername = (ClearEditText) findViewById(R.id.login_et_username);
		etPassword = (ClearEditText) findViewById(R.id.login_et_password);
		btnLogin = (Button) findViewById(R.id.login_btn_login);
		qqlogin = (LinearLayout) findViewById(R.id.qqlogin);
		wxlogin = (LinearLayout) findViewById(R.id.wxlogin);
		sinalogin = (LinearLayout) findViewById(R.id.sinalogin);
		btnLogin.setOnClickListener(new MyOnClickListener());
		sinalogin.setOnClickListener(this);
		
		getUserInfo();
	}

	/**
	 * 获取用户信息
	 */
	private void getUserInfo() {
		SharedPreferences sp = getSharedPreferences("UserInfo", 0);
		etUsername.setText(sp.getString("username", null));
		etPassword.setText(sp.getString("password", null));
	}
	
	/**
	 * 保存用户的登陆记录
	 * @param username
	 * @param password
	 */
		private void saveUserInfo(String username, String password) {
			SharedPreferences sp = getSharedPreferences("UserInfo", 0);
			Editor editor = sp.edit();
			editor.putString("username", username);
			editor.putString("password", password);
			editor.commit();
			
			// 获取application
			Application application = LoginActivity.this.getApplication();
			MobileApplication mApplication = (MobileApplication) application;
			// 得到平台账号密码
			MyUser user = new MyUser();
			user.setUsername(username);
			user.setPassword(password);
			mApplication.setmUserInfo(user);
			
		}

	public class MyOnClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.login_btn_login:
					// 过滤重复点击
					if (ViewUtil.isFastDoubleClick()) {// 重复点击了
						return;
					}
					// 检查网络
					if (!NetWorkUtils.isNetworkAvailable(LoginActivity.this)) {// 无网络不操作
						showToast("亲, 木有网络 ( ⊙ o ⊙ ) ");
						return;
					}
					// 用户名密码输入状态
					if (etUsername.getText().toString().equals("")) {
						etUsername.setError("亲, 请输入用戶名");
					}
					else if (etPassword.getText().toString().equals("")) {
						etPassword.setError("密码不能为空!");
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

	/**
	 * 登陆方法，bmob
	 */
	private void loginM() {
		username = etUsername.getText().toString();
		password = etPassword.getText().toString();
			MyUser user = new MyUser();
			user.setUsername(username);
			user.setPassword(password);
			user.login(this, new SaveListener() {
				@Override
				public void onSuccess() {
					// 登陆成功
					dismissProgressDialog();
					//保存用户信息
					saveUserInfo(username, password);
					// 跳转到主页
					startActivity(MainPageActivity.class);
					LoginActivity.this.finish();
				}

				@Override
				public void onFailure(int arg0, String msg) {
					showToast("亲, 用户名或密码错误");
					dismissProgressDialog();
				}
			});
	}

	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.sinalogin:// 新浪维保
				loginSina();
				break;
			case R.id.webView:// 微信
				loginWeiXin();
				break;
			case R.id.qqlogin:// qq
				loginQQ();
				break;
		}
	}

	/**
	 * 微博登陆
	 */
	private void loginSina() {
		// 配置sina SSO(免登录)开关
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		// 授权接口
		mController.doOauthVerify(this, SHARE_MEDIA.SINA, new UMAuthListener() {

			@Override
			public void onStart(SHARE_MEDIA arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onError(SocializeException arg0, SHARE_MEDIA arg1) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onComplete(Bundle value, SHARE_MEDIA arg1) {
				if (value != null && !TextUtils.isEmpty(value.getString("uid"))) {
					Toast.makeText(LoginActivity.this, "授权成功.", Toast.LENGTH_SHORT).show();
					getSinaData();
				}
				else {
					Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onCancel(SHARE_MEDIA arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	/**
	 * 获得新浪微博的相关信息
	 */
	private void getSinaData() {
		mController.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.SINA, new UMDataListener() {

			@Override
			public void onStart() {
				Toast.makeText(LoginActivity.this, "获取平台数据开始...", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onComplete(int status, Map<String, Object> info) {
				if (status == 200 && info != null) {
					StringBuilder sb = new StringBuilder();
					Set<String> keys = info.keySet();
					for (String key : keys) {
						sb.append(key + "=" + info.get(key).toString() + "\r\n");
					}
					Log.d("TestData", sb.toString());
				}
				else {
					Log.d("TestData", "发生错误：" + status);
				}
			}
		});
	}

	private void loginWeiXin() {
		// TODO Auto-generated method stub
	}

	private void loginQQ() {
		// TODO Auto-generated method stub
	}
}
