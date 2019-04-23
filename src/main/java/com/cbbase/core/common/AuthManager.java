package com.cbbase.core.common;

import com.cbbase.core.handler.AuthHandler;

public class AuthManager {

	
	public static AuthHandler getAuthHandler() {
		return BeanFactory.getBean(AuthHandler.class);
	}
	
	public static Long getUserId() {
		return getAuthHandler().getUserId();
	}
	
	public static String getUserName() {
		return getAuthHandler().getUserName();
	}
	
	public static boolean checkAuth(String auth) {
		if(getAuthHandler() != null) {
			return getAuthHandler().checkAuth(GlobalManager.getRequest(), auth);
		}
		return false;
	}

}
