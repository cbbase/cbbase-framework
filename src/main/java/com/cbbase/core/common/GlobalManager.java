package com.cbbase.core.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cbbase.core.common.BeanFactory;
import com.cbbase.core.handler.AuthHandler;
import com.cbbase.core.handler.DataSourceHandler;

/**
 * 
 * @author changbo
 *
 */
public class GlobalManager {
	
	public static DataSourceHandler getDataSourceHandler() {
		return BeanFactory.getBean(DataSourceHandler.class);
	}
	
	public static AuthHandler getAuthHandler() {
		return BeanFactory.getBean(AuthHandler.class);
	}
	
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	public static HttpServletResponse getResponse() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
	}
	
	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	public static void setSession(String key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static void removeSession(String key) {
		getSession().removeAttribute(key);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getSession(String key) {
		return (T) getSession().getAttribute(key);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getLoginUser() {
		return (T) getSession().getAttribute(SessionConstants.LOGIN_USER);
	}
	
	public static boolean checkAuth(String auth) {
		if(getAuthHandler() != null) {
			return getAuthHandler().checkAuth(getRequest(), auth);
		}
		return false;
	}
	
	public static String getDataSource() {
		if(getDataSourceHandler() != null) {
			getDataSourceHandler().getDataSource(getRequest());
		}
		return null;
	}
}
