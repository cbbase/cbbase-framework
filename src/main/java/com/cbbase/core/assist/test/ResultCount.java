package com.cbbase.core.assist.test;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class ResultCount {
	
	private Date startDate;
	private Date endDate;
	private AtomicLong requstTimes = new AtomicLong(0);
	private AtomicLong successTimes = new AtomicLong(0);
	private AtomicLong errorTimes = new AtomicLong(0);
	private AtomicLong finishTimes = new AtomicLong(0);
	private AtomicLong totalTime = new AtomicLong(0);
	
	public ResultCount(){
		startDate = new Date();
	}
	
	public void addRequstTimes(){
		requstTimes.incrementAndGet();
	}
	
	public void addSuccessTimes(){
		successTimes.incrementAndGet();
	}
	
	public void addErrorTimes(){
		errorTimes.incrementAndGet();
	}
	
	public void addFinishTimes(){
		endDate = new Date();
		finishTimes.incrementAndGet();
	}
	
	public void addTolalTime(long time){
		totalTime.addAndGet(time);
	}
	
	public long getRequstTimes() {
		return requstTimes.get();
	}

	public long getSuccessTimes() {
		return successTimes.get();
	}
	
	public long getErrorTimes() {
		return errorTimes.get();
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public long getFinishTimes() {
		return finishTimes.get();
	}

	public long getTotalTime() {
		return totalTime.get();
	}
	
}
