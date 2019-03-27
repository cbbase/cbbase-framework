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
	
	public static final String TYPE_ORACLE = "ORACLE";
	public static final String TYPE_MYSQL = "MYSQL";
	
	public static final String[] DB_TYPE = {"ORACLE", "MYSQL", "SQLSERVER", "DB2", "INFORMIX"};

	private static String CONFIG_FILE = "application.properties";
	
    private static Map<String, DruidDataSource> dataSourceMap = new HashMap<String, DruidDataSource>();
    
    public static void setConfigFile(String configFile){
    	CONFIG_FILE = configFile;
    }
    
    public static DruidDataSource getDataSource(String database){
    	if(database == null || database.length() == 0){
    		database = "jdbc";
    	}
    	DruidDataSource dataSource = dataSourceMap.get(database);
    	if(dataSource == null){
			PropertiesHelper helper = PropertiesHelper.getPropertiesHelper(CONFIG_FILE);
			String driverClass = helper.getValue(database+".driverClassName");
			String url = helper.getValue(database+".url");
			String username = helper.getValue(database+".username");
			String password = helper.getValue(database+".password");
    		
            dataSource = new DruidDataSource();
            dataSource.setDriverClassName(driverClass);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            dataSource.setInitialSize(3);
            dataSource.setMinIdle(3);
            dataSource.setMaxActive(20);
            dataSource.setTestWhileIdle(true);
            if(driverClass.toUpperCase().indexOf(TYPE_MYSQL) >= 0){
                dataSource.setValidationQuery("select 1");
            }
            if(driverClass.toUpperCase().indexOf(TYPE_ORACLE) >= 0){
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
	
	public static String getDatabaseType(String database){
		String driverClass = getDataSource(database).getDriverClassName();
		for(String type : DB_TYPE){
			if(driverClass.toUpperCase().indexOf(type) >= 0){
				return type;
			}
		}
		return "unkown";
	}

	public static boolean isMysql(String database){
		return getDatabaseType(database).equals(TYPE_MYSQL);
	}
	
	public static boolean isOracle(String database){
		return getDatabaseType(database).equals(TYPE_ORACLE);
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
