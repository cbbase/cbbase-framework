package com.cbbase.core.tools;

import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * 
 * @author changbo
 *
 */
public class SignUtil {
	
	public static String calcSign(Map<String, String> map, String key){
		return calcSign(map, key, null, "&", "MD5", false);
	}
	
	public static String calcSign(Map<String, String> map, String key, boolean ignoreEmpty){
		return calcSign(map, key, null, "&", "MD5", ignoreEmpty);
	}
	
	public static String calcSign(Map<String, String> map, String key, String keyName){
		return calcSign(map, key, keyName, "&", "MD5", false);
	}
	
	public static String calcSign(Map<String, String> map, String key, String keyName, String delimiter){
		return calcSign(map, key, keyName, delimiter, "MD5", false);
	}
	
	public static String calcSign(Map<String, String> map, String key, String keyName, String delimiter, String signType, boolean ignoreEmpty){
		//使用TreeMap实现自动排序
		Map<String, String> treeMap = new TreeMap<String, String>();
		treeMap.putAll(map);
		return calcSignNoSort(treeMap, keyName, key, delimiter, signType, ignoreEmpty);
	}
	
	/**
	 * 
	 * 如果想实现先进先出,使用LinkedHashMap作为参数
	 * 
	 */
	public static String calcSignNoSort(Map<String, String> map, String keyName, String key, String delimiter, String signType, boolean ignoreEmpty){
		StringBuilder sb = new StringBuilder();
		for(Entry<String, String> entry : map.entrySet()){
			if(ignoreEmpty && StringUtil.isEmpty(entry.getValue())){
				continue;
			}
			sb.append(delimiter).append(entry.getKey()).append("=").append(StringUtil.getValue(entry.getValue()));
		}
		if(StringUtil.hasValue(key)){
			if(StringUtil.hasValue(keyName)){
				sb.append(delimiter).append(keyName).append("=").append(key);
			}else{
				sb.append(key);
			}
		}
		String sign_str = null;
		if(sb.length() > 0){
			sign_str = sb.substring(1);
		}
		LogUtil.printLog("sign_str:"+sign_str);
		String sign = null;
		if("MD5".equalsIgnoreCase(signType)){
			sign = HashUtil.getMD5(sign_str);
		}else if("SHA1".equalsIgnoreCase(signType)){
			sign = HashUtil.getSHA1(sign_str);
		}else if("SHA256".equalsIgnoreCase(signType)){
			sign = HashUtil.getSHA256(sign_str);
		}
		return sign;
	}

	public static String toSignString(Map<String, String> map){
		return toSignString(map, "&", false);
	}
	
	public static String toSignString(Map<String, String> map, String delimiter, boolean ignoreEmpty){
		StringBuilder sb = new StringBuilder();
		for(Entry<String, String> entry : map.entrySet()){
			if(ignoreEmpty && StringUtil.isEmpty(entry.getValue())){
				continue;
			}
			sb.append(delimiter).append(entry.getKey()).append("=").append(StringUtil.getValue(entry.getValue()));
		}
		String sign_str = null;
		if(sb.length() > 0){
			sign_str = sb.substring(1);
		}
		LogUtil.printLog("sign_str:"+sign_str);
		return sign_str;
	}

	public static String calcSignByValue(Map<String, String> map, String key, String signType){
		//使用TreeMap实现自动排序
		Map<String, String> treeMap = new TreeMap<String, String>();
		treeMap.putAll(map);
		StringBuilder sb = new StringBuilder();
		for(Entry<String, String> entry : treeMap.entrySet()){
			if(StringUtil.isEmpty(entry.getValue())){
				continue;
			}
			sb.append(StringUtil.getValue(entry.getValue()));
		}
		if(StringUtil.hasValue(key)){
			sb.append(key);
		}
		String sign_str = sb.toString();
		LogUtil.printLog("sign_str:"+sign_str);
		String sign = null;
		if("MD5".equalsIgnoreCase(signType)){
			sign = HashUtil.getMD5(sign_str);
		}else if("SHA1".equalsIgnoreCase(signType)){
			sign = HashUtil.getSHA1(sign_str);
		}else if("SHA256".equalsIgnoreCase(signType)){
			sign = HashUtil.getSHA256(sign_str);
		}
		return sign;
	}
	
}
