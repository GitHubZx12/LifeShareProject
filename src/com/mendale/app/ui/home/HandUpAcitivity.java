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
import com.mendale.app.adapters.HandUpLvAdapter;
import com.mendale.app.adapters.HotCourseLvAdapter;
import com.mendale.app.constants.DataURL;
import com.mendale.app.pojo.HandUpMorePoJo;
import com.mendale.app.pojo.HotCoursePoJo;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.ui.mycenter.MyCenterActivity;
import com.mendale.app.utils.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.socialize.utils.Log;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 首页--手工达人--更多
 * @author zhangxue 
   @date 2016年4月27日
 */
public class HandUpAcitivity extends BaseActivity implements OnItemClickListener{
	
	private ListView mListView;
	private HandUpLvAdapter mAdapter;
	/**数据*/
	private List<HandUpMorePoJo> mDatas = null;
	private DisplayImageOptions options;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hot_course);
		initData();
		initHeaderView();
		initView();
		initImageOptions();
	}
	
	/**
	 * 初始化图片的相关参数
	 */
	private void initImageOptions() {
		// 使用DisplayImageOptions.Builder()创建DisplayImageOptions
		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.image_guidestep_defult) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.image_guidestep_defult) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.image_guidestep_defult) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				.displayer(new RoundedBitmapDisplayer(0)) // 设置成圆角图片
				.build(); // 创建配置过得DisplayImageOption对象
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		doGet(DataURL.HAND_UP);
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
				JSONArray handUpList = jsonObject.getJSONArray("list");
				mDatas = new ArrayList<HandUpMorePoJo>();
				for (int i = 0; i < handUpList.length(); i++) {
					HandUpMorePoJo item = gson.fromJson(handUpList.get(i).toString(), new TypeToken<HandUpMorePoJo>() {
					}.getType());
					item.save(this);
					mDatas.add(item);
				}
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						setListViewAdapter(mDatas);
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
	private void setListViewAdapter(List<HandUpMorePoJo> newsDatas) {
		mAdapter=new HandUpLvAdapter(this, mDatas,options);
		mListView.setAdapter(mAdapter);
	}
	/**
	 * 初始化标题
	 */
	private void initHeaderView() {
		setNavigationTitle("手工达人");
		setNavigationLeftBtnText("");
	}
	@Override
	public void leftButtonOnClick() {
		super.leftButtonOnClick();
		this.finish();
	}

	/**
	 *初始化view
	 */
	private void initView() {
		mListView = (ListView) findViewById(R.id.listview_hot_course);
		mListView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent=new Intent(this,MyCenterActivity.class);
		intent.putExtra("id", mDatas.get(position).getUser_id());
		intent.putExtra("flag",2);
		startActivity(intent);
	}
}
