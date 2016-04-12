package com.mendale.app.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mendale.app.pojo.CourseChildPojo;
import com.mendale.app.pojo.CoursePoJo;
import com.mendale.app.utils.Utils;
import com.mendale.app.vo.DarenItemBean;
import com.mendale.app.vo.HomeAllList;
import com.mendale.app.vo.HotCourseItemBean;
import com.mendale.app.vo.TypeItemBean;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * 最新教程top100
 */
public class NewsTop100Task {

	private static final int TIMEOUT = 30000;// 超时时间
	private Handler mhandler;// 上下文
	private Context context;

	/**
	 * 构造方法
	 * 
	 * @param context
	 */
	public NewsTop100Task(Context context, Handler mhandler) {
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
		OkHttpClient okHttpClient=new OkHttpClient();
		//创建一个request
		Request request=new Request.Builder().url(urlPath).build();
		Call call=okHttpClient.newCall(request);
		call.enqueue(new Callback() {
			
			@Override
			public void onResponse(Response arg0) throws IOException {
				String responseData=arg0.body().toString();
			}
			
			@Override
			public void onFailure(Request arg0, IOException arg1) {
				// TODO Auto-generated method stub
			}
		});
	}

}
