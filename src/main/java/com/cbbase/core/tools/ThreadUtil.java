package com.cbbase.core.tools;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 
 * @author changbo
 *
 */
public class ThreadUtil {

	public static String getCurrentMethod() {
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}
	
    public static void sleep(long m){
		try {
			Thread.sleep(m);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
    public static void sleep(long m, int random){
		try {
			Thread.sleep(m, RandomUtil.random(0, random));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void addDefaultUncaughtExceptionHandler(){
    	addDefaultUncaughtExceptionHandler("[UncaughtExceptionHandler]Thread");
    }
    
    public static void addDefaultUncaughtExceptionHandler(final String message){
    	Thread.currentThread().setUncaughtExceptionHandler(new UncaughtExceptionHandler(){
			
			public void uncaughtException(Thread thread, Throwable throwable) {
				System.out.println(message);
			}
    	});
    }
}
