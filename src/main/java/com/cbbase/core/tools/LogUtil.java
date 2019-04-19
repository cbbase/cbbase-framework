package com.cbbase.core.tools;

public class LogUtil {
	
	private static boolean showLog = false;

	public static void setShowLog(boolean showLog) {
		LogUtil.showLog = showLog;
	}
	
	public static void printLog(String log) {
		if(!showLog){
			return;
		}
		System.out.println(log);
	}
}
