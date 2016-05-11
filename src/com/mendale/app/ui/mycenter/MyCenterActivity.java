package com.mendale.app.ui.mycenter;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.mendale.app.R;
import com.mendale.app.application.MobileApplication;
import com.mendale.app.constants.DataURL;
import com.mendale.app.pojo.MemberPojo;
import com.mendale.app.pojo.MyUser;
import com.mendale.app.pojo.OtherUser;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.ui.mycenter.setting.MarkManActivity;
import com.mendale.app.utils.ExitApplication;
import com.mendale.app.utils.Utils;
import com.mendale.app.utils.background.MyScrollView;
import com.mendale.app.utils.imageUtils.RoundImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.socialize.utils.Log;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;

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
	/**
	 * 数据
	 */
	private ArrayList<OtherUser> mDatas;
	OtherUser other = null;
	
	/**DisplayImageOptions*/
	private DisplayImageOptions options;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mycenter);
		/** 加入全局退出队列------------规范：都要加 */
		ExitApplication.getInstance().addAllActivity(this);
		init();
		initOptions();
		setLinstener();
		initData();
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
		int flag=getIntent().getIntExtra("flag", 0);
		if(flag==1){
			MobileApplication application = (MobileApplication) getApplication();
			MyUser user=MyUser.getCurrentUser(this, MyUser.class);
			//
			mycenter_background_image.setImageResource(R.drawable.crafter_personal_bg);
			mycenter_head_image.setImageResource(R.drawable.defult_avator);
			mycenter_name.setText(user.getUsername());
			mycenter_total.setText("江苏徐州");
			mycenter_vip_image.setImageResource(R.drawable.lv1);
			mycenter_sex_image.setImageResource(R.drawable.vip_icon_wowam);
//			mycenter_background_image.setImageResource(R.drawable.crafter_personal_bg);
//			mycenter_head_image.setImageResource(R.drawable.defult_avator);
//			mycenter_name.setText(application.getmUserInfo().getUsername());
//			mycenter_total.setText("江苏徐州");
//			mycenter_vip_image.setImageResource(R.drawable.lv1);
//			mycenter_sex_image.setImageResource(R.drawable.vip_icon_wowam);
		}else{
			String id=getIntent().getStringExtra("id");
			doGet(id);
		}
		
	}

		/**
		 * 获取数据
		 * @param url
		 */
		private void doGet(String id) {
			final String fullUrl=DataURL.PEOPLE_DETAILS+id;
			new Thread(new Runnable() {
				@Override
				public void run() {
					OkHttpClient okHttpClient = new OkHttpClient();
					// 创建一个request
					Request request = new Request.Builder().url(fullUrl).build();
					Call call = okHttpClient.newCall(request);
					call.enqueue(new Callback() {

						@Override
						public void onResponse(Response arg0) throws IOException {
							parserData(arg0.body().string());
						}

						@Override
						public void onFailure(Request arg0, IOException arg1) {
						}
					});
				}
			}).start();
			
		}
		
		/**
		 * 解析數據
		 * @param string
		 */
		private void parserData(String string) {
			Gson gson = new Gson();
			if (!Utils.isEmpty(string)) {
					other=gson.fromJson(string, OtherUser.class);
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							mycenter_background_image.setImageResource(R.drawable.crafter_personal_bg);
							ImageLoader imageLoader=ImageLoader.getInstance();
							imageLoader.displayImage(other.getFace_pic(), mycenter_head_image);
							mycenter_name.setText(other.getUname());
							mycenter_total.setText(other.getRegion_name());
//							mycenter_vip_image.setImageResource(R.drawable.lv1);
//							mycenter_sex_image.setImageResource(R.drawable.vip_icon_wowam);
						}
					});
			}
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
	/**
	 * onclick 事件
	 */
	@Override
	public void onClick(View v) {
		Intent intent;
		Bundle bundle=new Bundle();
		switch (v.getId()) {
			case R.id.mycenter_background_image:// 背景
				// showPhoneDialog(1);
				break;
			case R.id.mycenter_AllData:// 为了不触发背景点击事件
				break;
			case R.id.mycenter_data:// 资料
				intent = new Intent(this, MarkManActivity.class);
				startActivityForResult(intent, 2);
				break;
			case R.id.mycenter_mysteward:// 发布教程
				mycenter_mysteward.setBackgroundColor(getResources().getColor(R.color.gray_x));
				intent=new Intent(this,LaunchCourseActivity.class);
//				bundle.putSerializable("courseData", other.getCourseData());
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			case R.id.mycenter_rightscent:// 收藏教程
				mycenter_rightscent.setBackgroundColor(getResources().getColor(R.color.gray_x));
				intent=new Intent(this,CollectCourseActivity.class);
//				bundle.putSerializable("collectData", other.getCollectData());
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			case R.id.mycenter_setting:// 发布记录
				mycenter_setting.setBackgroundColor(getResources().getColor(R.color.gray_x));
				intent=new Intent(this,LaunchRecordActivity.class);
//				bundle.putSerializable("opusData", other.getOpusData());
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			case R.id.mycenter_serve:// 收藏记录
				mycenter_serve.setBackgroundColor(getResources().getColor(R.color.gray_x));
				intent=new Intent(this,CollectRecordActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
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
		switch (resultCode) {
			case 2:
				showToast(data.getSerializableExtra("userinfo").toString());
				break;
			default:
				break;
		}
	}
	/**
	 * 初始化图片的相关参数
	 */
	private void initOptions() {
		// 使用DisplayImageOptions.Builder()创建DisplayImageOptions
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.image_guidestep_defult) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.image_guidestep_defult) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.image_guidestep_defult) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				.displayer(new RoundedBitmapDisplayer(0)) // 设置成圆角图片
				.build(); // 创建配置过得DisplayImageOption对象
	}
}
