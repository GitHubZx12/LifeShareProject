package com.mendale.app.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mendale.app.constants.URLS;
import com.mendale.app.utils.Utils;
import com.mendale.app.vo.DarenItemBean;
import com.mendale.app.vo.HomeAllList;
import com.mendale.app.vo.HotCourseItemBean;
import com.mendale.app.vo.TypeItemBean;

/**
 * 获取贵宾列表
 */
public class HomeTask {

	private static final int TIMEOUT = 30000;// 超时时间
	private Handler mhandler;// 上下文
	private Context context;

	/**
	 * 构造方法
	 * 
	 * @param context
	 */
	public HomeTask(Context context, Handler mhandler) {
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
	public void send(int id, String encoding,
			String action) {

		// 实例化返回对象
		Message msg = new Message();
		msg.what = 0;

		if (!Utils.isNetworkConnected(context)) {
			msg.obj = "网络已断开";
			mhandler.sendMessage(msg);
			return;
		}
		// 请求url
//		String urlPath = URLS.getRequestServerUrl() + action;
		String urlPath=action;
		// 返回参数
		StringBuilder sb = new StringBuilder();
		HttpURLConnection conn = null;
		try {
			// 将请求体转换成byte
			byte[] entitydata = ("qzsgid="+id+"&devicetoken="+Utils.getDeviceUnique(context)).getBytes();
			URL url = new URL(urlPath);
			// httpUrlConnection打开连接需要配置代理
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(TIMEOUT);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);// 不缓存数据
			// 设置传输格式
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("Charset", encoding);
			conn.setRequestProperty("Content-Length",
					String.valueOf(entitydata.length));
			OutputStream outStream = conn.getOutputStream();
			// 写入输出流
			outStream.write(entitydata);
			// 刷新输出流
			outStream.flush();
			outStream.close();

			// 实例化Gson
			Gson gson = new Gson();
			// 获取返回状态 200-成功 其它-失败
			conn.getResponseMessage();
			conn.getResponseCode();
			if (conn.getResponseCode() == 200) {
				// 读取输入流
				BufferedReader in = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), encoding));
				// 转成json文本
				String retData = null;
				String responseData = "";
				while ((retData = in.readLine()) != null) {
					responseData += retData;
				}
				in.close();
				// 测试流量
				System.out.println("上传流量:" + entitydata.length / 1024 + "K");
				System.out.println("下载流量:" + responseData.getBytes().length
						/ 1024 + "K");
				Log.d("http", responseData.toString());
				//responseData是请求服务器返回的结果（json格式的）
				if (!Utils.isEmpty(responseData)) {
					HomeAllList allList=new HomeAllList();
					JSONObject json = new JSONObject(responseData);
							if (json.get("hot") != null) {
								//热门教程
								JSONArray course = json.getJSONArray("hot");
								List<HotCourseItemBean> courseList = new ArrayList<HotCourseItemBean>();
								for (int i = 0; i < course.length(); i++) {
									HotCourseItemBean item = gson.fromJson(course.get(i).toString(),
											new TypeToken<HotCourseItemBean>(){}.getType());
									courseList.add(item);
								}
								allList.setCourseData(courseList);
								//手工达人
								JSONArray daren=json.getJSONArray("daren");
								List<DarenItemBean> darenList=new ArrayList<DarenItemBean>();
								for (int i = 0; i < daren.length(); i++) {
									DarenItemBean item = gson.fromJson(daren.get(i).toString(),
											new TypeToken<DarenItemBean>(){}.getType());
									darenList.add(item);
								}
								allList.setDarenData(darenList);
								//热门类别
								JSONArray type=json.getJSONArray("gcate");
								List<TypeItemBean> typeList=new ArrayList<TypeItemBean>();
								for (int i = 0; i < type.length(); i++) {
									TypeItemBean item = gson.fromJson(type.get(i).toString(),
											new TypeToken<TypeItemBean>(){}.getType());
									typeList.add(item);
								}
								allList.setTypeData(typeList);
								msg.what = 1;
								msg.obj = allList;
								mhandler.sendMessage(msg);
							} else {
								msg.what = 1;
								mhandler.sendMessage(msg);
							}

						
				} else {
					msg.obj = "远程服务器异常";
					mhandler.sendMessage(msg);
				}
			} else {
				msg.obj = conn.getResponseCode() + "远程服务器异常";
				mhandler.sendMessage(msg);
				System.out.println("服务器异常信息：" + conn.getResponseMessage());
			}

		} catch (SocketTimeoutException e) {
			msg.obj = "连接已超时";
			mhandler.sendMessage(msg);
		} catch (JsonSyntaxException e) {
			msg.obj = "数据异常";
			mhandler.sendMessage(msg);
		} catch (IOException e) {
			msg.obj = "连接不到服务器，请检查网络";
			System.out.println("连接不到服务器，请检查网络");
			mhandler.sendMessage(msg);
		} catch (JSONException e) {
 			msg.obj = "json解析异常";
			mhandler.sendMessage(msg);
			System.out.println("json解析异常");
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

}
