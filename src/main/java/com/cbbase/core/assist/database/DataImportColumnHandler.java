package com.cbbase.core.assist.database;

import java.sql.Date;
import java.util.Map;

import com.cbbase.core.tools.DateUtil;
import com.cbbase.core.tools.StringUtil;

public abstract class DataImportColumnHandler {
	
	public abstract Object formatColumn(Map<Integer, String> line, String columnName);
	public abstract Object formatColumn(String line, String columnName);
	
	public Object formatData(String data, Class<?> clazz){
		if(clazz == String.class){
			return data;
		}
		if(clazz == Integer.class){
			return StringUtil.toInt(data);
		}
		if(clazz == Long.class){
			return StringUtil.toLong(data);
		}
		if(clazz == Double.class){
			return StringUtil.toDouble(data);
		}
		if(clazz == Date.class){
			return DateUtil.dateOrTime(data);
		}
		return data;
	}
}
