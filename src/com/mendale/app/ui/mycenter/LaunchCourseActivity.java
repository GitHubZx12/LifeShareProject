package com.mendale.app.ui.mycenter;

import java.util.List;

import com.mendale.app.R;
import com.mendale.app.adapters.HotCourseGVAdapter;
import com.mendale.app.constants.DataURL;
import com.mendale.app.pojo.CourseData;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.ui.home.ShowDetailsActivity;
import com.mendale.app.vo.HomeAllList;
import com.mendale.app.vo.HomeHotCoursePoJo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.umeng.socialize.utils.Log;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**
 * 发布教程
 * @author Administrator
 *
 */
public class LaunchCourseActivity extends BaseActivity implements OnClickListener,OnItemClickListener{
	
	private GridView mGridView;
	/**adapter*/
	private HotCourseGVAdapter courseAdapter;
	/**DisplayImageOptions*/
	private DisplayImageOptions options;
	private CourseData courseData=null;
	
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
		setNavigationTitle("发布课程");
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
		courseData=(CourseData) getIntent().getSerializableExtra("courseData");
		if(null==courseData){
			return;
		}
		courseAdapter=new HotCourseGVAdapter(LaunchCourseActivity.this,courseData.getList(),options);
		mGridView.setAdapter(courseAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent=new Intent(this,ShowDetailsActivity.class);
		// 进入详情页
		String detail_url = DataURL.DETAILS_RMJC+courseData.getList().get(position).getHand_id();
		intent.putExtra("detail_url", detail_url);
		intent.putExtra("step", courseData.getList().get(position).getStep_count());
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
