package com.cbbase.core.assist.database;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.cbbase.core.tools.ExcelHelper;
import com.cbbase.core.tools.StringUtil;

/**
 * 
 * @author changbo
 *
 */
public class DataBaseDictionary {
	
	private String outputPath = null;
	private DataBaseTable dataBaseTable = null;
	private String tablePrefix;
	
	public DataBaseDictionary(String database, String outputPath){
		this.outputPath = outputPath;
		dataBaseTable = new DataBaseTable(database);
	}
	
	public DataBaseDictionary(String database, String outputPath, String tablePrefix){
		this.outputPath = outputPath;
		this.tablePrefix = tablePrefix;
		dataBaseTable = new DataBaseTable(database);
	}
	
	public void writeDict(String tableName){
		File path = new File(outputPath);
		path.mkdirs();
		ExcelHelper helper = ExcelHelper.getExcelHelper(outputPath + tableName + ".xls", ExcelHelper.WRITE);
		helper.open(0);
		List<Map<String, Object>> list = dataBaseTable.queryColumns(tableName);
		System.out.println("queryColumns:" + list.size());
		helper.setColumnView(new int[]{20,12,10,10,10,50});
		String[] title = new String[]{"列名","类型","长度","主键标志","允许为空","注释"};
		helper.writeLine(title);
		for(int i=0; i<list.size(); i++){
			Map<String, Object> map = list.get(i);
			String[] strs = new String[6];
			strs[0] = StringUtil.getValue(map.get("column_name"));
			strs[1] = StringUtil.getValue(map.get("data_type"));
			strs[2] = StringUtil.getValue(map.get("data_length"));
			strs[3] = StringUtil.getValue(map.get("is_primary"));
			strs[4] = StringUtil.getValue(map.get("nullable"));
			strs[5] = StringUtil.getValue(map.get("comments"));
			helper.writeLine(strs);
		}
		helper.close();
	}
	
	public void writeAll(){
		List<Map<String, Object>> list = dataBaseTable.queryTables();
		System.out.println("queryTables:" + list.size());
		for(int i=0; i<list.size(); i++){
			Map<String, Object> map = list.get(i);
			String tableName = StringUtil.getValue(map.get("table_name"));
			//过滤掉不需要的
			if(StringUtil.isEmpty(tablePrefix) || tableName.startsWith(tablePrefix)){
				System.out.println(tableName);
				writeDict(tableName);
			}
		}
		
	}

}
