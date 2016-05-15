package com.mendale.app.ui.home;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mendale.app.R;
import com.mendale.app.adapters.HandUpLvAdapter;
import com.mendale.app.constants.DataURL;
import com.mendale.app.pojo.HandUpMorePoJo;
import com.mendale.app.pojo.MyUser;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.ui.mycenter.MyCenterActivity;
import com.mendale.app.utils.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.umeng.socialize.utils.Log;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

/**
 * 首页--手工达人--更多
 * 
 * @author zhangxue
 * @date 2016年4月27日
 */
public class HandUpAcitivity extends BaseActivity implements OnItemClickListener {

	protected static final String TAG = "HandUpAcitivity";
	private ListView mListView;
	private HandUpLvAdapter mAdapter;
	/** 数据 */
	private List<MyUser> mDatas = null;
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
	 * 表与表之间的关联
	 * 一个记录可以被多个用户收藏
	 * 一个用户也可以收藏多个记录
	 * 记录   和    用户之间是多对多的
	 *添加多对多关联
	 */
	

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
	 * 
	 * @param url
	 */
	private void doGet(final String url) {
		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		// OkHttpClient okHttpClient = new OkHttpClient();
		// // 创建一个request
		// Request request = new Request.Builder().url(url).build();
		// Call call = okHttpClient.newCall(request);
		// call.enqueue(new Callback() {
		//
		// @Override
		// public void onResponse(Response arg0) throws IOException {
		// parserData(arg0.body().string());
		// }
		//
		// @Override
		// public void onFailure(Request arg0, IOException arg1) {
		// }
		// });
		// }
		// }).start();
//		BmobQuery<HandUpMorePoJo> bQuery = new BmobQuery<HandUpMorePoJo>();
//		bQuery.findObjects(this, new FindListener<HandUpMorePoJo>() {
//
//			@Override
//			public void onSuccess(List<HandUpMorePoJo> arg0) {
//				mDatas = arg0;
//				runOnUiThread(new Runnable() {
//
//					@Override
//					public void run() {
//						setListViewAdapter(mDatas);
//					}
//				});
//			}
//
//			@Override
//			public void onError(int arg0, String arg1) {
//				Log.e("tag", "errorCode" + arg0 + "errorString" + arg1);
//			}
//		});
		BmobQuery<MyUser>query=new BmobQuery<MyUser>();
		query.findObjects(this, new FindListener<MyUser>() {

			@Override
			public void onError(int arg0, String arg1) {
				Log.e(TAG,arg0+arg1);
				
			}

			@Override
			public void onSuccess(List<MyUser> arg0) {
				mDatas=arg0;
				setListViewAdapter(arg0);
			}
		});
	}

	/**
	 * 解析json数据
	 * 
	 * @param string
	 */
	private void parserData(String string) {
//		Gson gson = new Gson();
//		if (!Utils.isEmpty(string)) {
//			try {
//				JSONObject jsonObject = new JSONObject(string);
//				JSONArray handUpList = jsonObject.getJSONArray("list");
//				mDatas = new ArrayList<HandUpMorePoJo>();
//				for (int i = 0; i < handUpList.length(); i++) {
//					HandUpMorePoJo item = gson.fromJson(handUpList.get(i).toString(), new TypeToken<HandUpMorePoJo>() {
//					}.getType());
//					item.save(this);
//					mDatas.add(item);
//				}
//				runOnUiThread(new Runnable() {
//
//					@Override
//					public void run() {
//						setListViewAdapter(mDatas);
//					}
//				});
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//		}
	}

	/**
	 * Adapter
	 * 
	 * @param newsDatas
	 */
	private void setListViewAdapter(List<MyUser> newsDatas) {
		mAdapter = new HandUpLvAdapter(this, mDatas, options);
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
	 * 初始化view
	 */
	private void initView() {
		mListView = (ListView) findViewById(R.id.listview_hot_course);
		mListView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(this, MyCenterActivity.class);
		Bundle bundle=new Bundle();
		bundle.putSerializable("userInfo", mDatas.get(position));
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
