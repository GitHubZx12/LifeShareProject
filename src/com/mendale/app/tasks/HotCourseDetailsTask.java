package com.mendale.app.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mendale.app.utils.Utils;
import com.mendale.app.vo.CourseDetailsBean;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 热门教程列表
 */
public class HotCourseDetailsTask {

	private static final int TIMEOUT = 30000;// 超时时间
	private Handler mhandler;// 上下文
	private Context context;

	/**
	 * 构造方法
	 * 
	 * @param context
	 */
	public HotCourseDetailsTask(Context context, Handler mhandler) {
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
		Message msg = new Message();
		msg.what = 0;

		if (!Utils.isNetworkConnected(context)) {
			msg.obj = "网络已断开";
			mhandler.sendMessage(msg);
			return;
		}
		// 请求url
		// String urlPath = URLS.getRequestServerUrl() + action;
		String urlPath = action;
		// 返回参数
		StringBuilder sb = new StringBuilder();
		HttpURLConnection conn = null;
		try {
			// 将请求体转换成byte
			byte[] entitydata = ("qzsgid=" + id + "&devicetoken=" + Utils
					.getDeviceUnique(context)).getBytes();
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
				// responseData是请求服务器返回的结果（json格式的）
				if (!Utils.isEmpty(responseData)) {
					JSONObject json = new JSONObject(responseData);
					if (json.get("msg") != null) {
						CourseDetailsBean item = gson.fromJson(
								json.toString(),
								new TypeToken<CourseDetailsBean>() {
								}.getType());
						msg.what = 1;
						msg.obj = item;
						mhandler.sendMessage(msg);
//						for(int j=0;j<item.getTools().size();j++){
//							Tool c=item.getTools().get(j);
//							c.save(context);
//						}
//						for(int j=0;j<item.getMaterial().size();j++){
//							Material c=item.getMaterial().get(j);
//							c.save(context);
//						}
//						for(int j=0;j<item.getStep().size();j++){
//							Step c=item.getStep().get(j);
//							c.save(context);
//						}
//						for(int j=0;j<item.getCommentList().size();j++){
//							CommentList c=item.getCommentList().get(j);
//							c.save(context);
//						}
//						
//						item.save(context);
//						return;
					} else {
						msg.what = 1;
						mhandler.sendMessage(msg);
					}

					/*final CourseDetailsBean item = gson.fromJson(
							json.toString(),CourseDetailsBean.class);
					item.save(context, new SaveListener() {
						
						
						@Override
						public void onSuccess() {
							item.save(context);
						}
						
						@Override
						public void onFailure(int arg0, String arg1) {
							Log.e("tag", arg0+"--"+arg1);
						}
					});
					msg.what = 1;
					mhandler.sendMessage(msg);*/
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
