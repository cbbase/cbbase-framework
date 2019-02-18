package com.cbbase.core.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态设置数据源
 * @author changbo
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	
	private static final ThreadLocal<String> DATA_SOURCE = new ThreadLocal<String>();

	public static String getDataSource() {
		return (String) DATA_SOURCE.get();
	}

	public static void setDataSource(String dataSource) {
		DATA_SOURCE.set(dataSource);
	}

	public static void clearDataSource() {
		DATA_SOURCE.remove();
	}

	protected Object determineCurrentLookupKey() {
		return getDataSource();
	}
	
	@Override
	public void afterPropertiesSet() {
		
		super.afterPropertiesSet();
	}
}
