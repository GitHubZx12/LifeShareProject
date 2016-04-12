package com.mendale.app.ui.login;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mendale.app.R;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.utils.CheckUtils;
import com.mendale.app.utils.DialogUtil;

/**
 * 注册
 * (第三方的短信验证码实现)
 * @author zhangxue
 * @date 2015-12-14
 */
public class RegisterActivity2 extends BaseActivity implements OnClickListener, OnTouchListener, TextWatcher {

	/** 用户名 */
	private EditText userName;
	/** 验证码 */
	private EditText yzm;
	/** 验证码 */
	private Button btn_yzm;
	/** 密码 */
	private EditText password;
	/** 注册 */
	private Button register;
	/** 提示正在注册的对话框 */
	private Dialog loading;
	private int time = 60;
	private Timer timer = new Timer();
	private TimerTask task;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initHeaderView();
		initView();
		bindEvent();
	}

	/**
	 * header
	 */
	private void initHeaderView() {
		setNavigationTitle("注册");
		setNavigationLeftBtnText("返回");
		setNavigationLeftBtnImage(R.drawable.icon_back);
	}

	/**
	 * back
	 */
	@Override
	public void leftButtonOnClick() {
		finish();
		super.leftButtonOnClick();
	}

	/**
	 * 初始化
	 */
	private void initView() {
		loading = DialogUtil.createLoadingDialog(this, "正在注册请稍后...");
		userName = (EditText) findViewById(R.id.register_et_username);
		yzm = (EditText) findViewById(R.id.register_et_yzm);
		btn_yzm = (Button) findViewById(R.id.register_btn_yzm);
		password = (EditText) findViewById(R.id.register_et_pwd);
		register = (Button) findViewById(R.id.register_btn_confirm);
	}

	/**
	 * 绑定事件
	 */
	private void bindEvent() {
		register.setOnClickListener(this);
		btn_yzm.setOnClickListener(this);
		userName.setOnTouchListener(this);
		password.setOnTouchListener(this);
		userName.addTextChangedListener(this);
		password.addTextChangedListener(this);
		yzm.setOnTouchListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
			case R.id.register_btn_confirm:// 注册
				String name = userName.getText().toString().trim();// 获得用户输入的密码
				if (CheckUtils.checkPhone(this, name, userName)) {
					String p1 = password.getText().toString().trim();// 获得用户输入的密码
					if (CheckUtils.checkPwd(this, p1, password)) {
						@SuppressWarnings("unused")
						String mYzm = yzm.getText().toString().trim();// 获得用户输入的验证码
						if (true) {// 判断验证码是否正确
							loading.show();
//							RegisterTasks tasks = new RegisterTasks(this);
//							tasks.execute(Urls.REGISTER_ACTION, name, p1);
						}
					}
				}
				break;
			case R.id.register_btn_yzm:// 点击获取验证码
				String registerName = userName.getText().toString();
				if (!CheckUtils.checkPhone(this, registerName, userName)) {
					return;
				}
			sendYzm();
				break;
			default:
				break;
		}
	}

	/**
	 * 发送验证码
	 */
	private void sendYzm() {
		task = new TimerTask() {

			@Override
			public void run() {
				runOnUiThread(new Runnable() { // UI thread

					@Override
					public void run() {
						if (time <= 0) {
							btn_yzm.setEnabled(true);
							btn_yzm.setText("获取验证码");
							task.cancel();
						}
						else {
							btn_yzm.setText(time + "S");
						}
						time--;
					}
				});
			}
		};
		time = 60;
		timer.schedule(task, 0, 1000);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
			case R.id.register_et_yzm:
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					String name = userName.getText().toString().trim();
//					CheckUtils.checkPhone(this, name, userName);
				}
				break;
			case R.id.login_et_password:
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					String name = userName.getText().toString().trim();
//					CheckUtils.checkPhone(this, name, userName);
				}
				break;
		}
		return false;
	}

	/**
	 * 注册成功的回掉
	 * 
	 * @param result
	 */
	public void registerSuccess(String result) {
		loading.dismiss();
		Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, LoginActivity.class);
		intent.putExtra("username", userName.getText().toString().trim());
		intent.putExtra("password", password.getText().toString().trim());
		startActivity(intent);
		finish();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterTextChanged(Editable s) {
		if (!"".equals(userName.getText().toString().trim()) && !"".equals(password.getText().toString().trim())) {
			register.setBackgroundResource(R.drawable.bg_next_off);
		}
		else {
			register.setBackgroundResource(R.drawable.bg_next_off_50);
		}
	}
}
