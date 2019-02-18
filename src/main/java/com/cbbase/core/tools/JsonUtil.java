package com.cbbase.core.tools;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 	基于fastjson
 * @author changbo
 *
 */
public class JsonUtil {

	public static final Type TYPE_MAP = new TypeReference<Map<String, String>>(){}.getType();
	public static final Type TYPE_MAP_LIST = new TypeReference<List<Map<String, String>>>(){}.getType();

	public static final Pattern PATTERN = Pattern.compile("^\\{[\\*]*\\}$");
	
	public static Map<String, String> toMap(String json){
		try{
			return toObject(json, TYPE_MAP);
		}catch (Exception e) {
			return null;
		}
	}
	
	public static <T> T toObject(String json, Type type){
		return JSON.parseObject(json, type);
	}
	
	public static <T> T toObject(String json, Class<T> clazz){
		return JSON.parseObject(json, clazz);
	}
	
	public static String toJson(Object obj){
		return JSON.toJSONString(obj, SerializerFeature.SortField);
	}
	
	public static String toJsonFormat(Object obj){
		return JSON.toJSONString(obj, SerializerFeature.SortField, SerializerFeature.PrettyFormat);
	}
	
}
