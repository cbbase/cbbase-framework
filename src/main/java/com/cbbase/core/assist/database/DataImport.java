package com.cbbase.core.assist.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.cbbase.core.jdbc.JdbcHelper;
import com.cbbase.core.tools.ExcelHelper;
import com.cbbase.core.tools.FileUtil;
import com.cbbase.core.tools.KeyHelper;
import com.cbbase.core.tools.StringUtil;

public class DataImport {
	
	private String database;
	private String fileName;
	private int skipLine;
	private String tableName;
	private List<String> columnList;
	private int idType;
	private String txtDelimiter;
	private DataImportColumnHandler columnHandler;
	
	private String sql;
	
	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getSkipLine() {
		return skipLine;
	}

	public void setSkipLine(int skipLine) {
		this.skipLine = skipLine;
	}
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public List<String> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<String> columnList) {
		this.columnList = columnList;
	}

	public String getTxtDelimiter() {
		return txtDelimiter;
	}

	public void setTxtDelimiter(String txtDelimiter) {
		this.txtDelimiter = txtDelimiter;
	}
	
	public int getIdType() {
		return idType;
	}

	public void setIdType(int idType) {
		this.idType = idType;
	}
	
	public DataImportColumnHandler getColumnHandler() {
		return columnHandler;
	}

	public void setColumnHandler(DataImportColumnHandler columnHandler) {
		this.columnHandler = columnHandler;
	}

	public void execute(){
		if(StringUtil.isEmpty(fileName)){
			return ;
		}
		initSql();

		List<Map<Integer, String>> excelData = null;
		List<String> txtData = null;
		if(fileName.endsWith(".xls") || fileName.endsWith(".xlsx")){
			excelData = readExcel();
		}else if(fileName.endsWith(".txt")){
			txtData = readTxt();
		}
		
		//
		List<Map<String, Object>> tableColumn= new DataBaseTable(database).queryColumns(tableName);
		Map<String, Class<?>> columnType = new HashMap<String, Class<?>>();
		for(Map<String, Object> tempColumn : tableColumn){
			columnType.put((String)tempColumn.get("column_name"), DataTypeUtil.toClass((String)tempColumn.get("data_type"), tempColumn.get("data_scale")));
		}
		
		//
		List<List<Object>> data = new ArrayList<List<Object>>();
		int dataLength = 0;
		if(excelData != null){
			dataLength = excelData.size();
		}else if(txtData != null){
			dataLength = txtData.size();
		}
		for(int i=0; i<dataLength; i++){
			List<Object> objs = new ArrayList<Object>();
			if(idType == 1){
				objs.add(KeyHelper.nextKey());
			}
			for(int j=0; j<columnList.size(); j++){
				if(excelData != null){
					objs.add(columnHandler.formatColumn(excelData.get(i), columnList.get(j)));
				}else if(txtData != null){
					objs.add(columnHandler.formatColumn(txtData.get(i), columnList.get(j)));
				}
			}
			data.add(objs);
		}
		//
		insert(data);
		
	}
	
	public List<Map<Integer, String>> readExcel(){
		ExcelHelper helper = ExcelHelper.getExcelHelper(fileName, ExcelHelper.READ).open(0);
		helper.setSkipLine(skipLine);
		return helper.readAllLines();
	}
	
	public List<String> readTxt(){
		List<String> list = FileUtil.readAllLines(fileName);
		return list.subList(skipLine, list.size()-1);
	}
	
	
	public void insert(List<List<Object>> data){
		
		List<List<Object>> temp = new ArrayList<List<Object>>();
		int times = data.size()/100;
		if(data.size()%100 != 0){
			times++;
		}
		System.out.println("第一条数据:"+StringUtil.toString(data.get(0)));
		
		for(int i=0; i<times; i++){
			int toIndex = (i+1)*100;
			if(toIndex > data.size()){
				toIndex = data.size();
			}
			temp.addAll(data.subList(i*100, toIndex));
			System.out.println("insert:"+temp.size());
			JdbcHelper.getJdbcHelper(database).executeBatch(sql, temp);
			temp.clear();
		}
		
	}
	
	
	public void initSql(){
		StringBuilder sb = new StringBuilder("insert into " + tableName + " (");
		for(int i=0; i<columnList.size(); i++){
			if(i == 0){
				if(idType == 1){
					sb.append("id, ");
				}
				sb.append(columnList.get(i));
			}else{
				sb.append(", " + columnList.get(i));
			}
		}
		sb.append(") values (");
		for(int i=0; i<columnList.size(); i++){
			if(i == 0){
				if(idType == 1){
					sb.append("?, ");
				}
				sb.append("?");
			}else{
				sb.append(", ?");
			}
		}
		sb.append(")");
		sql = sb.toString();
		System.out.println(sql);
	}
	
	

}
