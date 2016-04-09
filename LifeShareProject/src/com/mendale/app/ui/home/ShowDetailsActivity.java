package com.mendale.app.ui.home;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mendale.app.R;
import com.mendale.app.adapters.DetailsPagerAdapter;
import com.mendale.app.tasks.HotCourseDetailsTask;
import com.mendale.app.vo.CourseDetailsItemBean;
import com.mendale.app.vo.HotCourseItemBean;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 热门教程 详情界面
 * 
 * @author ASSU_X
 * 
 */
public class ShowDetailsActivity extends Activity implements
		OnPageChangeListener, OnClickListener {

	// 布局容器
	private LayoutInflater mInflater;
	// 切换的ViewPage
	private ViewPager mViewPager;
	// 布局集合
	private List<View> mViewList;
	// 设置步骤的TextView
	TextView text;
	// 步骤的图标、退、赞、收集、材料、分享
	ImageView icon, back, laud, collect, material, share;
	private CourseDetailsItemBean detailsItemData;
	private String detail_url;
	private int step;
	private ImageLoader imageLoader;
	
	private Handler mhandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				if(msg.obj!=null){
					detailsItemData=(CourseDetailsItemBean) msg.obj;
					initView();// 初始化
					
				}
				
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		getIntentData();
		requestData();
	}

	/**
	 * 请求服务获取详细信息
	 */
	private void requestData() {
		new Thread(){
			public void run() {
				
				new HotCourseDetailsTask(ShowDetailsActivity.this, mhandler).send(1, "utf-8", detail_url);
			};
		}.start();
		
	}

	/**
	 * 获取详情页的id
	 */
	private void getIntentData() {
		Intent intent=getIntent();
		detail_url=intent.getStringExtra("detail_url");
		step=intent.getIntExtra("step", 0);
	}

	/**
	 * 初始化
	 */
	private void initView() {
		mInflater = LayoutInflater.from(this);
		mViewPager = (ViewPager) findViewById(R.id.details_viewpage);
		text = (TextView) findViewById(R.id.details_tv);
		icon = (ImageView) findViewById(R.id.details_iv);
		back = (ImageView) findViewById(R.id.details_iv_back);
		material = (ImageView) findViewById(R.id.details_iv_material);
		laud = (ImageView) findViewById(R.id.details_iv_laud);
		collect = (ImageView) findViewById(R.id.details_iv_collect);
		share = (ImageView) findViewById(R.id.details_iv_share);

		imageLoader=ImageLoader.getInstance();
		mViewList = new ArrayList<View>();
		// 动态创建view
		addView(step);

		// ViewPage的切换事件
		mViewPager.setOnPageChangeListener(this);
		back.setOnClickListener(this);
		laud.setOnClickListener(this);
		collect.setOnClickListener(this);
		material.setOnClickListener(this);
		share.setOnClickListener(this);
	}

	/**
	 * 根据数据多少，动态创建View
	 */
	@SuppressLint("InflateParams")
	private void addView(int step) {
		for (int i = 0; i < step; i++) {
			if (i == 0) {// 首页
				View itemView1 = mInflater.inflate(R.layout.detail_viewpage_1,
						null);
				setViewDate1(itemView1, i);
				mViewList.add(itemView1);
			} else {// 步骤页
				icon.setVisibility(View.INVISIBLE);
				text.setVisibility(View.VISIBLE);
				View itemView = mInflater.inflate(R.layout.detail_viewpage_2,
						null);
				setViewDate(itemView, i);// 为控件设置数据
				mViewList.add(itemView);
			}// 尾页

		}

		/**
		 * 设置页面切换适配器
		 */
		mViewPager.setAdapter(new DetailsPagerAdapter(mViewList,step));
	}

	/**
	 * 为所有ViewPage1的控件赋值，最好使用ViewHolder
	 * 
	 * @param view
	 *            View
	 * @param position
	 *            位置
	 */
	private void setViewDate1(View itemView, int position) {
		// 标题
		TextView title = (TextView) itemView
				.findViewById(R.id.viewpage_1_tv_title);
		title.setText(detailsItemData.getSubject());
		// 作者头像
		ImageView imageView = (ImageView) itemView
				.findViewById(R.id.viewpage_1_iv_pic);
		
		imageLoader.displayImage(detailsItemData.getHost_pic(), imageView);
		// 评论、赞等的数量
		TextView count = (TextView) itemView
				.findViewById(R.id.viewpage_1_tv_content);
		count.setText(detailsItemData.getSummary());

		TextView all = (TextView) itemView.findViewById(R.id.viewpage_1_tv_all);
		all.setText(detailsItemData.getView() + "/"
				+ detailsItemData.getLaud() + "/"
				+ detailsItemData.getCollect() + "/"
				+ detailsItemData.getComment_count());
	}

	/**
	 * 为所有ViewPage2的控件赋值，最好使用ViewHolder
	 * 
	 * @param view
	 *            View
	 * @param position
	 *            位置
	 */
	private void setViewDate(View itemView, int position) {
		ViewHolder viewHolder = new ViewHolder();
		// 图片
		viewHolder.imageView = (ImageView) itemView.findViewById(R.id.iv_pic);
		imageLoader.displayImage(detailsItemData.getStep().get(position).pic,viewHolder.imageView);
		// 内容
		viewHolder.content = (TextView) itemView.findViewById(R.id.tv_content);
		viewHolder.content.setText(detailsItemData.getStep().get(position).content);
		// 总共的步骤
		viewHolder.count = (TextView) itemView
				.findViewById(R.id.tv_comment_count);
		viewHolder.count.setText(detailsItemData.getComment_count());
		viewHolder.comment_fl = (ViewGroup) itemView
				.findViewById(R.id.details_2_fl);

		// 添加点击事件
		viewHolder.comment_fl.setOnClickListener(viewHolder);
	}

	class ViewHolder implements OnClickListener {
		ViewGroup comment_fl;
		ImageView imageView;
		TextView content;
		TextView count;
		TextView comment_count;

		// 点击事件
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.details_2_fl:// 评论
//				Intent intent = new Intent();
//				intent.setClass(showDetailsActivity.this, UserCommentActivity.class);
//				startActivity(intent);
				break;

			default:
				break;
			}

		}
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.details_iv_back:// 退

			break;
		case R.id.details_iv_material:// 材料
			Intent intent = new Intent();
//			intent.setClass(ShowDetailsActivity.this, MaterialActivity.class);
			startActivity(intent);
			break;
		case R.id.details_iv_laud:// 点赞
			// 动画、、服务器、插入到数据库中
			Toast.makeText(this, "点赞", Toast.LENGTH_SHORT).show();
			break;
		case R.id.details_iv_collect:// 收集
			// 动画、、服务器、插入到数据库中
			Toast.makeText(this, "收集", Toast.LENGTH_SHORT).show();
			break;
		case R.id.details_iv_share:// 分享
			// d
			Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
			break;
		}

	}

	/**
	 * ViewPage切换事件
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int position) {
		Log.i("test", "onPageSelected" + position);
		// page切换则修改步骤
		if (position == 0) {
			text.setVisibility(View.INVISIBLE);
			icon.setVisibility(View.INVISIBLE);
		} else {
			icon.setVisibility(View.VISIBLE);
			text.setVisibility(View.VISIBLE);
			text.setText(position + "/"
					+ detailsItemData.step.size());
		}
	}

}
