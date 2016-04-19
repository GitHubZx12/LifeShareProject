package com.mendale.app.ui.home.menu;

import java.util.List;

import com.mendale.app.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * 侧滑菜单--搜索教程
 * @author zx
 *
 */
public class SearchCourseActivity extends Activity implements TextWatcher{
	
	/**listview展示结果*/
	private ListView mListView;
	private EditText search;
	private ImageView iv_search;
	private ImageView iv_search_close;
	/**保存分类名称的集合*/
	private List<String>menuData;
	/**搜索集合*/
	private List<String>searchData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_course);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 搜索功能，文字改变监听
	 */
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		//获得用户输入的关键字
		String keyWord=search.getText().toString();
		if(keyWord.equals("")){//为空
			iv_search_close.setVisibility(View.INVISIBLE);
			menuData.clear();
		}else{
			iv_search_close.setVisibility(View.VISIBLE);
		}
		
	}

	

}
