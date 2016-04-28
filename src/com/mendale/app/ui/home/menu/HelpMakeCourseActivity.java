package com.mendale.app.ui.home.menu;

import com.mendale.app.R;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.utils.ExitApplication;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 求教程
 * @author Administrator
 *
 */
public class HelpMakeCourseActivity extends BaseActivity implements OnClickListener{
	
	private EditText et_help_name;
	private EditText et_help_descript;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_help_make_course);
		ExitApplication.getInstance().addActivity(this);
		initHeadView();
		initView();
		setListener();
	}
	/**
	 * 初始化头部view
	 */
	private void initHeadView() {
		setNavigationTitle("球教程");
		setNavigationLeftBtnText("");
		setNavigationRightBtnText("提问");
	}
	/**
	 * 取消
	 */
	@Override
	public void leftButtonOnClick() {
		super.leftButtonOnClick();
		this.finish();
	}
	/**
	 * 完成
	 */
	@Override
	public void rightButtonOnClick() {
		super.rightButtonOnClick();
	}

	/**
	 * 事件
	 */
	private void setListener() {
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		et_help_name=(EditText) findViewById(R.id.et_help_descript);
		et_help_descript=(EditText) findViewById(R.id.et_help_name);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_help_submit:
			String helpName=et_help_name.getText().toString();
			String helpDescript=et_help_descript.getText().toString();
			if(TextUtils.isEmpty(helpName)){
				Toast.makeText(this, "名称不能为空", Toast.LENGTH_LONG).show();
				return;
			}
			if(TextUtils.isEmpty(helpDescript)){
				Toast.makeText(this, "描述不能为空", Toast.LENGTH_LONG).show();
				return;
			}
			//保存到后台数据库
			Toast.makeText(this, "提交成功，马上就有好心人出现了", Toast.LENGTH_LONG).show();
			this.finish();
			break;
		case R.id.iv_help_back:
			this.finish();
			break;

		default:
			break;
			
		}
	}

}
