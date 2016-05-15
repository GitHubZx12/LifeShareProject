package com.mendale.app.ui.mycenter;

import java.util.List;

import com.mendale.app.R;
import com.mendale.app.adapters.CourseInfoLvAdapter2;
import com.mendale.app.pojo.MyUser;
import com.mendale.app.pojo.Record;
import com.mendale.app.ui.base.BaseActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.umeng.socialize.utils.Log;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;

/**
 * 收藏记录
 * @author zx
 *
 */
public class LaunchRecordActivity extends BaseActivity {

	protected static final String TAG = "LaunchRecordActivity";
	private ListView mListView;
	private CourseInfoLvAdapter2 mAdapter;
	private DisplayImageOptions options; // DisplayImageOptions是用于设置图片显示的类
	/** 显示没有更多数据 */
	private TextView tvTip;
	private ImageView iv_loading;
	private LinearLayout ll_loading;
	/**数据*/
	List<Record>recordList ;
	private MyUser user;
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_info);
		initHeaderView();
		initView();
		initAnim();
		initImageOptions();
		initData();
	}
	
	/**
	 * 查询一对一关联
	 */
	private void initData() {
		BmobQuery<Record> query=new BmobQuery<Record>();
		query.addWhereEqualTo("author", user);//查询当前用户发布的所有记录
		query.order("-updateAd");
		query.include("author");//希望在查询记录信息同时也把发布人的信息查询出来
		query.findObjects(this, new FindListener<Record>() {
			
			@Override
			public void onSuccess(final List<Record> arg0) {
				recordList=arg0;
//				getCollectSize();
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						if(null!=arg0){
							ll_loading.setVisibility(View.GONE);
							iv_loading.setVisibility(View.GONE);
							mListView.setVisibility(View.VISIBLE);
							mAdapter = new CourseInfoLvAdapter2(LaunchRecordActivity.this,arg0, options);
							mListView.setAdapter(mAdapter);	
						}else{
							iv_loading.setVisibility(View.GONE);
							mListView.setVisibility(View.GONE);
							tvTip.setText("还没发过记录");
						}
					}
				});
			}

			@Override
			public void onError(int arg0, String arg1) {
				Log.e("tag",arg0+arg1);
			}
		});
	}
	private void getCollectSize() {
		for(int i=0;i<recordList.size();i++){
			//查询喜欢这个记录的所有用户，因此查询的是用户表
			BmobQuery<MyUser> collQuery=new BmobQuery<MyUser>();
			Record collRecord=new Record();
			collRecord.setObjectId(recordList.get(i).getObjectId());
			//likes是Record表中的字段，用来存储所有喜欢该帖子的用户
			collQuery.addWhereRelatedTo("likes", new BmobPointer(collRecord));
			collQuery.findObjects(this, new FindListener<MyUser>() {

				@Override
				public void onError(int arg0, String arg1) {
					Log.e(TAG,arg0+arg1);
					
				}

				@Override
				public void onSuccess(List<MyUser> arg0) {
					showToast("总共有"+arg0.size()+"的人喜欢该记录");
				}
			});
			
		}
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
	 * 初始化界面
	 * 
	 * @param view
	 */
	private void initView() {
		mListView = (ListView) findViewById(R.id.listview_course_info);
		iv_loading = (ImageView) findViewById(R.id.iv_course_info_loading);
		ll_loading = (LinearLayout) findViewById(R.id.ll_course_info_loading);
		tvTip=(TextView) findViewById(R.id.tv_course_info_tips);
		
		user=(MyUser) getIntent().getSerializableExtra("userInfo");
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

}
