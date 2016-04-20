package com.mendale.app.ui.mycenter;

import java.util.List;

import com.mendale.app.R;
import com.mendale.app.adapters.HotCourseGVAdapter;
import com.mendale.app.constants.DataURL;
import com.mendale.app.tasks.HomeTask;
import com.mendale.app.ui.home.ShowDetailsActivity;
import com.mendale.app.utils.ExitApplication;
import com.mendale.app.vo.HomeAllList;
import com.mendale.app.vo.HotCourseItemBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 收藏教程
 * @author Administrator
 *
 */
public class CollectCourseActivity extends Activity implements OnClickListener,OnItemClickListener{
	
	private ImageView back;
	private TextView title;
	private GridView mGridView;
	/**adapter*/
	private HotCourseGVAdapter courseAdapter;
	/**DisplayImageOptions*/
	private DisplayImageOptions options;
	//数据
	private List<HotCourseItemBean>courseData;
	
	private Handler mhandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			HomeAllList allList=(HomeAllList) msg.obj;
			courseData=allList.getCourseData();
			courseAdapter=new HotCourseGVAdapter(CollectCourseActivity.this,courseData,options);
			mGridView.setAdapter(courseAdapter);
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
		ExitApplication.getInstance().addActivity(this);
		initData();
		initView();
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		back=(ImageView) findViewById(R.id.iv_launch_back);
		title=(TextView) findViewById(R.id.tv_launch_title);
		mGridView=(GridView) findViewById(R.id.gv_launch);
		
		back.setOnClickListener(this);
		mGridView.setOnItemClickListener(this);
		
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
	/**
	 * 初始化数据
	 */
	private void initData() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				//TODO 请求个人中心的发布教程的数据
				new HomeTask(CollectCourseActivity.this, mhandler).send(1, "utf-8", DataURL.HOME_URL);
				
			}
		}).start();
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent=new Intent(this,ShowDetailsActivity.class);
		// 进入详情页
		String detail_url = DataURL.DETAILS_RMJC+courseData.get(position).getHand_id();
		intent.putExtra("detail_url", detail_url);
		intent.putExtra("step", courseData.get(position).getStep_count());
		startActivity(intent);
		
	}

	@Override
	public void onClick(View v) {
		finish();
	}
	
	

	

}
