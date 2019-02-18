package com.cbbase.core.assist.test;

import com.cbbase.core.tools.StringUtil;

public abstract class ResultHandler {
	
	private long requestTimesPerClient = 10L;
	private long showInterval = 1000L;
	
	public abstract boolean isSuccess(String result);
	
	public void showResultCount(ResultCount resultCount) {
		String request = StringUtil.rightPad(""+resultCount.getRequstTimes(), ' ', 8);
		String success = StringUtil.rightPad(""+resultCount.getSuccessTimes(), ' ', 8);
		String error = StringUtil.rightPad(""+resultCount.getErrorTimes(), ' ', 8);
		System.out.println("[request]:"+request+"\t[success]:"+success+"\t[error]:"+error);
	}
	
	public long getRequestTimesPerClient() {
		return requestTimesPerClient;
	}
	public void setRequestTimesPerClient(long requestTimesPerClient) {
		this.requestTimesPerClient = requestTimesPerClient;
	}
	public long getShowInterval() {
		return showInterval;
	}
	public void setShowInterval(long showInterval) {
		this.showInterval = showInterval;
	}

	
}
