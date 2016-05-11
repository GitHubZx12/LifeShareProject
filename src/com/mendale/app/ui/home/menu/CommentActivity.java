package com.mendale.app.ui.home.menu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mendale.app.R;
import com.mendale.app.adapters.CommentLvAdapter;
import com.mendale.app.constants.DataURL;
import com.mendale.app.tasks.CommentListTask;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.utils.pullToRefreshUtils.PullToRefreshConfig;
import com.mendale.app.utils.pullToRefreshUtils.view.XListView;
import com.mendale.app.utils.pullToRefreshUtils.view.XListView.IXListViewListener;
import com.mendale.app.vo.CommentList;
import com.umeng.socialize.utils.Log;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 评论页面
 * @author zx
 *
 */
public class CommentActivity extends BaseActivity implements IXListViewListener{

	private LinearLayout ll_tips;
	private TextView tv_tips;
	private ImageView iv_loading;
	private XListView mListView;
	private CommentLvAdapter mAdapter;
	/**评论列表*/
	private List<CommentList> commentList;
	private String  hand_id;
	private int page=0;
	private int allDatasize;
	

	@SuppressLint("HandlerLeak")
	private Handler mhandler=new Handler(){
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			if(msg.obj!=null){
				commentList=(List<CommentList>) msg.obj;
				mAdapter=new CommentLvAdapter(CommentActivity.this, commentList);
				mListView.setAdapter(mAdapter);
			}
		};
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_charts);
		initHeaderView();
		initView();
		initData();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		commentList=new ArrayList<CommentList>();
		 hand_id=getIntent().getStringExtra("hand_id");
		 allDatasize=getIntent().getIntExtra("comment_count",0);
		 ++page;
		 final String detail_url=DataURL.COURSE_DETAILS_COMMENT_LIST+hand_id+"&page="+page;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				new CommentListTask(CommentActivity.this, mhandler,commentList).send(1,
						 "utf-8", detail_url);
			}
		}).start();
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		mListView=(XListView) findViewById(R.id.listview_comments);
		ll_tips=(LinearLayout) findViewById(R.id.ll_comments_loading);
		iv_loading=(ImageView) findViewById(R.id.iv_comments_loading);
		tv_tips=(TextView) findViewById(R.id.tv_comments_tips);
		mListView.setPullRefreshEnable(PullToRefreshConfig.pullRefreshEnable);// 设置是否可以下拉刷新
		mListView.setPullLoadEnable(PullToRefreshConfig.pullLoadEnable);// 设置是否可以上拉加载
		mListView.setXListViewListener(this);
	}

	/**
	 * 初始化标题
	 */
	private void initHeaderView() {
		setNavigationTitle("评论列表");
		setNavigationLeftBtnText("");
	}
	@Override
	public void leftButtonOnClick() {
		super.leftButtonOnClick();
		this.finish();
	}

	/**
	 * 下拉刷新
	 */
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 上啦加载
	 */
	@Override
	public void onLoadMore() {
		int count=mAdapter.getCount();
		if(count>allDatasize){
			return;
		}
			++page;
			//http://www.shougongke.com/index.php?m=Mobq_course&a=comment_list&id=55063&page=2
			final String detail_url=DataURL.COURSE_DETAILS_COMMENT_LIST+hand_id+"&page="+page;
			Log.e("tag",detail_url);
			if(count+1<allDatasize){
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						new CommentListTask(CommentActivity.this, mhandler,commentList).send(1,
								 "utf-8", detail_url);
					}
				}).start();
		}
		
	}
	
	/**
	 * 等待状态，顶部显示上次刷新时间
	 * 
	 */
	@SuppressLint("SimpleDateFormat")
	private void onLoad() {
		mListView.stopRefresh();// 停止刷新
		mListView.stopLoadMore();// 停止"加载更多"
		// 获得系统当前时间
		SimpleDateFormat formatter = new SimpleDateFormat(PullToRefreshConfig.strDateFormat);
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);// 格式化
		mListView.setRefreshTime(str);// 给listview设置刷新时间
	}
}
