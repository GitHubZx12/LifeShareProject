package com.mendale.app.ui.mycenter;

import com.mendale.app.R;
import com.mendale.app.adapters.HotCourseGVAdapter;
import com.mendale.app.constants.DataURL;
import com.mendale.app.pojo.CollectData;
import com.mendale.app.pojo.CourseData;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.ui.home.ShowDetailsActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**
 *收藏教程
 * @author Administrator
 *
 */
public class CollectCourseActivity extends BaseActivity implements OnClickListener,OnItemClickListener{
	
	private GridView mGridView;
	/**adapter*/
	private HotCourseGVAdapter courseAdapter;
	/**DisplayImageOptions*/
	private DisplayImageOptions options;
	private CollectData collectData=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
		initHeaderView();
		initView();
		getIntentData();
	}
	
	/**
	 * 初始化头部
	 */
	private void initHeaderView() {
		setNavigationTitle("收藏额教程");
		setNavigationLeftBtnText("");
	}
	@Override
	public void leftButtonOnClick() {
		super.leftButtonOnClick();
		this.finish();
	}

	
	/**
	 * 初始化view
	 */
	private void initView() {
		mGridView=(GridView) findViewById(R.id.gv_launch);
		
		mGridView.setOnItemClickListener(this);
		
	}

	/**
	 * 得到传递过来的参数
	 */
	private void getIntentData() {
		collectData=(CollectData) getIntent().getSerializableExtra("collectData");
		if(null==collectData){
			return;
		}
		courseAdapter=new HotCourseGVAdapter(CollectCourseActivity.this,collectData.getList(),options);
		mGridView.setAdapter(courseAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent=new Intent(this,ShowDetailsActivity.class);
		// 进入详情页
		String detail_url = DataURL.DETAILS_RMJC+collectData.getList().get(position).getHand_id();
		intent.putExtra("detail_url", detail_url);
		intent.putExtra("step", collectData.getList().get(position).getStep_count());
		startActivity(intent);
		
	}

	@Override
	public void onClick(View v) {
		finish();
	}
	/**
	 * 初始化图片的相关参数
	 */
	private void initImageOptions() {
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
