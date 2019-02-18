package com.cbbase.core.tools;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public class CountUtil {
	
	private static final Map<String, LongAdder> countMap = new ConcurrentHashMap<String, LongAdder>();
	private static final Object lock = new Object();
	
	private static void createCount(String key){
		if(countMap.get(key) == null){
			synchronized (lock) {
				if(countMap.get(key) == null){
					countMap.put(key, new LongAdder());
				}
			}
		}
	}
	
	public static long incrementAndGet(String key){
		if(StringUtil.isEmpty(key)){
			return -1;
		}
		if(countMap.get(key) == null){
			createCount(key);
		}
		LongAdder longAdder = countMap.get(key);
		longAdder.increment();
		return longAdder.longValue();
	}
	
	public static long decrementAndGet(String key){
		if(StringUtil.isEmpty(key)){
			return -1;
		}
		if(countMap.get(key) == null){
			createCount(key);
		}
		LongAdder longAdder = countMap.get(key);
		longAdder.decrement();
		return longAdder.longValue();
	}

	public static long get(String key){
		if(StringUtil.isEmpty(key)){
			return -1;
		}
		if(countMap.get(key) == null){
			countMap.put(key, new LongAdder());
		}
		LongAdder longAdder = countMap.get(key);
		return longAdder.longValue();
	}

	public synchronized static long remove(String key){
		if(StringUtil.isEmpty(key)){
			return -1;
		}
		if(countMap.get(key) == null){
			return -1;
		}
		LongAdder longAdder = countMap.remove(key);
		return longAdder.longValue();
	}
	
	
}
