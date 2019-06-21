package com.cbbase.core.assist.database;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.cbbase.core.tools.StringUtil;


public class DataTypeUtil {
	
	private static Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();
	private static Map<String, String> mybatisTypeMap = new HashMap<String, String>();
	
	static{
		typeMap.put("CHAR", String.class);
		typeMap.put("VARCHAR", String.class);
		typeMap.put("VARCHAR2", String.class);
		typeMap.put("TEXT", String.class);
		typeMap.put("MEDIUMTEXT", String.class);
		typeMap.put("CLOB", String.class);
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

		mybatisTypeMap.put("CHAR", "VARCHAR");
		mybatisTypeMap.put("VARCHAR", "VARCHAR");
		mybatisTypeMap.put("VARCHAR2", "VARCHAR");
		mybatisTypeMap.put("TEXT", "VARCHAR");
		mybatisTypeMap.put("MEDIUMTEXT", "VARCHAR");
		mybatisTypeMap.put("CLOB", "VARCHAR");
		mybatisTypeMap.put("INT", "INTEGER");
		mybatisTypeMap.put("TINYINT", "DECIMAL");
		mybatisTypeMap.put("BIGINT", "BIGINT");
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
		if(columnType.indexOf("(") >= 0) {
			columnType = columnType.substring(0, columnType.indexOf("("));
		}
		
		if(StringUtil.toInt(StringUtil.getValue(scale)) > 0
				&& columnType.toUpperCase().indexOf("TIME") < 0) {
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
	
	public static boolean isStringType(String columnType, Object scale){
		String className = toJavaType(columnType, scale);
		if("String".equals(className)){
			return true;
		}
		return false;
	}
	
	public static String toJavaType(String columnType, Object scale){
		if(columnType.indexOf("(") >= 0) {
			columnType = columnType.substring(0, columnType.indexOf("("));
		}
		return toClass(columnType, scale).getSimpleName();
	}
	
	public static String toMybatisType(String columnType, Object scale){
		if(columnType.indexOf("(") >= 0) {
			columnType = columnType.substring(0, columnType.indexOf("("));
		}
		if(StringUtil.toInt(StringUtil.getValue(scale)) > 0
				&& columnType.toUpperCase().indexOf("TIME") < 0) {
			return "DECIMAL";
		}
		String type = mybatisTypeMap.get(columnType.toUpperCase());
		return type;
	}
	
	public static String toJavaFullType(String columnType, Object scale){
		return toClass(columnType, scale).getName();
	}

}
