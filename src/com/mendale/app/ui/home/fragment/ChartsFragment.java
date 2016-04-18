package com.mendale.app.ui.home.fragment;

import java.util.List;

import com.mendale.app.R;
import com.mendale.app.adapters.ChartsLvAdapter;
import com.mendale.app.constants.DataURL;
import com.mendale.app.constants.Datas;
import com.mendale.app.pojo.ChartsPoJo;
import com.mendale.app.pojo.CoursePoJo;
import com.mendale.app.tasks.ChartsTask;
import com.mendale.app.tasks.ChartsTask2;
import com.mendale.app.tasks.CourseTask;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * 排行榜
 * 
 * @author Administrator
 * 
 */
public class ChartsFragment extends Fragment implements OnItemClickListener {

	private ChartsLvAdapter mAdapter;
	private ListView mListView;
	private LinearLayout ll_loading;
	private ImageView iv_loading;
	private List<ChartsPoJo> mDatas;
	private Handler mhandler = new Handler() {

		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case 1:
					if (msg.obj != null) {
						iv_loading.clearAnimation();
						ll_loading.setVisibility(View.INVISIBLE);
						mListView.setVisibility(View.VISIBLE);
						mDatas = (List<ChartsPoJo>) msg.obj;
						mAdapter = new ChartsLvAdapter(getActivity(), mDatas);
						mListView.setAdapter(mAdapter);
					}
					break;
				default:
					break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_charts, container, false);
		intData();
		initView(view);
		initAnim();
		return view;
	}

	/**
	 * 动画
	 */
	private void initAnim() {
		Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.loading);
		iv_loading.startAnimation(animation);
	}

	/**
	 * 初始化数据
	 */
	private void intData() {
		new Thread() {

			public void run() {
				new ChartsTask(getActivity(), mhandler).send(1, "utf-8", DataURL.CHARTS_URL);
			};
		}.start();
	}

	/**
	 * 初始化界面
	 */
	private void initView(View view) {
		ll_loading = (LinearLayout) view.findViewById(R.id.ll_charts_loading);
		iv_loading = (ImageView) view.findViewById(R.id.iv_charts_loading);
		mListView = (ListView) view.findViewById(R.id.listview_charts);
		mListView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	}
}
