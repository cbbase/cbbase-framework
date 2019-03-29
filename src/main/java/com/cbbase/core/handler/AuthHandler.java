package com.cbbase.core.handler;

import javax.servlet.http.HttpServletRequest;

public interface AuthHandler {
	
	public Long getUserId();
	public String getUserName();
	public boolean checkAuth(HttpServletRequest request, String auth);

}
