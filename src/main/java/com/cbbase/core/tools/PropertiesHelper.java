package com.cbbase.core.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author changbo
 * 
 */
public class PropertiesHelper {
	
	private String fileName;
	private Properties properties;
	
	private PropertiesHelper(String fileName){
		this.fileName = fileName;
		this.load();
	}
	
	public static PropertiesHelper getPropertiesHelper(String fileName){
		return new PropertiesHelper(fileName);
	}
	
	public synchronized void load(){
		properties = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(PropertiesHelper.class.getClassLoader().getResource(fileName).getFile());
			properties.load(new InputStreamReader(is, "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} finally {
			StreamUtil.close(is);
		}
	}
	
	public String getValue(String key){
		return getValue(key, null);
	}
	
	public String getValue(String key, String defaultValue){
		return (properties.getProperty(key)==null) ? defaultValue : properties.getProperty(key);
	}

	public boolean getValueAsBoolean(String key){
		return StringUtil.toBoolean(getValue(key));
	}
	public int getValueAsInt(String key){
		return StringUtil.toInt(getValue(key));
	}
	public long getValueAsLong(String key){
		return StringUtil.toLong(getValue(key));
	}
	
}
