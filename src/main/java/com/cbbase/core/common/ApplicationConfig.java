package com.cbbase.core.common;

import org.springframework.core.env.Environment;
import com.cbbase.core.common.ServiceFactory;
import com.cbbase.core.tools.StringUtil;

/**
 * 读取配置文件中的配置
 * @author changbo
 *
 */
public class ApplicationConfig {
	
	private static Environment getConfig() {
		return ServiceFactory.getBean(Environment.class);
	}

	public static String getParam(String code, String defaultValue){
		return getConfig().getProperty(code, defaultValue);
	}

	public static String getParam(String code){
		return getParam(code, null);
	}
	
	public static boolean getBoolean(String key){
		return StringUtil.toBoolean(getParam(key));
	}
	
	public static int getInt(String key){
		return StringUtil.toInt(getParam(key));
	}
	
	public static long getLong(String key){
		return StringUtil.toLong(getParam(key));
	}
}
