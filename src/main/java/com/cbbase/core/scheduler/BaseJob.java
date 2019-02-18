package com.cbbase.core.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cbbase.core.tools.DateUtil;


public abstract class BaseJob implements Job {
	
	protected boolean jobResult = false;
	protected long useTime = 0;
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			beforeJob(context);
		}catch (Exception e) {
			e.printStackTrace();
		}
		long start = DateUtil.getCurrentTimeStamp();
		try{
			jobResult = doJob(context);
		}catch (Exception e) {
			e.printStackTrace();
			jobResult = false;
		}
		long end = DateUtil.getCurrentTimeStamp();
		useTime = end-start;
		try{
			afterJob(context);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public abstract void beforeJob(JobExecutionContext context);
	public abstract boolean doJob(JobExecutionContext context);
	public abstract void afterJob(JobExecutionContext context);
}
