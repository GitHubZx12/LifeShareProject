package com.mendale.app.ui.home;

import com.mendale.app.R;
import com.mendale.app.pojo.MyUser;
import com.mendale.app.ui.home.fragment.CourseFragment;
import com.mendale.app.ui.home.fragment.HomeFragment;
import com.mendale.app.ui.home.fragment.HomeFragment.ChangeFragment;
import com.mendale.app.ui.home.fragment.QAFragment;
import com.mendale.app.ui.home.fragment.RecordFragment;
import com.mendale.app.ui.home.menu.CourseClassifyActivity;
import com.mendale.app.ui.home.menu.HelpMakeCourseActivity;
import com.mendale.app.ui.home.menu.SearchCourseActivity;
import com.mendale.app.ui.home.menu.SendFeekBackActivity;
import com.mendale.app.ui.home.menu.upload.TitleActivity;
import com.mendale.app.ui.login.LoginActivity;
import com.mendale.app.ui.mycenter.MyCenterActivity;
import com.mendale.app.utils.ExitApplication;
import com.mendale.app.utils.imageUtils.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.slidingmenu.lib.SlidingMenu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;

/**
 * 使用TabView的Activity
 * 
 * @author zhangxue
 * @date 2016-1-8
 */
public class MainPageActivity extends FragmentActivity implements OnClickListener, OnCheckedChangeListener,ChangeFragment {

	/** 标题 */
	private TextView tv_title;
	/** 左侧侧滑菜单 */
	private ImageButton iv_open;
	/** 右侧 */
	// private ImageButton iv_list;
	/** 指示器 */
	private ImageView mTabline;
	int mScreen1_3;
	/** radioGroup */
	private RadioGroup radioGroup;
	/** 首页 */
	private RadioButton rb_home;
	/** 课程 */
	private RadioButton rb_course;
	/** 排行版 */
	private RadioButton rb_charts;
	/** 记录 */
	private RadioButton rb_record;
	/** 上传教程 */
	private TextView tv_menu_upload;
	/** 头像 */
	private RoundImageView iv_menu_icon;
	/** 设置 */
	private ImageView iv_menu_setting;
	private TextView tv_menu_classify;
	private TextView tv_menu_help;
	private TextView tv_menu_login;
	private TextView tv_menu_news;
	private TextView tv_menu_search;
	/** Fragment Manager */
	FragmentManager fm;
	FragmentTransaction transaction;
	HomeFragment homeFragment;
	CourseFragment courseFragment;
	QAFragment qaFragment;
	RecordFragment recordFragment;
	/** 侧滑菜单 */
	private SlidingMenu menu;
	private Button btnExit;
	public long exitTime;// 储存点击退出时间
	// ImageLoader
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private LinearLayout.LayoutParams lp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_page);
		ExitApplication.getInstance().addAllActivity(this);// 加入全局退出队列
		// 添加侧滑菜单
		attachSlidingMenuRight();
		init();
		initTabLine();
		initFragment();
		setListener();
	}

	/**
	 * 添加侧滑菜单
	 */
	@SuppressWarnings("deprecation")
	private void attachSlidingMenuRight() {
		// 创建对象
		menu = new SlidingMenu(this);
		// 设置滑动的屏幕局限，该设置为全屏区域都可以滑动
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// 附加在Activity上
		menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		// 设置菜单滑出后屏幕剩下的宽度
		menu.setBehindOffset(getWindowManager().getDefaultDisplay().getWidth() / 2);
		// menu.setBehindOffset(50);
		// 设置菜单的宽度
		// menu.setBehindWidth(360);
		// 设置侧滑模式，该设置是左右都可侧滑
		menu.setMode(SlidingMenu.LEFT);
		// 设置布局文件（默认左侧）-----左侧的布局根据自身需求来画
		menu.setMenu(R.layout.item_menu_left);
		// 设置右侧布局文件-----右侧的布局根据自身需求来画
		menu.setSecondaryMenu(R.layout.item_menu_right);
		// 设置菜单页与内容页的移动比例，取值0~1，0时菜单页不动，1时两个同时移动
		menu.setBehindScrollScale(1);
	}

	/**
	 * 初始化控件
	 */
	private void init() {
		iv_open = (ImageButton) findViewById(R.id.iv_open);
		tv_title = (TextView) findViewById(R.id.tv_title);
		mTabline = (ImageView) findViewById(R.id.line2);
		radioGroup = (RadioGroup) findViewById(R.id.rg);
		rb_home = (RadioButton) findViewById(R.id.rb_home);
		rb_course = (RadioButton) findViewById(R.id.rb_course);
		rb_charts = (RadioButton) findViewById(R.id.rb_qa);
		rb_record = (RadioButton) findViewById(R.id.rb_record);
		// 侧滑
		tv_menu_upload = (TextView) findViewById(R.id.tv_menu_upload);
		tv_menu_classify = (TextView) findViewById(R.id.tv_menu_classify);
		tv_menu_help = (TextView) findViewById(R.id.tv_menu_help);
		tv_menu_login = (TextView) findViewById(R.id.tv_menu_login);
		tv_menu_news = (TextView) findViewById(R.id.tv_menu_news);
		tv_menu_search = (TextView) findViewById(R.id.tv_menu_search);
		iv_menu_icon = (RoundImageView) findViewById(R.id.iv_menu_icon);
		iv_menu_setting = (ImageView) findViewById(R.id.iv_menu_set);
		btnExit=(Button)findViewById(R.id.btn_exit);
		//
		MyUser user=BmobUser.getCurrentUser(this,MyUser.class);
		ImageLoader imageLoader=ImageLoader.getInstance();
		if(user.getUrl()!=null){
			imageLoader.displayImage(user.getUrl(), iv_menu_icon);
		}else{
			iv_menu_icon.setImageResource(R.drawable.defult_avator);
		}
		if(user.getUsername()!=null){
			tv_menu_login.setText(user.getUsername());
		}
		
	}

	/**
	 * 初始化指示器的宽度
	 */
	private void initTabLine() {
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);
		mScreen1_3 = outMetrics.widthPixels / 4;
		LayoutParams lp = mTabline.getLayoutParams();
		lp.width = mScreen1_3;
		mTabline.setLayoutParams(lp);
	}

	/**
	 * 初始化Fragment
	 */
	private void initFragment() {
		fm = getSupportFragmentManager();
		transaction = fm.beginTransaction();
		if (homeFragment == null) {
			homeFragment = new HomeFragment();
		}
		if (courseFragment == null) {
			courseFragment = new CourseFragment();
		}
		if (qaFragment == null) {
			qaFragment = new QAFragment();
		}
		if (recordFragment == null) {
			recordFragment = new RecordFragment();
		}
		changeFragment(homeFragment, true);
	}

	/**
	 * 切换Fragmentment
	 * 
	 * @param homeFragment2
	 * @param b
	 */
	private void changeFragment(Fragment fragment, boolean isInit) {
		if (!fragment.isAdded()) {
			transaction = fm.beginTransaction();
			transaction.replace(R.id.fragment, fragment);
			if (!isInit) {
				transaction.addToBackStack(null);
			}
			transaction.commit();
		}
	}

	/**
	 * 设置样式
	 */
	private void setListener() {
		iv_open.setOnClickListener(this);
		// iv_list.setOnClickListener(this);
		radioGroup.setOnCheckedChangeListener(this);
		tv_menu_upload.setOnClickListener(this);
		iv_menu_icon.setOnClickListener(this);
		tv_menu_help.setOnClickListener(this);
		tv_menu_news.setOnClickListener(this);
		tv_menu_classify.setOnClickListener(this);
		tv_menu_search.setOnClickListener(this);
		btnExit.setOnClickListener(this);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		resertColor();
		lp = (android.widget.LinearLayout.LayoutParams) mTabline.getLayoutParams();
		switch (checkedId) {
			case R.id.rb_home:
				lp.leftMargin = (int) 0;
				setPressedTextColor(rb_home);
				tv_title.setText("首页");
				changeFragment(homeFragment, true);
				break;
			case R.id.rb_course:
				lp.leftMargin = (int) mScreen1_3 * 1;
				setPressedTextColor(rb_course);
				tv_title.setText("教程");
				changeFragment(courseFragment, true);
				break;
			case R.id.rb_qa:
				lp.leftMargin = (int) mScreen1_3 * 2;
				setPressedTextColor(rb_charts);
				tv_title.setText("问答");
				changeFragment(qaFragment, true);
				break;
			case R.id.rb_record:
				lp.leftMargin = (int) mScreen1_3 * 3;
				setPressedTextColor(rb_record);
				tv_title.setText("记录");
				changeFragment(recordFragment, true);
				break;
		}
	}
	
	

	/**
	 * 设置选中的颜色
	 * 
	 * @param rb
	 */
	private void setPressedTextColor(RadioButton rb) {
		rb.setTextColor(Color.parseColor("#35CDE5"));
	}

	/**
	 * 字体恢复默认的颜色
	 */
	private void resertColor() {
		rb_home.setTextColor(Color.BLACK);
		rb_course.setTextColor(Color.BLACK);
		rb_charts.setTextColor(Color.BLACK);
		rb_record.setTextColor(Color.BLACK);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_open:
				menu.showMenu();
				break;
			case R.id.tv_menu_upload:// 上传教程
				Intent intent = new Intent(this, TitleActivity.class);
				startActivity(intent);
				break;
			case R.id.iv_menu_icon:// 个人信息详情
				Intent intent2 = new Intent(this, MyCenterActivity.class);
				intent2.putExtra("flag", 1);
				startActivity(intent2);
				break;
			case R.id.tv_menu_help:// 求教程
				Intent intent3 = new Intent(this, HelpMakeCourseActivity.class);
				startActivity(intent3);
				break;
			case R.id.tv_menu_news:// 反馈
				Intent intent4 = new Intent(this, SendFeekBackActivity.class);
				startActivity(intent4);
				break;
			case R.id.tv_menu_classify:// 分类
				Intent intent5 = new Intent(this, CourseClassifyActivity.class);
				startActivity(intent5);
				break;
			case R.id.tv_menu_search:// 搜索
				Intent intent6 = new Intent(this, SearchCourseActivity.class);
				startActivity(intent6);
				break;
			case R.id.btn_exit://退出登录
				BmobUser.logOut(this);   //清除缓存用户对象
				BmobUser currentUser = BmobUser.getCurrentUser(this); // 现在的currentUser是null了
				Intent intent7 = new Intent(this, LoginActivity.class);
				startActivity(intent7);
				break;
		}
	}

	/**
	 * 再按一次退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			}
			else {
				// 全局推出
				ExitApplication.getInstance().exitAll();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onClearMemoryClick(View view) {
		Toast.makeText(this, "清除内存缓存成功", Toast.LENGTH_SHORT).show();
		ImageLoader.getInstance().clearMemoryCache(); // 清除内存缓存
	}

	public void onClearDiskClick(View view) {
		Toast.makeText(this, "清除本地缓存成功", Toast.LENGTH_SHORT).show();
		ImageLoader.getInstance().clearDiskCache(); // 清除本地缓存
	}

	@Override
	public void changeFragmentClick() {
		resertColor();
		lp.leftMargin = (int) mScreen1_3 * 1;
		setPressedTextColor(rb_course);
		tv_title.setText("教程");
		changeFragment(courseFragment, true);
	}
}
