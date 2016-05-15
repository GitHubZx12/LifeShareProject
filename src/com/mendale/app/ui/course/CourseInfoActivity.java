package com.mendale.app.ui.course;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.mendale.app.R;
import com.mendale.app.adapters.CourseInfoLvAdapter;
import com.mendale.app.constants.DataURL;
import com.mendale.app.pojo.CourseChildPojo;
import com.mendale.app.pojo.CourseListPojo;
import com.mendale.app.tasks.CourseListTask;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.utils.pullToRefreshUtils.PullToRefreshConfig;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 课程详情页
 * @author zhangxue 
   @date 2016年4月27日
 */
public class CourseInfoActivity extends BaseActivity  {

	private ListView mListView;
	private CourseInfoLvAdapter mAdapter;
	private DisplayImageOptions options; // DisplayImageOptions是用于设置图片显示的类
	/** 显示没有更多数据 */
	private TextView tv_no_data;
	//
	private ImageView iv_loading;
	private LinearLayout ll_loading;
	private Handler mhandler = new Handler() {

		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case 1:
					if (msg.obj != null) {
						iv_loading.clearAnimation();
						ll_loading.setVisibility(View.INVISIBLE);
						mListView.setVisibility(View.VISIBLE);
						List<CourseListPojo> recordList = (List<CourseListPojo>) msg.obj;
						mAdapter = new CourseInfoLvAdapter(CourseInfoActivity.this, recordList, options);
						mListView.setAdapter(mAdapter);
					}
					break;
				default:
					break;
			}
		};
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_info);
		initHeaderView();
		initView();
		initAnim();
		initImageOptions();
	};


	/**
	 * 初始化头部
	 */
	private void initHeaderView() {
		CourseChildPojo item=(CourseChildPojo) getIntent().getSerializableExtra("courseItem");
		if(null==item){
			return;
		}
		setNavigationTitle(item.getName());
		setNavigationLeftBtnText("");
		initData(item.getId());
	}
	@Override
	public void leftButtonOnClick() {
		super.leftButtonOnClick();
		this.finish();
	}

	/**
	 * 初始化动画
	 */
	private void initAnim() {
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.loading);
		iv_loading.startAnimation(animation);
	}

	/**
	 * 初始化图片的相关参数
	 */
	private void initImageOptions() {
		// 使用DisplayImageOptions.Builder()创建DisplayImageOptions
		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.image_guidestep_defult) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.image_guidestep_defult) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.image_guidestep_defult) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				.displayer(new RoundedBitmapDisplayer(0)) // 设置成圆角图片
				.build(); // 创建配置过得DisplayImageOption对象
	}

	/**
	 * 请求接口
	 */
	private void initData(final String id) {
		new Thread() {
			public void run() {
				String fullUrl=DataURL.COURSE_LIST+id+"&type=new";
				new CourseListTask(CourseInfoActivity.this, mhandler).send(1, "utf-8", fullUrl);
			};
		}.start();
	}

	/**
	 * 初始化界面
	 * 
	 * @param view
	 */
	private void initView() {
		mListView = (ListView) findViewById(R.id.listview_course_info);
		//
		iv_loading = (ImageView) findViewById(R.id.iv_course_info_loading);
		ll_loading = (LinearLayout) findViewById(R.id.ll_course_info_loading);
	}

}
