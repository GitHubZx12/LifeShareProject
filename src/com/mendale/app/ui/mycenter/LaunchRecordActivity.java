package com.mendale.app.ui.mycenter;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mendale.app.R;
import com.mendale.app.adapters.CourseInfoLvAdapter;
import com.mendale.app.pojo.OpusData;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.utils.pullToRefreshUtils.PullToRefreshConfig;
import com.mendale.app.utils.pullToRefreshUtils.view.XListView;
import com.mendale.app.utils.pullToRefreshUtils.view.XListView.IXListViewListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 收藏记录
 * @author zx
 *
 */
public class LaunchRecordActivity extends BaseActivity implements IXListViewListener {

	private XListView mListView;
	private CourseInfoLvAdapter mAdapter;
	private DisplayImageOptions options; // DisplayImageOptions是用于设置图片显示的类
	/** 显示没有更多数据 */
	private TextView tvTip;
	private ImageView iv_loading;
	private LinearLayout ll_loading;
	/**数据*/
	private OpusData opusData=null;
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_info);
		initHeaderView();
		initView();
		initAnim();
		initImageOptions();
		getIntentData();
	}
	
	/**
	 * 得到传递过来的参数
	 */
	private void getIntentData() {
		opusData=(OpusData) getIntent().getSerializableExtra("opusData");
		iv_loading.clearAnimation();
		if(null==opusData){
			tvTip.setText("没有收藏任何记录");
			return;
		}
		mListView.setVisibility(View.VISIBLE);
		mAdapter = new CourseInfoLvAdapter(LaunchRecordActivity.this, opusData.getList(), options);
		mListView.setAdapter(mAdapter);
	}
	/**
	 * 初始化头部
	 */
	private void initHeaderView() {
		setNavigationTitle("发布记录");
		setNavigationLeftBtnText("");
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
	 * 初始化界面
	 * 
	 * @param view
	 */
	private void initView() {
		mListView = (XListView) findViewById(R.id.listview_course_info);
		mListView.setPullRefreshEnable(PullToRefreshConfig.pullRefreshEnable);// 设置是否可以下拉刷新
		mListView.setPullLoadEnable(PullToRefreshConfig.pullLoadEnable);// 设置是否可以上拉加载
		mListView.setXListViewListener(this);// 设置监听
		//
		iv_loading = (ImageView) findViewById(R.id.iv_course_info_loading);
		ll_loading = (LinearLayout) findViewById(R.id.ll_course_info_loading);
		tvTip=(TextView) findViewById(R.id.tv_course_info_tips);
	}

	/**
	 * 等待状态，顶部显示上次刷新时间
	 * 
	 * @author 张静
	 * @Time 2015年12月3日下午6:05:12
	 */
	@SuppressLint("SimpleDateFormat")
	private void onLoad() {
		mListView.stopRefresh();// 停止刷新
		mListView.stopLoadMore();// 停止"加载更多"
		// 获得系统当前时间
		SimpleDateFormat formatter = new SimpleDateFormat(PullToRefreshConfig.strDateFormat);
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);// 格式化
		mListView.setRefreshTime(str);// 给listview设置刷新时间
		// if (list.size() == 15) {
		// // 当数目大于15的时候
		// mListView.setPullLoadEnable(false);// 禁止上拉加载
		// mListView.setPullRefreshEnable(false);// 禁止下拉刷新
		// tv_no_data.setVisibility(View.VISIBLE);// 显示没有更多数据
		// }
	}

	@Override
	public void onRefresh() {
	
	}

	@Override
	public void onLoadMore() {
		
	}
}
