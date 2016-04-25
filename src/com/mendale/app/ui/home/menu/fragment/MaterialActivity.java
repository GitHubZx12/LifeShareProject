package com.mendale.app.ui.home.menu.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.mendale.app.R;
import com.mendale.app.adapters.UpLoadAddMaterialAdaper;
import com.mendale.app.ui.base.BaseActivity;

/**
 * 上传教程--材料
 * 
 * @author Administrator
 * 
 */
public class MaterialActivity extends BaseActivity {

	private ListView listView;
	/** 添加 */
	private ImageView btn_add;
	private ArrayList<String> text = new ArrayList<String>();
	private UpLoadAddMaterialAdaper mAdapter;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_material);
		initData();
		initHeadView();
		initView();
		addListener();
	}

	/**
	 * 初始化頭部
	 */
	private void initHeadView() {
		
	}

	/**
	 * 点击事件
	 */
	private void addListener() {
		btn_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				text.add("xxx");
				mAdapter.notifyDataSetChanged();
			}
		});
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		text = new ArrayList<String>();
		text.add("第一项");// 默认只加载一个Item
		mAdapter = new UpLoadAddMaterialAdaper(this, text);
	}

	/**
	 * 初始化
	 */
	private void initView() {
		listView = (ListView) findViewById(R.id.listview_material);
		btn_add = (ImageView) findViewById(R.id.btn_add);
		listView.setAdapter(mAdapter);
	}
}
