package com.cbbase.core.tools;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @author changbo
 *
 */
public class MapUtil {
	
	public static Map<String, String> paramToMap(String str){
		Map<String, String> map = new LinkedHashMap<String, String>();
		if(StringUtil.isEmpty(str)){
			return map;
		}
		String[] strs = str.split("&");
		for(String s : strs){
			if(StringUtil.isEmpty(s)){
				continue;
			}
			String[] kv = s.split("=");
			if(kv != null){
				map.put(kv[0].trim(), kv[1].trim());
			}
		}
		return map;
	}

	public static String mapToParam(Map<String, String> map){
		StringBuilder sb = new StringBuilder();
		if(map == null){
			return sb.toString();
		}
		for(Entry<String, String> entry : map.entrySet()){
			sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		
		return sb.toString();
	}
	
	public static String getString(Map<?, ?> map, Object key){
		if(map.get(key) == null) {
			return null;
		}
		return map.get(key).toString();
	}
	
	public static boolean getBoolean(Map<?, ?> map, Object key){
		return StringUtil.toBoolean(StringUtil.getValue(map.get(key)));
	}
	
	public static int getInt(Map<?, ?> map, Object key){
		return StringUtil.toInt(StringUtil.getValue(map.get(key)));
	}
	
	public static long getLong(Map<?, ?> map, Object key){
		return StringUtil.toLong(StringUtil.getValue(map.get(key)));
	}
	
}
