package com.mendale.app.ui.home.fragment;

import java.util.List;

import com.mendale.app.R;
import com.mendale.app.adapters.DarenGVAdapter;
import com.mendale.app.adapters.HotCourseGVAdapter;
import com.mendale.app.adapters.HotTypeGVAdapter;
import com.mendale.app.constants.DataURL;
import com.mendale.app.constants.Datas;
import com.mendale.app.tasks.HomeTask;
import com.mendale.app.ui.course.CourseInfoActivity;
import com.mendale.app.ui.home.HandUpAcitivity;
import com.mendale.app.ui.home.HotCourseActivity;
import com.mendale.app.ui.home.ShowDetailsActivity;
import com.mendale.app.ui.mycenter.MyCenterActivity;
import com.mendale.app.vo.HomeAllList;
import com.mendale.app.vo.HotCourseItemBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class HomeFragment extends Fragment implements OnClickListener, OnItemClickListener {

	/** 查看更多 */
	private TextView tv_Course_SeeMore;// 热门教程查看更多
	private Button btn_Course_SeeMore;
	private TextView tv_HandUp_SeeMore;// 手工达人
	private TextView tv_Type_SeeMore;
	private Button btn_Type_SeeMore;
	/** GridView */
	private GridView gv_Course;
	private GridView gv_HandUp;
	private ListView lv_Type;
	//
	private ImageView iv_loading;
	private LinearLayout ll_loading;
	private LinearLayout ll_main;
	/** adapter */
	private HotCourseGVAdapter courseAdapter;
	private DarenGVAdapter handUpAdapter;
	private HotTypeGVAdapter typeAdapter;
	/** DisplayImageOptions */
	private DisplayImageOptions options;
	// 数据
	private List<HotCourseItemBean> courseData;
	private HomeAllList allList;
	@SuppressLint("HandlerLeak")
	Handler mhandler = new Handler() {

		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case 1:
					if (msg.obj != null) {
						iv_loading.clearAnimation();
						ll_loading.setVisibility(View.INVISIBLE);
						ll_main.setVisibility(View.VISIBLE);
						allList = (HomeAllList) msg.obj;
						courseData = allList.getCourseData();
						courseAdapter = new HotCourseGVAdapter(getActivity(), courseData, options);
						gv_Course.setAdapter(courseAdapter);
						handUpAdapter = new DarenGVAdapter(getActivity(), allList.getDarenData(), options);
						gv_HandUp.setAdapter(handUpAdapter);
						typeAdapter = new HotTypeGVAdapter(getActivity(), allList.getTypeData(), options);
						lv_Type.setAdapter(typeAdapter);
					}
					break;
				default:
					break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		initView(view);
		initAnim();
		addListener();
		requestData();
		initImageOptions();
		return view;
	}

	/**
	 * 初始化动画
	 */
	private void initAnim() {
		Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.loading);
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
	 * 请求网络数据
	 */
	private void requestData() {
		new Thread() {

			public void run() {
				new HomeTask(getActivity(), mhandler).send(1, "utf-8", DataURL.HOME_URL);
			};
		}.start();
	}

	/**
	 * 点击事件
	 */
	private void addListener() {
		tv_Course_SeeMore.setOnClickListener(this);
		btn_Course_SeeMore.setOnClickListener(this);
		tv_HandUp_SeeMore.setOnClickListener(this);
		tv_Type_SeeMore.setOnClickListener(this);
		btn_Type_SeeMore.setOnClickListener(this);
		gv_Course.setOnItemClickListener(this);
		gv_HandUp.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(getActivity(), MyCenterActivity.class);
				startActivity(intent);
			}
		});
		lv_Type.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				Intent intent = new Intent(getActivity(), CourseInfoActivity.class);
				Bundle bundle=new Bundle();
				bundle.putSerializable("courseItem",Datas.cChildList.get(pos) );
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	/**
	 * 初始化界面
	 * 
	 * @param view
	 */
	private void initView(View view) {
		tv_Course_SeeMore = (TextView) view.findViewById(R.id.tv_course_see_more);
		btn_Course_SeeMore = (Button) view.findViewById(R.id.btn_course_see_more);
		tv_HandUp_SeeMore = (TextView) view.findViewById(R.id.tv_hand_up_see_more);
		tv_Type_SeeMore = (TextView) view.findViewById(R.id.tv_type_see_more);
		btn_Type_SeeMore = (Button) view.findViewById(R.id.btn_type_see_more);
		gv_Course = (GridView) view.findViewById(R.id.gv_coursee);
		gv_HandUp = (GridView) view.findViewById(R.id.gv_hand_up);
		lv_Type = (ListView) view.findViewById(R.id.lv_type);
		iv_loading = (ImageView) view.findViewById(R.id.iv_loading);
		ll_loading = (LinearLayout) view.findViewById(R.id.ll_home_loading);
		ll_main = (LinearLayout) view.findViewById(R.id.ll_main);
		// 加载数据库中的数据
		// courseData=new ArrayList<HotCourseList>();
		// courseAdapter=new HotCourseGVAdapter();
		// gv_Course.setAdapter(courseAdapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_course_see_more:
			case R.id.btn_course_see_more:
				Intent intent = new Intent(getActivity(), HotCourseActivity.class);
				startActivity(intent);
				break;
			case R.id.tv_hand_up_see_more:
				Intent intent2 = new Intent(getActivity(), HandUpAcitivity.class);
				startActivity(intent2);
				break;
			case R.id.tv_type_see_more:
			case R.id.btn_type_see_more:
				if (cFragment != null) {
					cFragment.changeFragmentClick();
				}
				break;
		}
	}

	public ChangeFragment cFragment;

	public ChangeFragment getcFragment() {
		return cFragment;
	}

	public void setcFragment(ChangeFragment cFragment) {
		this.cFragment = cFragment;
	}

	// 通过回调实现fragment的切换
	public interface ChangeFragment {

		public void changeFragmentClick();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(getActivity(), ShowDetailsActivity.class);
		// 进入详情页
		String detail_url = DataURL.DETAILS_RMJC + courseData.get(position).getHand_id();
		intent.putExtra("detail_url", detail_url);
		intent.putExtra("step", courseData.get(position).getStep_count());
		startActivity(intent);
	}
}
