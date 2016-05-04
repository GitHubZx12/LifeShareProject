package com.mendale.app.ui.home.menu;

import java.util.List;

import com.mendale.app.R;
import com.mendale.app.adapters.CommentLvAdapter;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.vo.CommentList;
import com.mendale.app.vo.CourseDetailsBean;
import com.umeng.socialize.utils.Log;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;

/**
 * 评论页面
 * @author zx
 *
 */
public class CommentActivity extends BaseActivity {

	private LinearLayout ll_tips;
	private TextView tv_tips;
	private ImageView iv_loading;
	private ListView mListView;
	private CommentLvAdapter mAdapter;
	/**评论列表*/
	private List<CommentList> commentList;
	

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
		CourseDetailsBean item=(CourseDetailsBean) getIntent().getSerializableExtra("detailsData");
		for(int i=0;i<item.getComment_count();i++){
			CommentList commentItem=item.getCommentList().get(i);
			commentList.add(commentItem);
		}
		mAdapter=new CommentLvAdapter(this, commentList);
		mListView.setAdapter(mAdapter);
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		mListView=(ListView) findViewById(R.id.listview_comments);
		ll_tips=(LinearLayout) findViewById(R.id.ll_comments_loading);
		iv_loading=(ImageView) findViewById(R.id.iv_comments_loading);
		tv_tips=(TextView) findViewById(R.id.tv_comments_tips);
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
}
