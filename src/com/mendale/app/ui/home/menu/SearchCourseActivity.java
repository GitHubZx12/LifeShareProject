package com.mendale.app.ui.home.menu;

import java.util.ArrayList;
import java.util.List;

import com.mendale.app.R;
import com.mendale.app.adapters.SecondListAdapter;
import com.mendale.app.constants.Datas;
import com.mendale.app.pojo.CourseChildPojo;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.ui.course.CourseInfoActivity;
import com.umeng.socialize.utils.Log;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * 侧滑菜单--搜索教程
 * 
 * @author zx
 *
 */
public class SearchCourseActivity extends BaseActivity implements TextWatcher,OnClickListener,OnItemClickListener {

	private ListView mListView;
	/**查询结果的adapter*/
	private SecondListAdapter customerAdapter;
	/**x号，清空输入的内容*/
	private ImageView iv_close;
	/**搜索图标，并没什么用*/
	private ImageView iv_search;
	private EditText et_search;
	/** 搜索之后符合要求的数据 */
	private List<CourseChildPojo> searchData;
	/** 所有的数据 */
	private List<CourseChildPojo> allData=null;
	private String[] name={"心情","问答","生活","美食","橡皮泥","运动","表演艺术","旧物改造","绘画","心里课堂"};
	private int[] pic={R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,
			R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,
			R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_course);
		 initHeaderView();
		initData();
		initView();
	}
	/**
	 * 初始化标题
	 */
	private void initHeaderView() {
		setNavigationTitle("搜索");
		setNavigationLeftBtnText("");
	}
	@Override
	public void leftButtonOnClick() {
		super.leftButtonOnClick();
		this.finish();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		allData=new ArrayList<CourseChildPojo>();
		BmobQuery<CourseChildPojo>bQuery=new BmobQuery<CourseChildPojo>();
		bQuery.findObjects(this, new FindListener<CourseChildPojo>() {
			
			@Override
			public void onSuccess(List<CourseChildPojo> arg0) {
				allData=arg0;
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				Log.e("tag",arg1);
			}
		});
		searchData=new ArrayList<CourseChildPojo>();
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
		mListView.setOnItemClickListener(this);
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
			if (allData.get(i).getName().contains(keyWord)) {// 包含
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
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(this, CourseInfoActivity.class);
		Bundle bundle=new Bundle();
		bundle.putSerializable("courseItem",allData.get(position) );
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
