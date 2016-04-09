package com.mendale.app.ui.home.fragment;

import com.mendale.app.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * 排行榜
 * 
 * @author Administrator
 * 
 */
public class ChartsFragment extends Fragment implements OnClickListener {

	private RelativeLayout rl_charts_top;
	private RelativeLayout rl_charts_add;
	private RelativeLayout rl_charts_hot;
	private RelativeLayout rl_charts_all_hot;
	private RelativeLayout rl_charts_grow;
	private RelativeLayout rl_charts_all_grow;
	private RelativeLayout rl_charts_comment;
	private RelativeLayout rl_charts_all_comment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_charts, container, false);
		initView(view);
		addListener();
		return view;
	}

	/**
	 * 添加点击事件
	 */
	private void addListener() {
		rl_charts_top.setOnClickListener(this);
		rl_charts_add.setOnClickListener(this);
		rl_charts_hot.setOnClickListener(this);
		rl_charts_all_hot.setOnClickListener(this);
		rl_charts_grow.setOnClickListener(this);
		rl_charts_all_grow.setOnClickListener(this);
		rl_charts_comment.setOnClickListener(this);
		rl_charts_all_comment.setOnClickListener(this);
	}

	/**
	 * 初始化界面
	 */
	private void initView(View view) {
		rl_charts_top = (RelativeLayout) view.findViewById(R.id.rl_charts_top);
		rl_charts_add = (RelativeLayout) view.findViewById(R.id.rl_charts_add);
		rl_charts_hot = (RelativeLayout) view.findViewById(R.id.rl_charts_hot);
		rl_charts_all_hot = (RelativeLayout) view
				.findViewById(R.id.rl_charts_all_hot);
		rl_charts_grow = (RelativeLayout) view
				.findViewById(R.id.rl_charts_grow);
		rl_charts_all_grow = (RelativeLayout) view
				.findViewById(R.id.rl_charts_all_grow);
		rl_charts_comment = (RelativeLayout) view
				.findViewById(R.id.rl_charts_comment);
		rl_charts_all_comment = (RelativeLayout) view
				.findViewById(R.id.rl_charts_all_comment);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_charts_top://最新教程Top100
			
			break;
		case R.id.rl_charts_add://本周新增人气王

			break;
		case R.id.rl_charts_hot://一周最热教程top100

			break;
		case R.id.rl_charts_all_hot://最热教程总榜

			break;
		case R.id.rl_charts_grow://一周成长总之

			break;
		case R.id.rl_charts_all_grow://成长值总榜

			break;
		case R.id.rl_charts_comment://本周点赞狂人

			break;
		case R.id.rl_charts_all_comment://点赞狂人总办

			break;
		}

	}
}
