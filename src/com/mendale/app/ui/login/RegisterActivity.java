package com.mendale.app.ui.login;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.mendale.app.R;
import com.mendale.app.pojo.LoginUser;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.utils.CheckUtils;
import com.mendale.app.utils.DialogUtil;

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
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 注册
 * 
 * @author zhangxue
 * @date 2015-12-14
 */
public class RegisterActivity extends BaseActivity implements OnClickListener, OnTouchListener, TextWatcher {

	/** 用户名 */
	private EditText userName;
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
		password = (EditText) findViewById(R.id.register_et_pwd);
		register = (Button) findViewById(R.id.register_btn_confirm);
	}

	/**
	 * 绑定事件
	 */
	private void bindEvent() {
		register.setOnClickListener(this);
		userName.setOnTouchListener(this);
		password.setOnTouchListener(this);
		userName.addTextChangedListener(this);
		password.addTextChangedListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
			case R.id.register_btn_confirm:// 注册
				final String name = userName.getText().toString();// 获得用户输入的用户名
				String p1 = password.getText().toString();// 获得用户输入的密码
				if (CheckUtils.checkPwd(this, p1, password)) {
					// 与已经存在的用户进行判断（不可重复）
					BmobQuery<LoginUser> bmobQuery = new BmobQuery<LoginUser>();
					bmobQuery.addWhereEqualTo("userName", name);
					bmobQuery.findObjects(this, new FindListener<LoginUser>() {
						
						@Override
						public void onSuccess(List<LoginUser> arg0) {
							for(int i=0;i<arg0.size();i++){
								if(name.equals(arg0.get(i).getUserName())){
									Toast.makeText(RegisterActivity.this,"用户名已经存在", Toast.LENGTH_SHORT).show();
									userName.setText(null);
									password.setText(null);
									return;
								}
							}
							//跳转
							Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
							intent.putExtra("username", userName.getText().toString());
							intent.putExtra("password", password.getText().toString());
							startActivity(intent);
							//保存到bmob云端
							LoginUser user=new LoginUser();
							user.setUserName(userName.getText().toString());
							user.setPassword(password.getText().toString());
							user.save(RegisterActivity.this, new SaveListener() {
								
								@Override
								public void onSuccess() {
									showToast("注册成功");
								}
								
								@Override
								public void onFailure(int arg0, String arg1) {
									showToast("注册失败");
								}
							});
							
						}
						
						@Override
						public void onError(int arg0, String arg1) {
							
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
					String name = userName.getText().toString().trim();
					CheckUtils.checkPhone(this, name, userName);
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
