package com.cbbase.core.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * @author changbo
 *
 */
@Component
public class ServiceFactory implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext = null;
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ServiceFactory.applicationContext = applicationContext;
	}
	
	public static <T> T getBean(Class<T> serviceClass){
		try {
			return (T)getApplicationContext().getBean(serviceClass);
		}catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name){
		try {
			return (T)getApplicationContext().getBean(name);
		}catch (Exception e) {
			return null;
		}
	}
	
}
