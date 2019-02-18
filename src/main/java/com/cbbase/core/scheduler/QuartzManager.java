package com.cbbase.core.scheduler;

import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 
 * Quartz调度管理器
 * 
 * @author changbo
 * 
 */
public class QuartzManager {
	
	private static final SchedulerFactory factory = new StdSchedulerFactory();
	private static Scheduler defaultScheduler = null;
	
	public static Scheduler getDefaultScheduler(){
		if(defaultScheduler != null){
			return defaultScheduler;
		}
		try {
			defaultScheduler = factory.getScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return defaultScheduler;
	}
	
	public static void addJob(String schedulerName, Class<? extends Job> jobClass, String cronExp){
		addJob(schedulerName, jobClass, cronExp, null);
	}
	
	public static void addJob(String schedulerName, Class<? extends Job> jobClass, String cronExp, Map<String, Object> params){
		if(checkJobExist(schedulerName)){
			return;
		}
		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(schedulerName).build();
		if(params != null){
			jobDetail.getJobDataMap().putAll(params);
		}
		CronTrigger trigger = TriggerBuilder.newTrigger()
	            .withSchedule(CronScheduleBuilder.cronSchedule(cronExp))
	            .withIdentity(schedulerName)
	            .build();
		try {
			getDefaultScheduler().scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean checkJobExist(String schedulerName){
		try {
			JobKey jobKey = JobKey.jobKey(schedulerName);
			return getDefaultScheduler().checkExists(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void deleteJob(String schedulerName){
		try {
			JobKey jobKey = JobKey.jobKey(schedulerName);
			getDefaultScheduler().deleteJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	
	public static void pauseJob(String schedulerName){
		try {
			JobKey jobKey = JobKey.jobKey(schedulerName);
			getDefaultScheduler().pauseJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public static void start(){
		try {
			if(!getDefaultScheduler().isStarted()){
				getDefaultScheduler().start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public static void shutdown(){
		try {
			if(getDefaultScheduler().isStarted()){
				getDefaultScheduler().shutdown();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public static void runJob(String schedulerName){
		runJob(schedulerName, null);
	}
	
	public static void runJob(String schedulerName, Map<String, Object> params){
		try {
			JobKey jobKey = JobKey.jobKey(schedulerName);
			if(params != null){
				getDefaultScheduler().triggerJob(jobKey, new JobDataMap(params));
			}else{
				getDefaultScheduler().triggerJob(jobKey);
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
