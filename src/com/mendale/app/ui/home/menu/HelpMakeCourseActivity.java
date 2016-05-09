package com.mendale.app.ui.home.menu;

import com.mendale.app.R;
import com.mendale.app.pojo.HelpCoursePoJo;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.utils.ExitApplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.listener.SaveListener;

/**
 * 求教程
 * @author Administrator
 *
 */
public class HelpMakeCourseActivity extends BaseActivity{
	
	private EditText et_help_phone;
	private EditText et_help_name;
	private EditText et_help_descript;
	String old_title = "";
	String old_describe = "";
	String old_phone = "";
	String title = "";
	String describe = "";
	String photo="";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_help_make_course);
		ExitApplication.getInstance().addActivity(this);
		initHeadView();
		initView();
		initData();
	}
	/**
	 * 得到通过intent传递过来的数据
	 */
	private void initData() {
		old_title = getIntent().getStringExtra("title");
		old_phone = getIntent().getStringExtra("phone");
		old_describe = getIntent().getStringExtra("describe");
		if(!TextUtils.isEmpty(old_title)){
			et_help_name.setText(old_title);
		}
		if(!TextUtils.isEmpty(old_phone)){
			et_help_phone.setText(old_phone);
		}
		if(!TextUtils.isEmpty(old_describe)){
			et_help_descript.setText(old_describe);
		}
		
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
		addQA();
	}

	/**
	 * 添加问题
	 */
	private void addQA() {
		title = et_help_name.getText().toString();
		describe = et_help_descript.getText().toString();
		photo = et_help_phone.getText().toString();
		
		if(TextUtils.isEmpty(title)){
			Toast.makeText(HelpMakeCourseActivity.this, "请填写标题", Toast.LENGTH_SHORT).show();
			return;
		}
		if(TextUtils.isEmpty(describe)){
			Toast.makeText(HelpMakeCourseActivity.this, "请填写描述", Toast.LENGTH_SHORT).show();
			return;
		}
		if(TextUtils.isEmpty(photo)){
			Toast.makeText(HelpMakeCourseActivity.this, "请填写手机", Toast.LENGTH_SHORT).show();
			return;
		}
		HelpCoursePoJo item = new HelpCoursePoJo();
		item.setContent(describe);
		item.setPhone(photo);
		item.setTitle(title);
		item.save(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				Toast.makeText(HelpMakeCourseActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
				setResult(RESULT_OK);
				finish();
			}
			
			@Override
			public void onFailure(int code, String arg0) {
				Toast.makeText(HelpMakeCourseActivity.this, "添加失败:"+arg0, Toast.LENGTH_SHORT).show();
			}
		});
		
	}
	/**
	 * 初始化界面
	 */
	private void initView() {
		et_help_name=(EditText) findViewById(R.id.et_help_descript);
		et_help_descript=(EditText) findViewById(R.id.et_help_name);
		et_help_phone=(EditText)findViewById(R.id.et_help_phone);
	}

}
