package com.cbbase.core.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cbbase.core.tools.JsonUtil;
import com.cbbase.core.tools.StringUtil;


/**
 * 
 * @author changbo
 *
 */
public class LocalCache {
	
	private static Map<String, String> map = new ConcurrentHashMap<>();
	
	public static void put(String key, String value) {
		map.put(key, value);
	}
	
	public static String get(String key) {
		return map.get(key);
	}
	
	public static int getAsInt(String key) {
		String value = map.get(key);
		if(StringUtil.isEmpty(value)) {
			return 0;
		}
		try {
			return Integer.valueOf(map.get(value));
		}catch (Exception e) {
			return 0;
		}
	}
	
	public static long getAsLong(String key) {
		String value = map.get(key);
		if(StringUtil.isEmpty(value)) {
			return 0;
		}
		try {
			return Long.valueOf(map.get(value));
		}catch (Exception e) {
			return 0;
		}
	}
	
	public static Map<String, String> getAsMap(String key) {
		String value = map.get(key);
		if(StringUtil.isEmpty(value)) {
			return new HashMap<>();
		}
		try {
			return JsonUtil.toMap(value);
		}catch (Exception e) {
			return new HashMap<>();
		}
	}
	
	public static <T> T getAsObject(String key, Class<T> clazz) {
		String value = map.get(key);
		if(StringUtil.isEmpty(value)) {
			return null;
		}
		try {
			return JsonUtil.toObject(value, clazz);
		}catch (Exception e) {
			return null;
		}
	}

}
