package com.cbbase.core.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cbbase.core.annotation.DataSource;
import com.cbbase.core.common.GlobalManager;
import com.cbbase.core.datasource.DynamicDataSource;
import com.cbbase.core.tools.StringUtil;

/**
 *	 数据源配置拦截器
 * @author changbo
 *
 */
public class DataSourceInterceptor extends HandlerInterceptorAdapter {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		if(!(handler instanceof HandlerMethod)) {
			return true;
		}
	    HandlerMethod handlerMethod = (HandlerMethod) handler;
	    Method method = handlerMethod.getMethod();
	    
	    DataSource dataSource = method.getAnnotation(DataSource.class);
        if(dataSource == null) {
        	return true;
        }
        
		//设置为指定的数据源
		if(StringUtil.hasValue(dataSource.value())){
			DynamicDataSource.setDataSource(dataSource.value());
			return true;
		}
		
        //按自定义方式设置数据源
		if(dataSource.route()){
			DynamicDataSource.setDataSource(GlobalManager.getDataSource());
		}
		
		return true;
	}
	
}
