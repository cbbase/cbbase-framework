package com.cbbase.core.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


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
		if(value == null || value.trim().length() == 0 || "null".equalsIgnoreCase(value)) {
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
		if(value == null || value.trim().length() == 0 || "null".equalsIgnoreCase(value)) {
			return 0;
		}
		try {
			return Long.valueOf(map.get(value));
		}catch (Exception e) {
			return 0;
		}
	}

}
