package com.cbbase.core.common;

import java.util.HashMap;
import java.util.Map;

public class ThreadCache {
	
	private static ThreadLocal<Map<String, Object>> cache = new ThreadLocal<Map<String, Object>>();
	
	public static Map<String, Object> getCacheMap(){
		Map<String, Object> map = cache.get();
		if(map == null) {
			map = new HashMap<String, Object>();
			cache.set(map);
		}
		return map;
	}
	
	public static void put(String key, Object value) {
		getCacheMap().put(key, value);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T get(String key){
		return (T) getCacheMap().get(key);
	}
	
	
}
