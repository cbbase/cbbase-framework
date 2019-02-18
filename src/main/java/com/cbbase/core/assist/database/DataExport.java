package com.cbbase.core.assist.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cbbase.core.jdbc.JdbcHelper;
import com.cbbase.core.tools.ExcelHelper;
import com.cbbase.core.tools.FileUtil;

/**
 * @author changbo
 * 
 */
public class DataExport {
	
	public static boolean toExcel(String fileName, String[] showColumns, List<Map<String, Object>> list){
		ExcelHelper excel = ExcelHelper.getExcelHelper(fileName, ExcelHelper.WRITE).open(0);
		
		Map<Integer, String> head = new HashMap<Integer, String>();
		for(int i=0; i<showColumns.length; i++){
			head.put(i, showColumns[i]);
		}
		excel.writeLine(head);
		System.out.println("[JdbcExport]list.size():"+list.size());
		for(Map<String, Object> map : list){
			Map<Integer, String> data = new HashMap<Integer, String>();
			for(int i=0; i<showColumns.length; i++){
				String key = showColumns[i];
				String val = (map.get(key)==null)?"":map.get(key).toString();
				data.put(i, val);
			}
			excel.writeLine(data);
		}
		excel.close();
		
		return true;
	}

	public static boolean toTxt(String fileName, String[] showColumns, List<Map<String, Object>> list){
		String head = "";
		for(String s : showColumns){
			head = head + s + "|";
		}
		FileUtil.appendLine(fileName, head);
		System.out.println("[JdbcExport]list.size():"+list.size());
		for(Map<String, Object> map : list){
			String data = "";
			for(int i=0; i<showColumns.length; i++){
				String key = showColumns[i];
				String val = (map.get(key)==null)?"":map.get(key).toString();
				data = data + val + "|";
			}
			FileUtil.appendLine(fileName, data);
		}
		return true;
	}
	
}
