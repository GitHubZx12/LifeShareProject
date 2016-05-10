package com.mendale.app.ui.home.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.mendale.app.R;
import com.mendale.app.adapters.RecordLvAdapter;
import com.mendale.app.pojo.RecordItemBean;
import com.mendale.app.ui.record.UpLoadRecordActivity;
import com.mendale.app.utils.pullToRefreshUtils.PullToRefreshConfig;
import com.mendale.app.utils.pullToRefreshUtils.view.XListView;
import com.mendale.app.utils.pullToRefreshUtils.view.XListView.IXListViewListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.umeng.socialize.utils.Log;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * 记录
 * @author zx
 *
 */
public class RecordFragment extends Fragment implements IXListViewListener,OnClickListener{

	private XListView mListView;
	private RecordLvAdapter mAdapter;
	private DisplayImageOptions options; // DisplayImageOptions是用于设置图片显示的类
	/** 显示没有更多数据 */												
	private TextView tv_no_data;
	//动画相关
	private ImageView iv_loading;
    private LinearLayout ll_loading;
    /**记录*/
	private TextView recordAction;
	//
	private List<RecordItemBean> recordList;

	@SuppressLint("HandlerLeak")
	private Handler mhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				if (recordList != null) {
					iv_loading.clearAnimation();
					ll_loading.setVisibility(View.INVISIBLE);
					mListView.setVisibility(View.VISIBLE);
					mAdapter = new RecordLvAdapter(getActivity(), recordList,
							options);
					mListView.setAdapter(mAdapter);
				}
				break;

			default:
				break;
			}

		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_record, container, false);
		initView(view);
		initAnim();
		initData();
		initImageOptions();
		return view;
	}
	
	/**
	 * 初始化动画
	 */
	private void initAnim() {
		Animation animation=AnimationUtils.loadAnimation(getActivity(),R.anim.loading);
		iv_loading.startAnimation(animation);
	}
	/**
	 * 请求接口
	 */
	private void initData() {
		BmobQuery<RecordItemBean>bmobQuery=new BmobQuery<RecordItemBean>();
		bmobQuery.findObjects(getActivity(), new FindListener<RecordItemBean>() {
			@Override
			public void onError(int arg0, String arg1) {
				Log.e("tag",arg1);
			}

			@Override
			public void onSuccess(List<RecordItemBean> arg0) {
				recordList=arg0;
				mhandler.sendEmptyMessage(1);
			}
		});
	}

	/**
	 * 初始化界面
	 * 
	 * @param view
	 */
	private void initView(View view) {
		mListView = (XListView) view.findViewById(R.id.listview_record);
		mListView.setPullRefreshEnable(PullToRefreshConfig.pullRefreshEnable);// 设置是否可以下拉刷新
		mListView.setPullLoadEnable(PullToRefreshConfig.pullLoadEnable);// 设置是否可以上拉加载
		//
		iv_loading=(ImageView) view.findViewById(R.id.iv_loading);
		ll_loading=(LinearLayout) view.findViewById(R.id.ll_record_loading);
		//
		recordAction=(TextView) view.findViewById(R.id.iv_record_action);
		//
		mListView.setXListViewListener(this);// 设置监听
		recordAction.setOnClickListener(this);
	}

	/**
	 * 等待状态，顶部显示上次刷新时间
	 * 
	 * @author 张静
	 * @Time 2015年12月3日下午6:05:12
	 */
	@SuppressLint("SimpleDateFormat")
	private void onLoad() {
		mListView.stopRefresh();// 停止刷新
		mListView.stopLoadMore();// 停止"加载更多"
		// 获得系统当前时间
		SimpleDateFormat formatter = new SimpleDateFormat(
				PullToRefreshConfig.strDateFormat);
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);// 格式化
		mListView.setRefreshTime(str);// 给listview设置刷新时间
		 if (recordList.size() == 15) {
		 // 当数目大于15的时候
		 mListView.setPullLoadEnable(false);// 禁止上拉加载
		 mListView.setPullRefreshEnable(false);// 禁止下拉刷新
		 tv_no_data.setVisibility(View.VISIBLE);// 显示没有更多数据
		 }
	}
	
	@Override
	public void onRefresh() {
		mhandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mhandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				onLoad();
			}
		}, 2000);
	}

	/**
	 * 上传记录
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_record_action://记录
			Intent intent=new Intent(getActivity(),UpLoadRecordActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	/**
	 * 初始化图片的相关参数
	 */
	@SuppressWarnings("deprecation")
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
