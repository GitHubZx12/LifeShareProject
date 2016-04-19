package com.mendale.app.ui.home.menu;

import java.text.ChoiceFormat;
import java.util.ArrayList;
import java.util.List;

import com.mendale.app.R;
import com.mendale.app.adapters.SecondListAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 侧滑菜单--搜索教程
 * 
 * @author zx
 *
 */
public class SearchCourseActivity extends Activity implements TextWatcher,OnClickListener {

	private ListView mListView;
	/**查询结果的adapter*/
	private SecondListAdapter customerAdapter;
	/**x号，清空输入的内容*/
	private ImageView iv_close;
	/**搜索图标，并没什么用*/
	private ImageView iv_search;
	private EditText et_search;
	/** 搜索之后符合要求的数据 */
	private List<String> searchData;
	/** 所有的数据 */
	private List<String> allData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_course);
		initData();
		initView();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		allData=new ArrayList<String>();
		allData.add("吃饭");
		allData.add("吃饭1");
		allData.add("看电视");
		allData.add("看电影");
		allData.add("看综艺");
		searchData=new ArrayList<String>();
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		iv_search = (ImageView) findViewById(R.id.iv_search);
		iv_close = (ImageView) findViewById(R.id.iv_search_close);
		et_search = (EditText) findViewById(R.id.et_search_keyword);
		mListView=(ListView) findViewById(R.id.lv_search_result);
		
		et_search.addTextChangedListener(this);
		iv_close.setOnClickListener(this);
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub
	}

	/**
	 * 用来监听文字的改变，实现搜索功能
	 * 
	 * @param s
	 * @param start
	 * @param before
	 * @param count
	 */
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// 获取用户输入的关键字
		String keyWord = et_search.getText().toString();
		searchData.clear();
		if (keyWord.equals("")) {// 没有输入关键字
			iv_close.setVisibility(View.GONE);
			mListView.setVisibility(View.GONE);
		}
		else {
			iv_close.setVisibility(View.VISIBLE);
			mListView.setVisibility(View.VISIBLE);
		}
		// 遍历
		for (int i = 0; i < allData.size(); i++) {
			if (allData.get(i).contains(keyWord)) {// 包含
				searchData.add(allData.get(i));
			}
		}
		if (searchData.size() != 0) {
			customerAdapter = new SecondListAdapter(this, searchData);
			mListView.setAdapter(customerAdapter);
		}
		else {
			Toast.makeText(SearchCourseActivity.this, "没有相关的内容", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View v) {
		et_search.setText(null);
		iv_close.setVisibility(View.GONE);
		searchData.clear();
		customerAdapter.notifyDataSetChanged();
	}
}
