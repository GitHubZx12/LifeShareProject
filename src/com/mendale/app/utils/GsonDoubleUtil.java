package com.mendale.app.utils;

import java.lang.reflect.Type;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Google Gson int 类型字段适配器
 * @author wenjian
 *
 */
public class GsonDoubleUtil implements JsonDeserializer<String>,
		JsonSerializer<Double> {

	@Override
	public JsonElement serialize(Double arg0, Type arg1,
			JsonSerializationContext arg2) {
		return new JsonPrimitive(arg0);

	}

	@Override
	public String deserialize(JsonElement arg0, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
		String runtime="";;
	
		try {
			String id = arg0.getAsString();
			if(id != null && !id.trim().equals("")){
				runtime = id+"";
			}else{
				runtime = "0.0";
			}
		} catch (Exception e) {
			runtime = "0.0";
		}
		return runtime;

	}

}
