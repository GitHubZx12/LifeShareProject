package com.mendale.app.tasks;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mendale.app.pojo.ChartsPoJo;
import com.mendale.app.utils.Utils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.socialize.utils.Log;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * 最新教程top100
 */
public class ChartsTask2 {

	private static final int TIMEOUT = 30000;// 超时时间
	private Handler mhandler;// 上下文
	private Context context;

	/**
	 * 构造方法
	 * 
	 * @param context
	 */
	public ChartsTask2(Context context, Handler mhandler) {
		this.context = context;
		this.mhandler = mhandler;
	}

	/**
	 * 登录验证 hashmap->json
	 * 
	 * @param params
	 * @param encoding
	 * @return json
	 */
	public void send(int id, String encoding, String action) {
		// 实例化返回对象
		final Message msg = new Message();
		msg.what = 0;
		if (!Utils.isNetworkConnected(context)) {
			msg.obj = "网络已断开";
			mhandler.sendMessage(msg);
			return;
		}
		// 请求url
		String urlPath = action;
		// 返回参数
		StringBuilder sb = new StringBuilder();
		HttpURLConnection conn = null;
		OkHttpClient okHttpClient = new OkHttpClient();
		// 创建一个request
		Request request = new Request.Builder().url(urlPath).build();
		Call call = okHttpClient.newCall(request);
		call.enqueue(new Callback() {

			@Override
			public void onResponse(Response arg0) throws IOException {
				String responseData = arg0.body().toString();
				List<ChartsPoJo> mDatas = parserData(responseData);
				Log.e("tag", mDatas.toString());
				msg.what = 1;
				msg.obj = mDatas;
				mhandler.sendMessage(msg);
			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {
			}
		});
	}

	/**
	 * 使用Gson进行解析 List<person>
	 * 
	 * @param <t>
	 * @param jsonString
	 * @param cls
	 * @return
	 */
	public static <T> List<T> parserData(String jsonString) {
		List<T> list = new ArrayList<T>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
			}.getType());
		} catch (Exception e) {
		}
		return list;
	}
}
