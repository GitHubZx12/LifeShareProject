package com.mendale.app.ui.home.fragment;

import static com.mendale.app.R.id.tv_describe;
import static com.mendale.app.R.id.tv_photo;
import static com.mendale.app.R.id.tv_time;
import static com.mendale.app.R.id.tv_title;

import java.util.List;

import com.mendale.app.R;
import com.mendale.app.adapters.BaseAdapterHelper;
import com.mendale.app.adapters.QuickAdapter;
import com.mendale.app.pojo.HelpCoursePoJo;
import com.mendale.app.utils.popwindow.EditPopupWindow;
import com.mendale.app.utils.popwindow.IPopupItemClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 问答Fragment
 * 侧滑菜单部分的求教程的显示列表
 * 
 * @author zhangxue
 * @date 2016年4月17日
 */
public class QAFragment extends Fragment implements OnClickListener,
IPopupItemClick, OnItemLongClickListener {

	private ListView mListView;
	protected QuickAdapter<HelpCoursePoJo> mAdapter;// 失物
	private DisplayImageOptions options; // DisplayImageOptions是用于设置图片显示的类
	/** 显示没有更多数据 */
	private TextView tv_no_data;
	private ImageView iv_loading;
	private LinearLayout ll_loading;
	private EditPopupWindow mPopupWindow;
	private int position;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_qa, container, false);
		initView(view);
		initAnim();
		initData();
		initImageOptions();
		return view;
	}
	/**
	 * 初始化界面
	 * 
	 * @param view
	 */
	private void initView(View view) {
		mListView = (ListView) view.findViewById(R.id.listview_record);
		iv_loading = (ImageView) view.findViewById(R.id.iv_loading);
		ll_loading = (LinearLayout) view.findViewById(R.id.ll_record_loading);
		mListView.setOnItemLongClickListener(this);
		// 初始化长按弹窗
		initEditPop();
	}
	private void initEditPop() {
		mPopupWindow = new EditPopupWindow(getActivity(), 200, 48);
		mPopupWindow.setOnPopupItemClickListner(this);
	}

	/**
	 * 请求接口
	 */
	private void initData() {
		if (mAdapter == null) {
			mAdapter = new QuickAdapter<HelpCoursePoJo>(getActivity(), R.layout.listview_item2_qa) {
				@Override
				protected void convert(BaseAdapterHelper helper, HelpCoursePoJo items) {
					helper.setText(tv_title, items.getTitle())
							.setText(tv_describe, items.getContent())
							.setText(tv_time, items.getCreatedAt())
							.setText(tv_photo, items.getPhone());
				}
			};
		}
		mListView.setAdapter(mAdapter);
		// 默认加载失物界面
		queryLosts();
	}
	

	/**
	 * 查询全部失物信息 queryLosts
	 * 
	 * @return void
	 * @throws
	 */
	private void queryLosts() {
		showView();
		BmobQuery<HelpCoursePoJo> query = new BmobQuery<HelpCoursePoJo>();
		query.order("-createdAt");// 按照时间降序
		query.findObjects(getActivity(), new FindListener<HelpCoursePoJo>() {

			@Override
			public void onSuccess(List<HelpCoursePoJo> items) {
				// TODO Auto-generated method stub
				mAdapter.clear();
				if (items == null || items.size() == 0) {
					showErrorView(0);
					mAdapter.notifyDataSetChanged();
					return;
				}
				iv_loading.setVisibility(View.GONE);
				mAdapter.addAll(items);
				mListView.setAdapter(mAdapter);
			}

			@Override
			public void onError(int code, String arg0) {
				// TODO Auto-generated method stub
				showErrorView(0);
			}
		});
	}
	/**
	 * 请求出错或者无数据时候显示的界面 showErrorView
	 * 
	 * @return void
	 * @throws
	 */
	private void showErrorView(int tag) {
		mListView.setVisibility(View.GONE);
		ll_loading.setVisibility(View.VISIBLE);
		if (tag == 0) {
			tv_no_data.setText(getResources().getText(R.string.list_no_data_lost));
		} else {
			tv_no_data.setText(getResources().getText(R.string.list_no_data_found));
		}
	}
	/**
	 * 显示listview，隐藏提示
	 */
	private void showView() {
		mListView.setVisibility(View.VISIBLE);
		ll_loading.setVisibility(View.GONE);
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
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onEdit(View v) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDelete(View v) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


	

	
}
