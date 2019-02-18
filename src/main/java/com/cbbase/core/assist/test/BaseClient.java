package com.cbbase.core.assist.test;

import java.util.Map;

public abstract class BaseClient {
	
	public abstract void init();
	public abstract String getUrl();
	public abstract Map<String, String> getParameterMap();
	public abstract String getRequestBody();
	
}
