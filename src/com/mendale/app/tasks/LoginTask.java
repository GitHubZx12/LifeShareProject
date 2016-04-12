package com.mendale.app.tasks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mendale.app.constants.URLS;
import com.mendale.app.vo.LoginReturn;
import com.umeng.socialize.utils.Log;

/**
 * 账号密码登录的task
 * 
 * @author wenjian
 * 
 */
public class LoginTask {

	private static final int TIMEOUT = 30000;// 超时时间
	@SuppressWarnings("unused")
	private Context mContext;// 上下文

	/**
	 * 构造方法
	 * 
	 * @param context
	 */
	public LoginTask(Context context) {
		this.mContext = context;
	}

	/**
	 * 登录验证 hashmap->json
	 * 
	 * @param params
	 * @param encoding
	 * @return json
	 */
	public String sendLogin(Map<String, String> params, String encoding) {
		// 请求url
		String urlPath = URLS.getRequestServerUrl() + URLS.LOGIN_ACTION;
		Log.e("tag666",urlPath);
		// 返回参数
		String tag = "";
		StringBuilder sb = new StringBuilder();
		HttpURLConnection conn = null;
		try {
			// 转换成post请求格式
			if (params != null && !params.isEmpty()) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					// 防止出现空的值
					if (entry.getValue() == null || entry.getValue().equals("")) {
						sb.append(entry.getKey()).append('=').append(URLEncoder.encode("", encoding)).append('&');
					}
					else {
						sb.append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(), encoding))
								.append('&');
					}
				}
				// 去掉最后一个符号
				sb.deleteCharAt(sb.length() - 1);
			}
			// 将请求体转换成byte
			byte[] entitydata = sb.toString().getBytes();
			URL url = new URL(urlPath);
			// httpUrlConnection打开连接需要配置代理
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(TIMEOUT);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);// 不缓存数据
			// 设置传输格式
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Charset", encoding);
			conn.setRequestProperty("Content-Length", String.valueOf(entitydata.length));
			OutputStream outStream = conn.getOutputStream();
			// 写入输出流
			outStream.write(entitydata);
			// 刷新输出流
			outStream.flush();
			outStream.close();
			// 实例化返回对象
			LoginReturn loginReturn = null;
			// 实例化Gson
			Gson gson = new Gson();
			// 获取返回状态 200-成功 其它-失败
			if (conn.getResponseCode() == 200) {
				// 读取输入流
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
				// 转成json文本
				String retData = null;
				String responseData = "";
				while ((retData = in.readLine()) != null) {
					responseData += retData;
				}
				in.close();
				// 测试流量
				System.out.println("上传流量:" + entitydata.length / 1024 + "K");
				System.out.println("下载流量:" + responseData.getBytes().length / 1024 + "K");
				// json转java对象
				loginReturn = new LoginReturn();
				try {
					loginReturn = gson.fromJson(responseData, LoginReturn.class);
					if (loginReturn.getReturnnum().equals("00")) {
						tag = "S";
					}
					else {
						tag = loginReturn.getReturnstr();
					}
				} catch (JsonSyntaxException e) {
					// json格式异常
					tag = "数据格式错误!";
				}
			}
			else {
				tag = conn.getResponseCode() + " 服务端异常!";
			}
		} catch (Exception e) {
			tag = "请求出错!" + e.getLocalizedMessage();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return tag;
	}
}
