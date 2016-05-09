package com.mendale.app.ui.home.fragment;

import static com.mendale.app.R.id.tv_describe;
import static com.mendale.app.R.id.tv_photo;
import static com.mendale.app.R.id.tv_time;
import static com.mendale.app.R.id.tv_title;

import java.util.List;

import com.mendale.app.R;
import com.mendale.app.adapters.BaseAdapterHelper;
import com.mendale.app.adapters.QuickAdapter;
import com.mendale.app.constants.Constants;
import com.mendale.app.pojo.HelpCoursePoJo;
import com.mendale.app.ui.home.menu.HelpMakeCourseActivity;
import com.mendale.app.utils.popwindow.EditPopupWindow;
import com.mendale.app.utils.popwindow.IPopupItemClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Context;
import android.content.Intent;
import android.database.CursorJoiner.Result;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;

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
		queryQA();
	}
	

	/**
	 * 查询全部失物信息 queryLosts
	 * 
	 * @return void
	 * @throws
	 */
	private void queryQA() {
		showView();
		BmobQuery<HelpCoursePoJo> query = new BmobQuery<HelpCoursePoJo>();
		query.order("-createdAt");// 按照时间降序
		query.findObjects(getActivity(), new FindListener<HelpCoursePoJo>() {

			@Override
			public void onSuccess(List<HelpCoursePoJo> items) {
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
		position = position;
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		mPopupWindow.showAtLocation(view, Gravity.RIGHT | Gravity.TOP,
				location[0], getStateBar() + location[1]);
		return false;
	}
	/** 获取当前状态栏的高度
	  * getStateBar
	  * @Title: getStateBar
	  * @throws
	  */
	public  int getStateBar(){
		Rect frame = new Rect();
		getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		return statusBarHeight;
	}
	
	/**
	 * dp转px
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context,float dipValue){
		float scale=context.getResources().getDisplayMetrics().density;		
		return (int) (scale*dipValue+0.5f);		
	}
	/**
	 * 编辑
	 */
	@Override
	public void onEdit(View v) {
		Intent intent = new Intent(getActivity(), HelpMakeCourseActivity.class);
		String title = "";
		String describe = "";
		String phone = "";
		//
		title = mAdapter.getItem(position).getTitle();
		describe = mAdapter.getItem(position).getContent();
		phone = mAdapter.getItem(position).getPhone();
		//
		intent.putExtra("describe", describe);
		intent.putExtra("phone", phone);
		intent.putExtra("title", title);
		startActivityForResult(intent, 1);
		
	}
	@Override
	public void onDelete(View v) {
		delete();
	}
	/**
	 * 删除选中的listview的item
	 */
	private void delete() {
		HelpCoursePoJo item = new HelpCoursePoJo();
		item.setObjectId(mAdapter.getItem(position).getObjectId());
		item.delete(getActivity(), new DeleteListener() {

			@Override
			public void onSuccess() {
				mAdapter.remove(position);
			}

			@Override
			public void onFailure(int code, String arg0) {

			}
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
//		if (resultCode !=RESULT_OK) {
//			return;
//		}
		switch (requestCode) {
		case 1:// 添加成功之后的回调
			queryQA();
			break;
		}
	}


	

	
}
