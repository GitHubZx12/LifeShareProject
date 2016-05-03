package com.mendale.app.ui.home.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mendale.app.R;
import com.mendale.app.adapters.CourseGvAdapter;
import com.mendale.app.constants.Datas;
import com.mendale.app.pojo.CourseChildPojo;
import com.mendale.app.pojo.CoursePoJo;
import com.mendale.app.ui.course.CourseInfoActivity;
import com.umeng.socialize.utils.Log;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * 教程模块
 * 
 * @author Administrator
 *
 */
public class CourseFragment extends Fragment implements OnItemClickListener {

	/** 动态设置gridview的高度 */
	private CourseGvAdapter mAdapter;
	private GridView gv_Course;
	private ImageView iv_loading;
	private LinearLayout ll_loading;
	//数据
	private List<CoursePoJo> courseData;
	
	@SuppressLint("HandlerLeak")
	private Handler mhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case 1:
						iv_loading.clearAnimation();
						ll_loading.setVisibility(View.INVISIBLE);
						gv_Course.setVisibility(View.VISIBLE);
						mAdapter = new CourseGvAdapter(getActivity(), formatList(courseData));
						gv_Course.setAdapter(mAdapter);
					break;
				default:
					break;
			}
		}
	};

	/**
	 * 提取child的数据
	 * 
	 * @param courseList
	 * @return
	 */
	private List<CourseChildPojo> formatList(List<CoursePoJo> courseList) {
		Datas.list = new ArrayList<Map<String, String>>();
		Datas.cChildList=new ArrayList<CourseChildPojo>();
		CourseChildPojo item;
		for(int i=0;i<courseList.size();i++){
			for (int j = 0; j < courseList.get(i).getChild().size(); j++) {
				item=courseList.get(i).getChild().get(j);
				Datas.cChildList.add(item);
			}
		}
		return Datas.cChildList;
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_course, container, false);
		intData();
		initView(view);
		initAnim();
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
	 * 初始化界面
	 */
	private void initView(View view) {
		gv_Course = (GridView) view.findViewById(R.id.gv_course_classify);
		iv_loading = (ImageView) view.findViewById(R.id.iv_loading);
		ll_loading = (LinearLayout) view.findViewById(R.id.ll_course_loading);
		gv_Course.setOnItemClickListener(this);
	}

	/**
	 * 初始化数据
	 */
	private void intData() {
		BmobQuery<CoursePoJo>bmobQuery=new BmobQuery<CoursePoJo>();
		bmobQuery.findObjects(getActivity(), new FindListener<CoursePoJo>() {
			
			@Override
			public void onSuccess(List<CoursePoJo> arg0) {
				courseData=arg0;
				Datas.courseList=arg0;
				mhandler.sendEmptyMessage(1);
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				Log.e("tag",arg1);
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		Intent intent = new Intent(getActivity(), CourseInfoActivity.class);
		Bundle bundle=new Bundle();
		bundle.putSerializable("courseItem",Datas.cChildList.get(pos) );
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
