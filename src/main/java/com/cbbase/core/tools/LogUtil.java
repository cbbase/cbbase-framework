package com.cbbase.core.tools;

public class LogUtil {
	
	public static ThreadLocal<Long> timer = new ThreadLocal<Long>();
	
	public static void timerStart() {
		timer.set(System.currentTimeMillis());
	}
	
	public static void timerUsed() {
		System.out.println("[LogUtil]timerUsed:"+(System.currentTimeMillis() - timer.get()));
	}
}
