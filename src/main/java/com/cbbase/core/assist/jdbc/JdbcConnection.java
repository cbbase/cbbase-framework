package com.cbbase.core.assist.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.druid.pool.DruidDataSource;

import com.cbbase.core.tools.PropertiesHelper;

/**
 * @author changbo
 * 
 */
public class JdbcConnection {

	private static String configFile = "application.properties";
	
    private static Map<String, DruidDataSource> dataSourceMap = new HashMap<String, DruidDataSource>();
    
    public static void setConfigFile(String configFile){
    	JdbcConnection.configFile = configFile;
    }
    
    public static DruidDataSource getDataSource(String database){
    	if(database == null || database.length() == 0){
    		database = "spring.datasource";
    	}
    	DruidDataSource dataSource = dataSourceMap.get(database);
    	if(dataSource != null){
        	return dataSource;
    	}
		PropertiesHelper helper = PropertiesHelper.getPropertiesHelper(configFile);
		String driverClassName = helper.getValue(database+".driver-class-name");
		String url = helper.getValue(database+".url");
		String username = helper.getValue(database+".username");
		String password = helper.getValue(database+".password");
		return getDataSource(database, driverClassName, url, username, password);
    }
    
    public static DruidDataSource getDataSource(String database, String driverClassName, String url, String username, String password){
    	DruidDataSource dataSource = dataSourceMap.get(database);
    	if(dataSource == null){
            dataSource = new DruidDataSource();
            dataSource.setDriverClassName(driverClassName);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            dataSource.setInitialSize(2);
            dataSource.setMinIdle(2);
            dataSource.setMaxActive(50);
            dataSource.setTestWhileIdle(true);
            if(driverClassName.toUpperCase().indexOf(DatabaseType.MYSQL.name()) >= 0){
                dataSource.setValidationQuery("select 1");
            }
            if(driverClassName.toUpperCase().indexOf(DatabaseType.ORACLE.name()) >= 0){
                dataSource.setValidationQuery("select 1 from dual");
                dataSource.setOracle(true);
            }
            dataSourceMap.put(database, dataSource);
    	}
    	return dataSource;
    }
    
	public static Connection getConnection(){
		return getConnection(null);
	}
	
	public static Connection getConnection(String database){
		Connection conn = null;
		try {
			conn = getDataSource(database).getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}

	public static boolean isMysql(String database){
		return checkType(database, DatabaseType.MYSQL);
	}
	
	public static boolean isOracle(String database){
		return checkType(database, DatabaseType.ORACLE);
	}
	
	public static boolean checkType(String database, DatabaseType type) {
		return getDataSource(database).getDriverClassName().toUpperCase().indexOf(type.name()) >= 0;
	}
	
	public static String getDatabaseUser(String database){
		return getDataSource(database).getUsername();
	}
	
	public static void closeConnection(Connection conn){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
