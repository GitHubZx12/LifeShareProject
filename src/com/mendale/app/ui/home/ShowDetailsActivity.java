package com.mendale.app.ui.home;

import java.util.ArrayList;
import java.util.List;

import com.mendale.app.R;
import com.mendale.app.adapters.DetailsPagerAdapter;
import com.mendale.app.ui.home.menu.CommentActivity;
import com.mendale.app.ui.thirdparty.AndroidShare;
import com.mendale.app.vo.CourseDetailsBean;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.utils.Log;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 热门教程 详情界面
 * 
 * @author ASSU_X
 * 
 */
public class ShowDetailsActivity extends Activity implements OnPageChangeListener, OnClickListener {

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
	private CourseDetailsBean detailsItemData;
	private String detail_url;
	private int step;
	private ImageLoader imageLoader;
	private String hand_id;
	private String objectId;
	// 友盟分享相关
	UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
	UMSocialService controller = UMServiceFactory.getUMSocialService("com.umeng.share");
	private Context mContext;
	private int flag;

	private Handler mhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				initView();// 初始化
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

		// 7友盟评论相关
		mContext = this;
		// 首先在您的Activity中添加如下成员变量
		mController = UMServiceFactory.getUMSocialService("com.umeng.share");
		// 设置分享内容
		mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social");
		// 设置分享图片, 参数2为图片的url地址
		mController.setShareMedia(new UMImage(this, "http://www.umeng.com/images/pic/banner_module_social.png"));
	}

	/**
	 * 根据传递过来的id，查询详细信息 请求服务获取详细信息
	 */
	private void requestData() {
		// new Thread(){
		// public void run() {
		// new HotCourseDetailsTask(ShowDetailsActivity.this, mhandler).send(1,
		// "utf-8", detail_url);
		// };
		// }.start();
		BmobQuery<CourseDetailsBean> bmobQuery = new BmobQuery<CourseDetailsBean>();
		bmobQuery.addWhereEqualTo("hand_id", hand_id);
		bmobQuery.findObjects(this, new FindListener<CourseDetailsBean>() {

			@Override
			public void onSuccess(List<CourseDetailsBean> arg0) {
				detailsItemData = arg0.get(0);
				mhandler.sendEmptyMessage(1);
			}

			@Override
			public void onError(int arg0, String arg1) {
				Log.e("tag", arg1);
			}
		});

	}

	/**
	 * 获取详情页的id
	 */
	private void getIntentData() {
		Intent intent = getIntent();
		detail_url = intent.getStringExtra("detail_url");
		step = intent.getIntExtra("step", 0);
		hand_id = intent.getIntExtra("hand_id", 0) + "";
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

		imageLoader = ImageLoader.getInstance();
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
				View itemView1 = mInflater.inflate(R.layout.detail_viewpage_1, null);
				setViewDate1(itemView1, i);
				mViewList.add(itemView1);
			} else {// 步骤页
				icon.setVisibility(View.INVISIBLE);
				text.setVisibility(View.VISIBLE);
				View itemView = mInflater.inflate(R.layout.detail_viewpage_2, null);
				setViewDate(itemView, i);// 为控件设置数据
				mViewList.add(itemView);
			} // 尾页

		}

		/**
		 * 设置页面切换适配器
		 */
		mViewPager.setAdapter(new DetailsPagerAdapter(mViewList, step));
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
		TextView title = (TextView) itemView.findViewById(R.id.viewpage_1_tv_title);
		title.setText(detailsItemData.getSubject());
		// 作者头像
		ImageView imageView = (ImageView) itemView.findViewById(R.id.viewpage_1_iv_pic);

		imageLoader.displayImage(detailsItemData.getHost_pic(), imageView);
		// 评论、赞等的数量
		TextView count = (TextView) itemView.findViewById(R.id.viewpage_1_tv_content);
		count.setText(detailsItemData.getSummary());

		TextView all = (TextView) itemView.findViewById(R.id.viewpage_1_tv_all);
		all.setText(detailsItemData.getView() + "/" + detailsItemData.getLaud() + "/" + detailsItemData.getCollect()
				+ "/" + detailsItemData.getComment_count());
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
		imageLoader.displayImage(detailsItemData.getStep().get(position).getUrl(), viewHolder.imageView);
		// 内容
		viewHolder.content = (TextView) itemView.findViewById(R.id.tv_content);
		viewHolder.content.setText(detailsItemData.getStep().get(position).content);
		// 总共的步骤
		viewHolder.count = (TextView) itemView.findViewById(R.id.tv_comment_count);
		viewHolder.count.setText(detailsItemData.getComment_count() + "");
		viewHolder.comment_fl = (ViewGroup) itemView.findViewById(R.id.details_2_fl);

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
				// mController.openComment(mContext, false);
				Intent intent = new Intent(ShowDetailsActivity.this, CommentActivity.class);
				intent.putExtra("hand_id", hand_id);
				intent.putExtra("comment_count", detailsItemData.getComment_count());
//				Bundle bundle = new Bundle();
//				bundle.putSerializable("hand_id", hand_id);
//				intent.putExtras(bundle);
				startActivity(intent);
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
			this.finish();
			break;
		case R.id.details_iv_material:// 材料
			// Intent intent = new Intent();
			// intent.setClass(ShowDetailsActivity.this,
			// MaterialActivity.class);
			// startActivity(intent);
			break;
		case R.id.details_iv_laud:// 点赞
			// 动画、、服务器、插入到数据库中
			getObjectId(2);
			break;
		case R.id.details_iv_collect:// 收藏
			// 动画、、服务器、插入到数据库中
			getObjectId(1);
			break;
		case R.id.details_iv_share:// 分享
//			mController.getConfig().removePlatform(SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN);
//			// 是否只有已登录用户才能打开分享选择页
//			mController.openShare(this, false);
			AndroidShare as = new AndroidShare(
					ShowDetailsActivity.this,"成功分享了"+detailsItemData.getSubject(),
					detailsItemData.getStep().get(0).getUrl());
			as.show();
			break;
		}

	}

	/**
	 * 修改点赞
	 * 
	 * @param objectId
	 */
	private void updateLaud(String objectId) {
		final CourseDetailsBean item = new CourseDetailsBean();
		item.update(this, objectId, new UpdateListener() {

			@Override
			public void onSuccess() {
				laud.setImageResource(R.drawable.laud_yes);
				item.increment("laud", 1);
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Log.e("tag", arg0 + "/" + arg1);
			}
		});
	}

	/**
	 * 收藏
	 */
	private void getObjectId(final int flag) {
		BmobQuery<CourseDetailsBean> bQuery = new BmobQuery<CourseDetailsBean>();
		bQuery.addWhereEqualTo("hand_id", hand_id);
		bQuery.findObjects(this, new FindListener<CourseDetailsBean>() {

			@Override
			public void onError(int arg0, String arg1) {
				Log.e("tag", arg0 + "/" + arg1);
			}

			@Override
			public void onSuccess(List<CourseDetailsBean> arg0) {
				objectId = arg0.get(0).getObjectId();
				if (flag == 1) {
					updateCollect(objectId);
				} else {
					updateLaud(objectId);
				}
			}
		});

	}

	/**
	 * 修改CourseDetailsBean中的collection的数据
	 * 
	 * @param objectId
	 */
	private void updateCollect(String objectId) {

		final CourseDetailsBean item = new CourseDetailsBean();
		item.update(this, objectId, new UpdateListener() {

			@Override
			public void onSuccess() {
				collect.setImageResource(R.drawable.collect_yes);
				item.increment("collect", 1);
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Log.e("tag", arg0 + "/" + arg1);
			}
		});
	}

	/**
	 * 友盟分享授权回调
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
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
		// page切换则修改步骤
		if (position == 0) {
			text.setVisibility(View.INVISIBLE);
			icon.setVisibility(View.INVISIBLE);
		} else {
			icon.setVisibility(View.VISIBLE);
			text.setVisibility(View.VISIBLE);
			text.setText(position + "/" + detailsItemData.step.size());
		}
	}

}
