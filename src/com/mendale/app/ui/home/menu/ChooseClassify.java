package com.mendale.app.ui.home.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mendale.app.R;
import com.mendale.app.adapters.SecondListAdapter;
import com.mendale.app.pojo.MainMenu;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.ui.home.UpLoadRecordActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 上传教程--分类--选择分类修改
 * @author zx
 *
 */
public class ChooseClassify extends BaseActivity implements OnItemClickListener{
	
	private ListView mListView;
	/** 集合 */
	private List<MainMenu> mlist;
	private String[] name={"心情","问答","生活","美食","橡皮泥","运动","表演艺术","旧物改造","绘画","心里课堂"};
	private int[] pic={R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,
			R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,
			R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_classify);
		initHeadView();
		initView();
		initData();
	}

	/**
	 * 初始化數據
	 */
	private void initData() {
		mlist=new ArrayList<MainMenu>();
		for (int i = 0; i <name.length; i++) {
			mlist.add(new MainMenu(name[i], pic[i]));
		}
		SecondListAdapter mAdapter = new SecondListAdapter(this, mlist);
		mListView.setAdapter(mAdapter);
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		mListView=(ListView) findViewById(R.id.lv_choose_classify);
		mListView.setOnItemClickListener(this);
		
	}

	/**
	 * 初始化头部view
	 */
	private void initHeadView() {
		setNavigationTitle("分类选择");
		setNavigationLeftBtnText("取消");
		setNavigationRightBtnText("完成");
	}
	/**
	 * 取消
	 */
	@Override
	public void leftButtonOnClick() {
		super.leftButtonOnClick();
		this.finish();
	}
	/**
	 * 完成
	 */
	@Override
	public void rightButtonOnClick() {
		super.rightButtonOnClick();
	}

	/**
	 * 
	 * @param parent
	 * @param view
	 * @param position
	 * @param id
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		startActivity(UpLoadRecordActivity.class);
	}

}
