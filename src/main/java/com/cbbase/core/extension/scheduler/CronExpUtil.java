package com.cbbase.core.extension.scheduler;

import java.util.Date;

import com.cbbase.core.tools.DateUtil;

/**
 * 
 * 简单的Cron表达式工具
 * @author changbo
 *
 */
public class CronExpUtil {

	/**
	 * 每月的最后一天执行
	 */
	public static String createCronLastDayPerMonth(int minute, int hour){
		return "0 "+minute+" "+hour+" L * ?";
	}
	
	public static String createCronPerMonth(int minute, int hour, int day){
		return "0 "+minute+" "+hour+" "+day+" * ?";
	}
	
	public static String createCronPerDay(int minute, int hour){
		return "0 "+minute+" "+hour+" * * ?";
	}
	
	public static String createCronPerHour(int minute){
		return "0 "+minute+" * * * ?";
	}
	
	public static String createCronPerMinute(int second){
		return second+" * * * * ?";
	}
	
	/**
	 * day:按星期天为1,星期一为2,到星期六为7的顺序取值
	 */
	public static String createCronPerWeek(int minute, int hour, int day){
		return "0 "+minute+" "+hour+" ? * "+day;
	}
	
	public static String createCronByTime(Date time){
		int s = DateUtil.getSecond(time);
		int m = DateUtil.getMinute(time);
		int h = DateUtil.getHour(time);
		int d = DateUtil.getDay(time);
		int month = DateUtil.getMonth(time);
		int y = DateUtil.getYear(time);
		return s+" "+m+" "+h+" "+d+" "+month+" ? "+y;
	}
	
}
