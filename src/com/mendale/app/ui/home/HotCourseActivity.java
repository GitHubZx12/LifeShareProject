package com.mendale.app.ui.home;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mendale.app.R;
import com.mendale.app.adapters.HotCourseLvAdapter;
import com.mendale.app.constants.DataURL;
import com.mendale.app.pojo.HotCoursePoJo;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.utils.Utils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.socialize.utils.Log;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 首页--热门教程--更多
 * 
 * @author Administrator
 *
 */
public class HotCourseActivity extends BaseActivity {

	private ListView mListView;
	private HotCourseLvAdapter mAdapter;
	/**数据*/
	private List<HotCoursePoJo> newsDatas = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hot_course);
		initData();
		initHeaderView();
		initView();
	}

	/**
	 * 初始化头部
	 */
	private void initHeaderView() {
		setNavigationTitle("热门教程");
		setNavigationLeftBtnText("");
	}
	@Override
	public void leftButtonOnClick() {
		super.leftButtonOnClick();
		this.finish();
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		mListView = (ListView) findViewById(R.id.listview_hot_course);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		doGet(DataURL.Z_NEW_JIAOCHENG);
	}

	/**
	 * 获取数据
	 * @param url
	 */
	private void doGet(final String url) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				OkHttpClient okHttpClient = new OkHttpClient();
				// 创建一个request
				Request request = new Request.Builder().url(url).build();
				Call call = okHttpClient.newCall(request);
				call.enqueue(new Callback() {

					@Override
					public void onResponse(Response arg0) throws IOException {
						parserData(arg0.body().string());
					}

					@Override
					public void onFailure(Request arg0, IOException arg1) {
					}
				});
			}
		}).start();
		
	}
	/**
	 * 解析json数据
	 * 
	 * @param string
	 */
	private void parserData(String string) {
		Gson gson = new Gson();
		if (!Utils.isEmpty(string)) {
			try {
				JSONObject jsonObject = new JSONObject(string);
				JSONArray top100 = jsonObject.getJSONArray("list");
				newsDatas = new ArrayList<HotCoursePoJo>();
				for (int i = 0; i < top100.length(); i++) {
					HotCoursePoJo item = gson.fromJson(top100.get(i).toString(), new TypeToken<HotCoursePoJo>() {
					}.getType());
					newsDatas.add(item);
				}
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						Log.e("tag6666",newsDatas.toString());
						setListViewAdapter(newsDatas);
					}
				});
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Adapter
	 * @param newsDatas
	 */
	private void setListViewAdapter(List<HotCoursePoJo> newsDatas) {
		mAdapter=new HotCourseLvAdapter(this, newsDatas);
		mListView.setAdapter(mAdapter);
	}
}
