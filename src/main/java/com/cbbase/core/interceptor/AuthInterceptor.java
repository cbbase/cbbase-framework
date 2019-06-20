package com.cbbase.core.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cbbase.core.annotation.Authority;
import com.cbbase.core.container.RestResponse;
import com.cbbase.core.common.AuthManager;
import com.cbbase.core.tools.JsonUtil;
import com.cbbase.core.tools.ServletUtil;
import com.cbbase.core.tools.StringUtil;

/**
 *	权限控制:通过controller方法的注释,获取需要校验的权限,过滤权限不足的请求
 * @author changbo
 *
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		if(!(handler instanceof HandlerMethod)) {
			return true;
		}
	    HandlerMethod handlerMethod = (HandlerMethod) handler;
	    Method method = handlerMethod.getMethod();
	    
        Authority authority = method.getAnnotation(Authority.class);
        if(authority == null) {
        	return true;
        }
        String uri = request.getRequestURI();
        //检查是否登录
		if(authority.login()){
			if(AuthManager.getUserId() == null){
				logger.debug("authority.isLogin():"+authority.login());
				if(uri.endsWith(".html")) {
					request.setAttribute("errorMsg", "用户未登录");
		            request.getRequestDispatcher("/error.jsp").forward(request, response);
				}else {
					RestResponse resp = new RestResponse(1000, "用户未登录");
					ServletUtil.returnString(response, JsonUtil.toJson(resp));
				}
				return false;
			}
		}
		
		//检查是否拥有指定的权限
		if(StringUtil.hasValue(authority.value())){
			if(!AuthManager.checkAuth(authority.value())){
				logger.debug("authority.value():"+authority.value());
				if(uri.endsWith(".html")) {
					request.setAttribute("errorMsg", "用户无权限");
		            request.getRequestDispatcher("/error.jsp").forward(request, response);
				}else {
					RestResponse resp = new RestResponse(1001, "用户无权限");
					ServletUtil.returnString(response, JsonUtil.toJson(resp));
				}
				return false;
			}
		}
        
		return true;
	}
	
}
