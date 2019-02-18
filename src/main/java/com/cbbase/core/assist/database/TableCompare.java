package com.cbbase.core.assist.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cbbase.core.tools.StringUtil;

public class TableCompare {

	public static void compare(String database1, String database2){
		compare(database1, database2, null);
	}
	
	public static void compare(String database1, String database2, String tablePrefix){
		DataBaseTable dbt1 = new DataBaseTable(database1);
		DataBaseTable dbt2 = new DataBaseTable(database2);

		List<Map<String,Object>> tableList1 = dbt1.queryTables();
		List<Map<String,Object>> tableList2 = dbt2.queryTables();

		List<String> tableNameList1 = new ArrayList<String>();
		List<String> tableNameList2 = new ArrayList<String>();
		for(Map<String,Object> tableMap : tableList1){
			String tableName = (String)tableMap.get("table_name");
			if(StringUtil.isEmpty(tablePrefix) || tableName.startsWith(tablePrefix)){
				tableNameList1.add(tableName);
			}
		}
		for(Map<String,Object> tableMap : tableList2){
			String tableName = (String)tableMap.get("table_name");
			if(StringUtil.isEmpty(tablePrefix) || tableName.startsWith(tablePrefix)){
				tableNameList2.add(tableName);
			}
		}
		
		List<String> moreTableList1 = new ArrayList<String>(tableNameList1);
		List<String> moreTableList2 = new ArrayList<String>(tableNameList2);
		moreTableList1.removeAll(tableNameList2);
		moreTableList2.removeAll(tableNameList1);
		
		System.out.println("===========================");
		showMoreList(database1, moreTableList1);
		showMoreList(database2, moreTableList2);
		System.out.println("===========================");
		tableNameList1.removeAll(moreTableList1);
		
		for(String tableName : tableNameList1){
			List<Map<String,Object>> colList1 = dbt1.queryColumns(tableName);
			List<Map<String,Object>> colList2 = dbt2.queryColumns(tableName);

			List<String> colNameList1 = new ArrayList<String>();
			List<String> colNameList2 = new ArrayList<String>();
			for(Map<String,Object> colMap : colList1){
				String colName = (String)colMap.get("column_name");
				colNameList1.add(colName);
			}
			for(Map<String,Object> colMap : colList2){
				String colName = (String)colMap.get("column_name");
				colNameList2.add(colName);
			}
			List<String> moreColList1 = new ArrayList<String>(colNameList1);
			List<String> moreColList2 = new ArrayList<String>(colNameList2);
			moreColList1.removeAll(colNameList2);
			moreColList2.removeAll(colNameList1);
			
			showMoreList(database1, tableName, moreColList1);
			showMoreList(database2, tableName, moreColList2);
		}
		
	}
	
	private static void showMoreList(String key, List<String> moreList){
		if(moreList != null && moreList.size() > 0){
			System.out.println(key+":"+moreList);
		}
	}
	private static void showMoreList(String key, String name, List<String> moreList){
		if(moreList != null && moreList.size() > 0){
			System.out.println(key+"["+name+"]:"+moreList);
		}
	}

}
