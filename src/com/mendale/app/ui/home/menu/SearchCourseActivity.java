package com.mendale.app.ui.home.menu;

import com.mendale.app.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * 侧滑菜单--搜索教程
 * 根据名称搜索
 * 输入关键字搜索符合的分类名称
 * @author zx
 *
 */
public class SearchCourseActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_course);
	}

}
