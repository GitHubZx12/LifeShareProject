package com.mendale.app.ui.home.menu.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.mendale.app.R;
import com.mendale.app.adapters.UpLoadAddMaterialAdaper;
import com.mendale.app.pojo.MaterialPoJo;
import com.mendale.app.ui.base.BaseActivity;
import com.umeng.socialize.utils.Log;

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
	private List<MaterialPoJo> text = new ArrayList<MaterialPoJo>();
	private UpLoadAddMaterialAdaper mAdapter;
	private int count;

	
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
		setNavigationTitle("材料");
		setNavigationRightBtnImage(R.drawable.crafter_cguide_lastarrow_yes_selected);
		setNavigationLeftBtnText("");
		
	}
	/**
	 * 返回
	 */
	@Override
	public void leftButtonOnClick() {
		super.leftButtonOnClick();
	}
	/**
	 * 下一步
	 */
	@Override
	public void rightImageButtonOnClick() {
		super.rightImageButtonOnClick();
		//TODO 保存到数据库中
	}

	/**
	 * 点击事件
	 */
	private void addListener() {
		btn_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mAdapter.getStr(count++);
			}
		});
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		text = new ArrayList<MaterialPoJo>();
		MaterialPoJo mp=new MaterialPoJo();
		mp.setDesc("haha");
		mp.setName("haha");
		mp.setFlag(false);
		text.add(mp);
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
