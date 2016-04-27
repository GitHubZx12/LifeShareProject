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
import com.mendale.app.adapters.NewsTop100LVAdapter;
import com.mendale.app.pojo.NewTop100;
import com.mendale.app.utils.Utils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 最新教程Top100
 * 
 * @author Administrator
 *
 */
public class NewTopActivity extends Activity {

	private List<NewTop100> newTop100List = null;
	private NewsTop100LVAdapter mAdapter;
	private ListView mListView;
	private TextView title;
	private ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_charts);
		initData();
		initView();
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		title = (TextView) findViewById(R.id.tv_charts_title);
		back = (ImageView) findViewById(R.id.iv_charts_back);
		mListView = (ListView) findViewById(R.id.listview_charts);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
//		doGet(DataURL.Z_NEW_JIAOCHENG);
	}

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
		List<NewTop100> newsDatas = null;
		Gson gson = new Gson();
		if (!Utils.isEmpty(string)) {
			try {
				JSONObject jsonObject = new JSONObject(string);
				JSONArray top100 = jsonObject.getJSONArray("list");
				newsDatas = new ArrayList<NewTop100>();
				for (int i = 0; i < top100.length(); i++) {
					NewTop100 item = gson.fromJson(top100.get(i).toString(), new TypeToken<NewTop100>() {
					}.getType());
					newsDatas.add(item);
				}
				setListViewAdapter(newsDatas);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Adapter
	 * @param newsDatas
	 */
	private void setListViewAdapter(List<NewTop100> newsDatas) {
		mAdapter=new NewsTop100LVAdapter(this, newsDatas);
		mListView.setAdapter(mAdapter);
	}
}
