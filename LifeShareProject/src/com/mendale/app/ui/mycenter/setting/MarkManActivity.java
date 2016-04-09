package com.mendale.app.ui.mycenter.setting;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.mendale.app.R;
import com.mendale.app.ui.base.BaseActivity;

/**
 * 个人资料详情
 * 
 * @author zhangxue
 * @date 2016-1-26
 */
public class MarkManActivity extends BaseActivity implements OnClickListener {

	private ImageView iv_changeHead;
	private PopupWindow mPop;// 弹出拍照

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mark_man);
		initHeaderView();
		initView();
	}

	/**
	 * 初始化
	 */
	private void initView() {
		iv_changeHead = (ImageView) findViewById(R.id.iv_changeHead);
		iv_changeHead.setOnClickListener(this);
	}

	/**
	 * 初始化头部
	 */
	private void initHeaderView() {
		setNavigationTitle("个人资料");
		setNavigationLeftBtnText("返回");
		setNavigationLeftBtnImage(R.drawable.icon_back);
	}

	@Override
	public void leftButtonOnClick() {
		this.finish();
		super.leftButtonOnClick();
	}

	@Override
	public void onClick(View v) {
		showPopWindow();// 更换头像
	}

	/**
	 * 
	 */
	private void showPopWindow() {
		
	}


}
