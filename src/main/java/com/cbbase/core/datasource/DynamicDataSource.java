package com.cbbase.core.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态设置数据源
 * 
 * @author changbo
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	private static final ThreadLocal<String> dataDource = new ThreadLocal<String>();
	
	public static String getDataSource() {
		return (String) dataDource.get();
	}

	public static void setDataSource(String dataSource) {
		dataDource.set(dataSource);
	}

	public static void clearDataSource() {
		dataDource.remove();
	}
	
	protected Object determineCurrentLookupKey() {
		return getDataSource();
	}

}
