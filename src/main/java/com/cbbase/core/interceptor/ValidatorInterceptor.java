package com.cbbase.core.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cbbase.core.annotation.Validator;
import com.cbbase.core.common.ApplicationConfig;
import com.cbbase.core.container.RestResponse;
import com.cbbase.core.extension.validator.ValidatorManager;
import com.cbbase.core.tools.JsonUtil;
import com.cbbase.core.tools.ServletUtil;
import com.cbbase.core.tools.StringUtil;

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
		
		if(StringUtil.hasValue(validator.value())){
			String prefix = ApplicationConfig.getParam("param.validator.prefix", "");
			String suffix = ApplicationConfig.getParam("param.validator.suffix", "");
			String validatorFile = prefix+validator.value()+suffix;
			String result = ValidatorManager.check(request, validatorFile);
			if(StringUtil.hasValue(validator.value())) {
				RestResponse resp = new RestResponse(2000, "参数错误:"+result);
				ServletUtil.returnString(response, JsonUtil.toJson(resp));
				return false;
			}
		}
	    
		return true;
	}

}
