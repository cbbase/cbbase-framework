package com.cbbase.core.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cbbase.core.annotation.Validator;
import com.cbbase.core.tools.StringUtil;

/**
 *
 * @author changbo
 *
 */
@Component
public class ValidatorInterceptor extends HandlerInterceptorAdapter {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		if(!(handler instanceof HandlerMethod)) {
			return true;
		}
	    HandlerMethod handlerMethod = (HandlerMethod) handler;
	    Method method = handlerMethod.getMethod();
	    
	    Validator validator = method.getAnnotation(Validator.class);
        if(validator == null) {
        	return true;
        }
		//
		if(StringUtil.hasValue(validator.value())){
			
		}
        
		return true;
	}
	
}
