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
import com.cbbase.core.common.SessionConstants;
import com.cbbase.core.common.GlobalManager;
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
	    
        Authority[] authorities = method.getAnnotationsByType(Authority.class);
        if(authorities == null || authorities.length == 0) {
        	return true;
        }
        for(Authority authority : authorities) {
            //检查是否登录
    		if(authority.checkLogin()){
    			if(GlobalManager.getLoginUser() == null){
    				logger.debug("authority.isLogin():"+authority.checkLogin());
    				RestResponse resp = new RestResponse(-1, "No login");
    				ServletUtil.returnString(response, JsonUtil.toJson(resp));
    				return false;
    			}
    		}
    		
    		//检查是否拥有指定的权限
    		if(StringUtil.hasValue(authority.value())){
    			if(!GlobalManager.checkAuth(authority.value())){
    				logger.debug("authority.value():"+authority.value());
    				RestResponse resp = new RestResponse(-2, "No authority");
    				ServletUtil.returnString(response, JsonUtil.toJson(resp));
    				return false;
    			}
    		}
    		
            //检查sessionToken
    		if(authority.formToken()){
    	    	String requestToken = (String) request.getParameter(SessionConstants.FORM_TOKEN);
    	    	String sessionToken = GlobalManager.getSession(SessionConstants.FORM_TOKEN);
    	    	if(!StringUtil.isEqualIgnoreCase(requestToken, sessionToken)) {
    				logger.debug("authority.fromToken():"+authority.formToken());
    				RestResponse resp = new RestResponse(-3, "Token error");
    				ServletUtil.returnString(response, JsonUtil.toJson(resp));
    				return false;
    	    	}
    		}
        }
        
		return true;
	}
	
}
