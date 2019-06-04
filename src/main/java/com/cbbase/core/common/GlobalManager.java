package com.cbbase.core.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 
 * @author changbo
 *
 */
public class GlobalManager {
	
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
}
