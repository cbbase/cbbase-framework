package com.cbbase.core.assist.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.cbbase.core.jdbc.JdbcHelper;
import com.cbbase.core.tools.FileUtil;
import com.cbbase.core.tools.StringUtil;

public class DataBackup {
	
	public static String file = null;
	public static String database = null;
	
	public static void clear(){
		if(StringUtil.isEmpty(file)){
			return;
		}
		try {
			Files.delete(Paths.get(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void backup(String table){
		backup(table, null, null);
	}
	
	public static void backup(String table, String key){
		backup(table, key, "id");
	}
	
	public static void backup(String table, String key, String column){
		String select = "select * from "+table+" where "+column+" = '"+key+"'";
		if(column == null || key == null){
			select = "select * from "+table;
		}
		
		List<Map<String, Object>> list = JdbcHelper.getJdbcHelper(database).query(select);
		if(list.size() == 0){
			System.out.println("[DataBackup]data not find:"+key);
			return;
		}
		for(Map<String, Object> data : list){
			String tempKey = key;
			if(column == null || key == null){
				column = "id";
				tempKey = (String)data.get("id");
			}
			writeFile(data, table, tempKey, column);
		}
		if(column == null || key == null){
			FileUtil.appendLine(file);
		}
	}
	
	public static void writeFile(Map<String, Object> data, String table, String key, String column){
		StringBuilder sb = new StringBuilder("INSERT INTO "+table+"(");
		for(Entry<String, Object> entry : data.entrySet()){
			sb.append(entry.getKey()+", ");
		}
		sb.setLength(sb.length() - 2);
		sb.append(") \r\nVALUES (");
		for(Entry<String, Object> entry : data.entrySet()){
			if(entry.getValue() instanceof String){
				sb.append("'"+entry.getValue()+"', ");
			}else{
				sb.append(entry.getValue()+", ");
			}
		}
		sb.setLength(sb.length() - 2);
		sb.append(");");
		
		String delete = "DELETE FROM "+table+" WHERE "+column+" = '"+key+"';";
		String insert = sb.toString();
		System.out.println(delete);
		System.out.println(insert);
		if(StringUtil.hasValue(file)){
			FileUtil.appendLine(file, delete);
			FileUtil.appendLine(file, insert);
		}
	}
}
