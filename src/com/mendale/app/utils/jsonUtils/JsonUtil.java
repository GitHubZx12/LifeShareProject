package com.mendale.app.utils.jsonUtils;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * json和java对象相互转换通用类
 * 
 * @author wenjian
 * 
 */
public class JsonUtil {

	/**
	 * json转换为单个对象 可包含复杂list子集
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Object getODT(String jsonString) {
		Object obj = new Object();
		Gson gson = new Gson();
		try {
			// 转换成列表类型：
			obj = gson.fromJson(jsonString, Object.class);
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			obj = null;
		}
		return obj;
	}

	/**
	 * json转换为list集合
	 * 
	 * @param jsonString
	 * @return
	 */
	public static List<Object> getODTList(String jsonString) {
		List<Object> obj = new ArrayList<Object>();
		Gson gson = new Gson();
		try {
			// 转换成列表类型：
			obj = gson.fromJson(jsonString, new TypeToken<List<Object>>() {
			}.getType());
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			obj = null;
		}
		return obj;
	}
}
