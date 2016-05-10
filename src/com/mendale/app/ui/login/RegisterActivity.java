package com.mendale.app.ui.login;

import java.util.Timer;
import java.util.TimerTask;

import com.mendale.app.R;
import com.mendale.app.pojo.MyUser;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.utils.CheckUtils;
import com.mendale.app.utils.DialogUtil;
import com.umeng.socialize.utils.Log;

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
import cn.bmob.v3.listener.SaveListener;

/**
 * 注册
 * 
 * @author zhangxue
 * @date 2015-12-14
 */
public class RegisterActivity extends BaseActivity implements OnClickListener, OnTouchListener, TextWatcher {

	/** 用户名 */
	private EditText etUserName;
	private EditText etPhone;
	private EditText etComfirmPsd;
	
	/** 密码 */
	private EditText etPassword;
	/** 注册 */
	private Button btnRegister;
	/** 提示正在注册的对话框 */
	private Dialog loading;
	private int time = 60;
	private Timer timer = new Timer();
	private TimerTask task;
	
	private String username;
	private String password;
	private String comfirmPsd;
	private String phone;

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
		etUserName = (EditText) findViewById(R.id.register_et_username);
		etPassword = (EditText) findViewById(R.id.register_et_pwd);
		btnRegister = (Button) findViewById(R.id.register_btn_confirm);
		etComfirmPsd=(EditText)findViewById(R.id.register_et_comfirm_psd);
		etPhone=(EditText)findViewById(R.id.register_et_phone_number);
	}

	/**
	 * 绑定事件
	 */
	private void bindEvent() {
		btnRegister.setOnClickListener(this);
		etUserName.setOnTouchListener(this);
		etPassword.setOnTouchListener(this);
		etUserName.addTextChangedListener(this);
		etPassword.addTextChangedListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
			case R.id.register_btn_confirm:// 注册
				username = etUserName.getText().toString();
				password = etPassword.getText().toString();
				comfirmPsd = etComfirmPsd.getText().toString();
				phone=etPhone.getText().toString();
				if (username.equals("") || password.equals("")
						|| comfirmPsd.equals("") || phone.equals("")) {
					Toast.makeText(this,"亲, 不填完整, 不能拿到身份证, ~~~~(>_<)~~~~ ",Toast.LENGTH_SHORT).show();
				} else if (!comfirmPsd.equals(password)) {
					Toast.makeText(this,"亲, 说你手抖了下, 两次密码输入不一致",Toast.LENGTH_SHORT).show();
				} else if(!CheckUtils.checkPhone(this, phone, etPhone)) {
					Toast.makeText(this,"亲, 请输入正确的手机号码",Toast.LENGTH_SHORT).show();
				}else {
					// 开始提交注册信息
					MyUser bu = new MyUser();
					bu.setUsername(username);
					bu.setPassword(password);
					bu.setMobilePhoneNumber(phone);
					bu.signUp(this, new SaveListener() {
						@Override
						public void onSuccess() {
							Toast.makeText(RegisterActivity.this,"亲, 拿到身份证了, 一起GoGoGo",Toast.LENGTH_SHORT).show();
							Intent backLogin = new Intent(RegisterActivity.this,
									LoginActivity.class);
							startActivity(backLogin);
							RegisterActivity.this.finish();
						}

						@Override
						public void onFailure(int arg0, String msg) {
							if(msg.equals("mobilePhoneNumber Must be valid mobile number")){
								Toast.makeText(RegisterActivity.this,"手机号不符合规范.",Toast.LENGTH_SHORT).show();
							}
							Toast.makeText(RegisterActivity.this,"亲, 被人捷足先登了, 换个名字吧.",Toast.LENGTH_SHORT).show();
						}

					});
				}
				break;
			default:
				break;
		}
	}
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
			case R.id.login_et_password:
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					String name = etUserName.getText().toString().trim();
					CheckUtils.checkPhone(this, name, etUserName);
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
		intent.putExtra("username", etUserName.getText().toString().trim());
		intent.putExtra("password", etPassword.getText().toString().trim());
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
		if (!"".equals(etUserName.getText().toString().trim()) && !"".equals(etPassword.getText().toString().trim())) {
			btnRegister.setBackgroundResource(R.drawable.bg_next_off);
		}
		else {
			btnRegister.setBackgroundResource(R.drawable.bg_next_off_50);
		}
	}
}
