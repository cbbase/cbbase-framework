package com.cbbase.core.tools;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;


/**
 * @author changbo
 * 
 */
public class CommonUtil {
	
	public static String getProjectPath(){
		return System.getProperty("user.dir").replaceAll("\\\\", "/");
	}
	
	public static String getLocalMac(){
		//获取网卡，获取地址
		InetAddress ia;
		byte[] mac = null;
		try {
			ia = InetAddress.getLocalHost();
			mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		StringBuilder sb = new StringBuilder("");
		for(int i=0; i<mac.length; i++) {
			if(i!=0) {
				sb.append("-");
			}
			//字节转换为整数
			int temp = mac[i]&0xff;
			String str = Integer.toHexString(temp);
			if(str.length()==1) {
				sb.append("0"+str);
			}else {
				sb.append(str);
			}
		}
		return sb.toString().toUpperCase();
	}
	
	public static String getMemoryInfo(){
		Runtime runtime = Runtime.getRuntime();
		long total = runtime.totalMemory()/1048576;
		long use = (runtime.totalMemory()-runtime.freeMemory())/1048576;
		return use + "M/" + total + "M";
	}
	
	public static int getThreadCount(){
		return ManagementFactory.getThreadMXBean().getThreadCount();
	}
	
	public static void showMethodStack() {
    	StackTraceElement[] mStacks = Thread.currentThread().getStackTrace();
    	for(StackTraceElement s: mStacks){
    		System.out.println(s.getClassName()+"."+s.getMethodName()+ "()["+s.getLineNumber()+"]");
    	}
	}
	
}
