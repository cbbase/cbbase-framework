package com.cbbase.core.common;

import com.cbbase.core.constants.SessionConstants;
import com.cbbase.core.handler.AuthHandler;

public class AuthManager {

	
	public static AuthHandler getAuthHandler() {
		return BeanFactory.getBean(AuthHandler.class);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getLoginUser() {
		return (T) GlobalManager.getSession().getAttribute(SessionConstants.LOGIN_USER);
	}
	
	public static boolean checkAuth(String auth) {
		if(getAuthHandler() != null) {
			return getAuthHandler().checkAuth(GlobalManager.getRequest(), auth);
		}
		return false;
	}

}
