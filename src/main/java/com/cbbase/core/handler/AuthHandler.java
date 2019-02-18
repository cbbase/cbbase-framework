package com.cbbase.core.handler;

import javax.servlet.http.HttpServletRequest;

public interface AuthHandler {
	
	public boolean checkAuth(HttpServletRequest request, String auth);

}
