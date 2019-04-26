package com.cbbase.core.tools;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 
 * 基于org.apache.commons.lang3.time.FastDateFormat实现
 * <br>java.util.SimpleDateFormat是线程不安全的,所有弃用
 * @author changbo
 * 
 */
public class DateUtil {
	
	public static final ZoneId zoneId = ZoneId.systemDefault();
	
	public static final DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
	
	public static final FastDateFormat dfDateCn = FastDateFormat.getInstance("yyyy年MM月dd日");
    public static final FastDateFormat dfTimeCn = FastDateFormat.getInstance("yyyy年MM月dd日 HH时mm分ss秒");
    
    public static final FastDateFormat dfYearMonth = FastDateFormat.getInstance("yyyyMM");
    public static final FastDateFormat dfDateNum = FastDateFormat.getInstance("yyyyMMdd");
    public static final FastDateFormat dfDateNum6 = FastDateFormat.getInstance("yyMMdd");
    public static final FastDateFormat dfTimeNum = FastDateFormat.getInstance("yyyyMMddHHmmss");
    public static final FastDateFormat dfTimeNumMs = FastDateFormat.getInstance("yyyyMMddHHmmssSSS");
    public static final FastDateFormat dfDate = FastDateFormat.getInstance("yyyy-MM-dd");
    public static final FastDateFormat dfTime = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    public static final FastDateFormat dfTimeOnly = FastDateFormat.getInstance("HH:mm:ss");
    
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(zoneId).toLocalDateTime();
    }
    
    public static Date LocalDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(zoneId).toInstant());
    }
    
    public static String dateToYearMonth(Date date){
    	return dfYearMonth.format(date);
    }
    
    public static String dateToString(Date date){
    	return dfDate.format(date);
    }
    
    public static String timeToString(Date date){
    	return dfTime.format(date);
    }
    
    public static String dateToCNString(Date date){
    	return dfDateCn.format(date);
    }
    
    public static String timeToCNString(Date date){
    	return dfTimeCn.format(date);
    }

    public static String dateToStringNum(Date date){
    	return dfDateNum.format(date);
    }
    
    public static String dateToStringNum6(Date date){
    	return dfDateNum6.format(date);
    }
    
    public static String timeToStringNum(Date date){
    	return dfTimeNum.format(date);
    }

    public static String getCurrentDateString(){
    	return dfDate.format(new Date());
    }
    public static String getCurrentDateStringNum(){
    	return dfDateNum.format(new Date());
    }
    
    public static String getCurrentTimeString(){
    	return dfTime.format(new Date());
    }
    public static String getCurrentTimeStringNum(){
    	return dfTimeNum.format(new Date());
    }
    
    public static String getCurrentTimeOnlyString(){
    	return dfTimeOnly.format(new Date());
    }
    public static String getCurrentTimeStringNumMs(){
    	return dfTimeNumMs.format(new Date());
    }
    
    public static Date stringToDate(String date){
    	try {
			return dfDate.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public static Date stringToTime(String date){
    	try {
			return dfTime.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public static Date stringToDateNum(String date){
    	try {
			return dfDateNum.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public static Date stringToTimeNum(String date){
    	try {
			return dfTimeNum.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public static boolean isSameDay(Date date1, Date date2){
    	
    	if(dateToString(date1).equals(dateToString(date2))){
    		return true;
    	}
    	return false;
    }
    
    public static Date nextYears(Date date, int years){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, years);
		return calendar.getTime();
    }
    
    public static Date nextMonths(Date date, int months){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
    }
    
    public static Date nextDays(Date date, int days){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, days);
		return calendar.getTime();
    }
    
    public static Date nextDaysByCurrent(int days){
    	return nextDays(new Date(), days);
    }
    
    public static Date nextMinutes(Date date, int minutes){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minutes);
		return calendar.getTime();
    }
    
    public static Date nextSeconds(Date date, int seconds){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, seconds);
		return calendar.getTime();
    }
    
    public static Date nextMinutesByCurrent(int minutes){
    	return nextMinutes(new Date(), minutes);
    }
    
    public static Date setYear(Date date, int year){
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.YEAR, year);
		return calendar.getTime();
    }
    
    public static Date setMonth(Date date, int month){
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH, month);
		return calendar.getTime();
    }
    
    public static Date setDay(Date date, int day){
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
    }
    
    public static Date setMonthLastDay(Date date){
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		return calendar.getTime();
    }
    
    public static int getYear(Date date){
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
    }
    
    public static int getMonth(Date date){
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH);
    }

    public static int getDay(Date date){
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
    }
    
    public static int getHour(Date date){
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
    }
    
    public static int getMinute(Date date){
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
    }
    
    public static int getSecond(Date date){
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
    }
    
    public static long getCurrentMsec(){
    	return System.currentTimeMillis();
    }
    
    public static long getCurrentTimeStamp(){
    	return getCurrentMsec()/1000;
    }
    
    public static Date getCurrentTime(){
    	return new Date();
    }
    
    public static java.sql.Date getCurrentSqlDate(){
    	return new java.sql.Date(getCurrentMsec());
    }

    public static String getYesterdayDateString(){
    	return dfDate.format(nextDays(new Date(), -1));
    }
    public static String getTomorrowDateString(){
    	return dfDate.format(nextDays(new Date(), 1));
    }
    
    /**
     * 检查时间有效性
     * @param times
     * @return
     */
    public static boolean checkCurrentTime(String times, long expireTime){
    	long tt = StringUtil.toLong(times);
    	long cc = new Date().getTime();
    	if(Math.abs(tt-cc) > expireTime){
    		return false;
    	}
    	return true;
    }
    
    public static Date dateToTimeStart(Date date){
    	if(date == null){
    		return null;
    	}
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.set(Calendar.HOUR, 0);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);
    	return cal.getTime();
    }
    
    public static Date dateToTimeEnd(Date date){
    	if(date == null){
    		return null;
    	}
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.set(Calendar.HOUR, 23);
    	cal.set(Calendar.MINUTE, 59);
    	cal.set(Calendar.SECOND, 59);
    	cal.set(Calendar.MILLISECOND, 999);
    	return cal.getTime();
    }
    
    public static int getDValue(Date date1, Date date2){
    	if(date1 == null || date2 == null){
    		throw new RuntimeException();
    	}
    	date1 = dateToTimeStart(date1);
    	date2 = dateToTimeStart(date2);
    	long time = date1.getTime() - date2.getTime();
    	
    	return (int)(time/(24*60*60*1000));
    }
    
    public static int getDiffValue(String date1, String date2){
    	if(date1 == null || date2 == null){
    		throw new RuntimeException();
    	}
    	return getDValue(stringToDate(date1), stringToDate(date2));
    }
    
    public static int getWeekDay(Date date){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	int wd = cal.get(Calendar.DAY_OF_WEEK) -1;
    	wd = (wd == 0) ? 7 : wd;
    	return wd;
    }
    
    public static int getCurrentYear(){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(new Date());
    	return cal.get(Calendar.YEAR);
    }
    
    public static int getCurrentMonth(){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(new Date());
    	return cal.get(Calendar.MONTH);
    }
    
    public static Date dateOrTime(String dateOrTime){
    	if(StringUtil.isEmpty(dateOrTime)){
    		return null;
    	}
    	dateOrTime = dateOrTime.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "").replaceAll("/", "");
    	if(dateOrTime.length() == 8){
    		return stringToDateNum(dateOrTime);
    	}else if(dateOrTime.length() == 14){
    		return stringToTimeNum(dateOrTime);
    	}
    	return null;
    }
    
    public static String formatDateToNormal(String date){
    	date = date.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "").replaceAll("/", "");
    	if(date.length() >= 8){
    		return date.substring(0, 4)+"-"+date.substring(4, 6)+"-"+date.substring(6, 8);
    	}
    	return date;
    }
    
    public static String formatTimeToNormal(String time){
    	time = time.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "").replaceAll("/", "");
    	int length = time.length();
    	if(length >= 6){
    		return time.substring(length-6, length-4)+":"+time.substring(length-4, length-2)+":"+time.substring(length-2, length);
    	}
    	return time;
    }
    
}
