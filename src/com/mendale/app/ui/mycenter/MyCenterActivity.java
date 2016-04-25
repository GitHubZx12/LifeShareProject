package com.mendale.app.ui.mycenter;

import java.io.File;
import java.util.Date;

import com.mendale.app.R;
import com.mendale.app.application.MobileApplication;
import com.mendale.app.constants.URLS;
import com.mendale.app.pojo.LoginUser;
import com.mendale.app.pojo.MemberPojo;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.ui.home.MainPageActivity;
import com.mendale.app.ui.mycenter.setting.MarkManActivity;
import com.mendale.app.utils.ExitApplication;
import com.mendale.app.utils.Utils;
import com.mendale.app.utils.background.MyScrollView;
import com.mendale.app.utils.imageUtils.ImageOpera;
import com.mendale.app.utils.imageUtils.RoundImageView;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 我的主界面
 * 
 * 
 */
public class MyCenterActivity extends BaseActivity implements OnClickListener {

	/**
	 * 背景
	 */
	private ImageView mycenter_background_image;
	/**
	 * 线性布局
	 */
	private LinearLayout mycenter_AllData;
	/**
	 * 资料
	 */
	private TextView mycenter_data;
	/**
	 * 头像
	 */
	private RoundImageView mycenter_head_image;
	/**
	 * 人名
	 */
	private TextView mycenter_name;
	/**
	 * 性别图标
	 */
	private ImageView mycenter_sex_image;
	/**
	 * 城市
	 */
	private TextView mycenter_total;
	/**
	 * 级别图标
	 */
	private ImageView mycenter_vip_image;
	/**
	 * 发布教程
	 */
	private LinearLayout mycenter_mysteward;
	/**
	 * 收藏教程
	 */
	private LinearLayout mycenter_rightscent;
	/**
	 * 收藏记录
	 */
	private LinearLayout mycenter_serve;
	/**
	 * 发布记录
	 */
	private LinearLayout mycenter_setting;
	/**
	 * 返回
	 */
	private ImageView iv_back;
	private MyScrollView scrollview;
	private TextView view;
	private MemberPojo memberbean;
	private Context context;
	private int current_id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mycenter);
		/** 加入全局退出队列------------规范：都要加 */
		ExitApplication.getInstance().addAllActivity(this);
		init();
		setLinstener();
	}

	/**
	 * 绑定控件ID
	 */
	private void init() {
		mycenter_background_image = (ImageView) findViewById(R.id.mycenter_background_image);
		mycenter_data = (TextView) findViewById(R.id.mycenter_data);
		mycenter_head_image = (RoundImageView) findViewById(R.id.mycenter_head_image);
		mycenter_name = (TextView) findViewById(R.id.mycenter_name);
		mycenter_sex_image = (ImageView) findViewById(R.id.mycenter_sex_image);
		mycenter_total = (TextView) findViewById(R.id.mycenter_total);
		mycenter_vip_image = (ImageView) findViewById(R.id.mycenter_vip_image);
		mycenter_mysteward = (LinearLayout) findViewById(R.id.mycenter_mysteward);
		mycenter_rightscent = (LinearLayout) findViewById(R.id.mycenter_rightscent);
		mycenter_serve = (LinearLayout) findViewById(R.id.mycenter_serve);
		mycenter_setting = (LinearLayout) findViewById(R.id.mycenter_setting);
		mycenter_AllData = (LinearLayout) findViewById(R.id.mycenter_AllData);
		iv_back = (ImageView) findViewById(R.id.iv_mycenter_back);
		view = (TextView) findViewById(R.id.mycenter_background_view);
		scrollview = (MyScrollView) findViewById(R.id.myScrollView);
		scrollview.setImageView(mycenter_background_image);
		context = this;
	}

	/**
	 * 给控件赋值
	 */
	private void initData() {
		MobileApplication application = (MobileApplication) getApplication();
		mycenter_background_image.setImageResource(R.drawable.crafter_personal_bg);
		mycenter_head_image.setImageResource(R.drawable.defult_avator);
		mycenter_name.setText(application.getmUserInfo().getUserName());
		mycenter_total.setText("江苏徐州");
		mycenter_vip_image.setImageResource(R.drawable.lv1);
		mycenter_sex_image.setImageResource(R.drawable.vip_icon_wowam);
	}

	/**
	 * 设置控件监听
	 */
	private void setLinstener() {
		mycenter_background_image.setOnClickListener(this);
		mycenter_data.setOnClickListener(this);
		mycenter_mysteward.setOnClickListener(this);
		mycenter_rightscent.setOnClickListener(this);
		mycenter_serve.setOnClickListener(this);
		mycenter_setting.setOnClickListener(this);
		mycenter_AllData.setOnClickListener(this);
		iv_back.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		initData();
		super.onResume();
	}

	/**
	 * onclick 事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.mycenter_background_image:// 背景
				// showPhoneDialog(1);
				break;
			case R.id.mycenter_AllData:// 为了不触发背景点击事件
				break;
			case R.id.mycenter_data:// 资料
				Intent intent = new Intent(this, MarkManActivity.class);
				startActivityForResult(intent, 2);
				break;
			case R.id.mycenter_mysteward:// 发布教程
				mycenter_mysteward.setBackgroundColor(getResources().getColor(R.color.gray_x));
				startActivity(LaunchCourseActivity.class);
				break;
			case R.id.mycenter_rightscent:// 收藏教程
				mycenter_rightscent.setBackgroundColor(getResources().getColor(R.color.gray_x));
				startActivity(CollectCourseActivity.class);
				break;
			case R.id.mycenter_setting:// 发布记录
				mycenter_setting.setBackgroundColor(getResources().getColor(R.color.gray_x));
				startActivity(LaunchRecordActivity.class);
				break;
			case R.id.mycenter_serve:// 收藏记录
				mycenter_serve.setBackgroundColor(getResources().getColor(R.color.gray_x));
				startActivity(CollectRecordActivity.class);
				break;
			case R.id.mycenter_background_view:// 背景
				// showPhoneDialog(1);
				break;
			case R.id.iv_mycenter_back:
				this.finish();
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case 2:
				LoginUser loginUser=(LoginUser) data.getSerializableExtra("userinof");
				showToast(loginUser.toString());
				break;
			default:
				break;
		}
		
		
	}
}
