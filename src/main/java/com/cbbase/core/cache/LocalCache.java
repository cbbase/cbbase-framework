package com.cbbase.core.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 
 * @author changbo
 *
 */
public class LocalCache {
	
	private static Map<String, Object> map = new ConcurrentHashMap<>();
	
	public static void put(String key, Object value) {
		map.put(key, value);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T get(String key){
		return (T) map.get(key);
	}
	

}
