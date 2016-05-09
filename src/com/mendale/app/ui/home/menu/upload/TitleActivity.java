package com.mendale.app.ui.home.menu.upload;

import com.mendale.app.R;
import com.mendale.app.pojo.Titles;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.utils.ExitApplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.listener.SaveListener;

/**
 * 1
 * 上传教程的标题
 * @author zx
 *
 */
public class TitleActivity extends BaseActivity {
	/**
	 * 是否填写完毕
	 */
	public boolean finished = false;
	/**
	 * 教程名称
	 */
	private EditText et_title;
	/**
	 * 教程简介
	 */
	private EditText et_title_descript;

	View view;
	Titles titles;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_title);
		ExitApplication.getInstance().addActivity(this);
		initHeaderView();
		initView();
	}

	/**
	 * 初始化头部view
	 */
	private void initHeaderView() {
		setNavigationTitle("标题");
		setNavigationLeftBtnText("");
		setNavigationRightBtnImage(R.drawable.crafter_cguide_lastarrow_yes_selected);
		setNavigationLeftBtnImage(R.drawable.crafter_cguide_lastarrow_yes);
	}

	/**
	 * 返回
	 */
	@Override
	public void leftButtonOnClick() {
		super.leftButtonOnClick();
		this.finish();
	}
	/**
	 * 下一步
	 */
	@Override
	public void rightImageButtonOnClick() {
		super.rightImageButtonOnClick();
		createData();
		startActivity(MaterialActivity.class);
//		Toast.makeText(this, "下一步", Toast.LENGTH_SHORT).show();
		isEmpty();
	}
	
	
	/**
	 * 创建数据
	 */
	public void createData(){
		titles = new Titles();
		titles.setTitles_name(et_title.getText().toString());
		titles.setTitle_description(et_title_descript.getText().toString());
		titles.save(this, new SaveListener() {
		
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				showToast("创建成功");
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				showToast("创建失败："+arg1);
			}
		});
			
			
	}
	
	
	/**
	 * 判断输入的是否为空
	 */
	private void isEmpty() {
		String s = String.valueOf(et_title.getText());
		String s1 = String.valueOf(et_title_descript.getText());
		if (s.equals("") || s1.equals("")) {
			finished = false;
			Toast.makeText(this, "请填写内容", Toast.LENGTH_LONG).show();
		}else{
			startActivity(MaterialActivity.class);
		}

	}

	/**
	 * 初始化view
	 */
	private void initView() {
		et_title = (EditText) findViewById(R.id.et_title);
		et_title_descript = (EditText) findViewById(R.id.et_title_descript);
	}

}
