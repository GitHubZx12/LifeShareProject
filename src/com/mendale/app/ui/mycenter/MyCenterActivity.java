package com.mendale.app.ui.mycenter;

import java.io.File;
import java.util.Date;

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

import com.mendale.app.R;
import com.mendale.app.constants.URLS;
import com.mendale.app.pojo.MemberPojo;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.ui.home.MainPageActivity;
import com.mendale.app.ui.home.fragment.RecordFragment;
import com.mendale.app.ui.home.menu.UpLoadCourseActivity;
import com.mendale.app.ui.mycenter.setting.MarkManActivity;
import com.mendale.app.utils.ExitApplication;
import com.mendale.app.utils.Utils;
import com.mendale.app.utils.background.MyScrollView;
import com.mendale.app.utils.imageUtils.ImageOpera;
import com.mendale.app.utils.imageUtils.RoundImageView;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 我的主界面
 * 
 * @author ouyanghao
 * 
 */
public class MyCenterActivity extends BaseActivity implements OnClickListener,
		OnTouchListener {

	/** 本界面用到的控件 */
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
	 * 积分
	 */
	private TextView mycenter_total;
	/**
	 * vip级别图标
	 */
	private ImageView mycenter_vip_image;
	/**
	 * vip级别名称
	 */
	private TextView mycenter_vip_name;
	/**
	 * 我的管家
	 */
	private LinearLayout mycenter_mysteward;
	/**
	 * 权益中心
	 */
	private LinearLayout mycenter_rightscent;
	/**返回*/
	private ImageView iv_back;
	/**
	 * 服务记录
	 */
	private LinearLayout mycenter_serve;
	/**
	 * 设置
	 */
	
	private MyScrollView scrollview;
	private TextView view;
	LinearLayout mycenter_setting;
	private MemberPojo memberbean;
	private Context context;
	
	private int current_id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mycenter);
		/**加入全局退出队列------------规范：都要加*/
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
		mycenter_vip_name = (TextView) findViewById(R.id.mycenter_vip_name);
		mycenter_mysteward = (LinearLayout) findViewById(R.id.mycenter_mysteward);
		mycenter_rightscent = (LinearLayout) findViewById(R.id.mycenter_rightscent);
		mycenter_serve = (LinearLayout) findViewById(R.id.mycenter_serve);
		mycenter_setting = (LinearLayout) findViewById(R.id.mycenter_setting);
		mycenter_AllData = (LinearLayout) findViewById(R.id.mycenter_AllData);
		iv_back=(ImageView) findViewById(R.id.iv_mycenter_back);

		view = (TextView) findViewById(R.id.mycenter_background_view);
		scrollview = (MyScrollView) findViewById(R.id.myScrollView);
		scrollview.setImageView(mycenter_background_image);
		context = this;
	}

	/**
	 * 给控件赋值
	 */
	private void initData() {
		mycenter_background_image.setImageResource(R.drawable.crafter_personal_bg);
		mycenter_head_image.setImageResource(R.drawable.defult_avator);
		mycenter_name.setText("陈女士");
		mycenter_total.setText("江苏徐州");
		mycenter_vip_name.setText("一级");
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
	 * 弹出选择相册的dialog
	 * 
	 * @param
	 */
	public void showPhoneDialog(final int flag) {
		showDigLog("相册", "取消", new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.openSysPhone(flag, MyCenterActivity.this);
				closeDialog();
			}
		}, new OnClickListener() {

			@Override
			public void onClick(View v) {
				closeDialog();
			}
		});
	}

	/**
	 * onclick 事件
	 */
	@Override
	public void onClick(View v) {
		Intent intent=new Intent(this,MainPageActivity.class);
		switch (v.getId()) {
		case R.id.mycenter_background_image:// 背景
			showPhoneDialog(1);
			break;
		case R.id.mycenter_AllData:// 为了不触发背景点击事件
			break;
		case R.id.mycenter_data:// 资料
			startActivity(MarkManActivity.class);
			break;
		case R.id.mycenter_mysteward:// 发布教程
			mycenter_mysteward.setBackgroundColor(getResources()
					.getColor(R.color.gray_x));
			startActivity(UpLoadCourseActivity.class);
			break;
		case R.id.mycenter_rightscent:// 收藏教程
			mycenter_rightscent.setBackgroundColor(getResources().getColor(
					R.color.gray_x));
			startActivity(CollectCourseActivity.class);
			break;
		case R.id.mycenter_setting:// 发布记录
			mycenter_setting.setBackgroundColor(getResources().getColor(
					R.color.gray_x));
			intent.putExtra("recordflag",1);
			startActivity(intent);
			break;
		case R.id.mycenter_serve://收藏记录
			mycenter_serve.setBackgroundColor(getResources().getColor(
					R.color.gray_x));
//			startActivity(CollectRecordActivity.class);
			intent.putExtra("recordflag",2);
			startActivity(intent);
			break;
		case R.id.mycenter_background_view:// 服务记录
			showPhoneDialog(1);
			break;
		case R.id.iv_mycenter_back:
			this.finish();
			break;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			String sdStatus = Environment.getExternalStorageState();
			if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
				showToast("请确认已经插入SD卡");
				return;
			}
			if (data.getData() == null) {
				showToast("返回路径为空");
				return;
			}
			Uri originalUri = data.getData(); // 在系统中图片的路径
			if (originalUri == null) {
				showToast("图片不存在");
				return;
			}
			String path = getrealPath(originalUri);
			switch (requestCode) {
			case 1:
				mycenter_background_image.setTag(path);
				ImageOpera.getInstance(context).loadImage(
						"file://" + path,
						mycenter_background_image,
						new imageListener("background"));
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 这里没有用类实现接口是因为需要传名字过来，来给图片命名。
	 * 
	 * @author jsx
	 * 
	 */
	class imageListener implements ImageLoadingListener {

		private String name;

		private imageListener(String name) {
			this.name = name;
		}

		@Override
		public void onLoadingCancelled(String arg0, View arg1) {
			System.out.println(1);
		}

		@Override
		public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
			if (arg2 != null) {
				File file = new File(URLS.SDCARD_DIR + "ButlerImage");
				if (!file.exists()) {
					file.mkdirs();
				}
				try {
					String path = file.getCanonicalPath() + "/" + new Date().getTime() + ".jpg";
					// 保存到本地
					ImageOpera.getInstance(context).compressToFile(arg2, path,
							200).recycle();
					arg1.setTag(path);
					// 假如修改背景，直接上传
//					if (arg1.getId() == R.id.head_image) {
//						showLoadDialog("正在修改背景,请稍候");
//						closeLoadDialog();
//						showToast("修改背景成功");
//					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
			System.out.println(1);
		}

		@Override
		public void onLoadingStarted(String arg0, View arg1) {
			System.out.println(1);
		}
	}

	/**
	 * 得到图片所在的sd卡路径
	 * 
	 * @param mImageCaptureUri
	 *            系统路径
	 */
	@SuppressWarnings("finally")
	private String getrealPath(Uri mImageCaptureUri) {
		String path = null;
		Cursor cursor = null;
		try {
			cursor = this.getContentResolver().query(mImageCaptureUri, null,
					null, null, null);
			if (cursor.moveToFirst()) {
				path = cursor.getString(cursor.getColumnIndex("_data"));// 获取绝对路径
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			return path;
		}
	}

	
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}

}
