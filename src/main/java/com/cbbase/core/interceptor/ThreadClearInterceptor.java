package com.cbbase.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cbbase.core.cache.ThreadCache;

/**
 *	清除线程缓存
 * @author changbo
 *
 */
public class ThreadClearInterceptor extends HandlerInterceptorAdapter {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
		ThreadCache.clear();
	}
	
}
