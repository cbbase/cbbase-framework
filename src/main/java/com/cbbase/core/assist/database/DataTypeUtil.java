package com.cbbase.core.assist.database;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.cbbase.core.tools.StringUtil;


public class DataTypeUtil {
	
	private static Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();
	private static Map<Class<?>, String> typeMapMysql = new HashMap<Class<?>, String>();
	private static Map<Class<?>, String> typeMapOracle = new HashMap<Class<?>, String>();
	private static Map<String, String> mybatisTypeMap = new HashMap<String, String>();
	
	static{
		typeMap.put("CHAR", String.class);
		typeMap.put("VARCHAR", String.class);
		typeMap.put("VARCHAR2", String.class);
		typeMap.put("TEXT", String.class);
		typeMap.put("MEDIUMTEXT", String.class);
		typeMap.put("INT", Integer.class);
		typeMap.put("TINYINT", Integer.class);
		typeMap.put("BIGINT", Long.class);
		typeMap.put("NUMBER", Long.class);
		typeMap.put("DECIMAL", BigDecimal.class);
		typeMap.put("DATETIME", Date.class);
		typeMap.put("DATE", Date.class);
		typeMap.put("TIMESTAMP", Date.class);
		typeMap.put("TINYBLOB", byte[].class);
		typeMap.put("BLOB", byte[].class);
		typeMap.put("MEDIUMBLOB", byte[].class);
		
		typeMapMysql.put(String.class, "VARCHAR(32)");
		typeMapMysql.put(Long.class, "BIGINT");
		typeMapMysql.put(Integer.class, "INT");
		typeMapMysql.put(Double.class, "DECIMAL(18,6)");
		typeMapMysql.put(Date.class, "DATETIME");
		typeMapMysql.put(long.class, "BIGINT");
		typeMapMysql.put(int.class, "INT");
		typeMapMysql.put(double.class, "DECIMAL(18,6)");
		typeMapMysql.put(BigDecimal.class, "DECIMAL(18,6)");
		
		typeMapOracle.put(String.class, "VARCHAR2(32)");
		typeMapOracle.put(Long.class, "NUMBER");
		typeMapOracle.put(Integer.class, "INT");
		typeMapOracle.put(Double.class, "DECIMAL(18,6)");
		typeMapOracle.put(Date.class, "DATETIME");
		typeMapOracle.put(long.class, "NUMBER");
		typeMapOracle.put(int.class, "INT");
		typeMapOracle.put(double.class, "DECIMAL(18,6)");
		typeMapOracle.put(byte[].class, "BLOB");

		mybatisTypeMap.put("CHAR", "VARCHAR");
		mybatisTypeMap.put("VARCHAR", "VARCHAR");
		mybatisTypeMap.put("VARCHAR2", "VARCHAR");
		mybatisTypeMap.put("TEXT", "VARCHAR");
		mybatisTypeMap.put("MEDIUMTEXT", "VARCHAR");
		mybatisTypeMap.put("INT", "DECIMAL");
		mybatisTypeMap.put("TINYINT", "DECIMAL");
		mybatisTypeMap.put("BIGINT", "DECIMAL");
		mybatisTypeMap.put("NUMBER", "DECIMAL");
		mybatisTypeMap.put("DECIMAL", "DECIMAL");
		mybatisTypeMap.put("DATETIME", "TIMESTAMP");
		mybatisTypeMap.put("DATE", "TIMESTAMP");
		mybatisTypeMap.put("TIMESTAMP", "TIMESTAMP");
		mybatisTypeMap.put("TINYBLOB", "BLOB");
		mybatisTypeMap.put("BLOB", "BLOB");
		mybatisTypeMap.put("MEDIUMBLOB", "BLOB");
	}
	
	public static Class<?> toClass(String columnType, Object scale){
		if(StringUtil.hasValue(scale)) {
			return BigDecimal.class;
		}
		Class<?> clazz = typeMap.get(columnType.toUpperCase());
		if(clazz == null){
			return String.class;
		}
		return clazz;
	}
	
	public static boolean isNumberType(String columnType){
		String className = toJavaType(columnType, null);
		if(StringUtil.whereIn(className, "Long", "Integer", "Double")){
			return true;
		}
		return false;
	}
	
	public static String toJavaType(String columnType, Object scale){
		return toClass(columnType, scale).getSimpleName();
	}
	
	public static String toMybatisType(String columnType){
		return mybatisTypeMap.get(columnType.toUpperCase());
	}
	
	public static String toJavaFullType(String columnType, Object scale){
		return toClass(columnType, scale).getName();
	}
	
	public static String toDBType(Class<?> clazz){
		return toDBType(clazz, "MYSQL");
	}

	public static String toDBType(Class<?> clazz, String databaseType){
		if("MYSQL".equalsIgnoreCase(databaseType)){
			return typeMapMysql.get(clazz);
		}
		if("ORACLE".equalsIgnoreCase(databaseType)){
			return typeMapOracle.get(clazz);
		}
		return null;
	}

}
