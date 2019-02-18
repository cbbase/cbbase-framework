package com.cbbase.core.assist.database;

import java.util.List;
import java.util.Map;

import com.cbbase.core.jdbc.JdbcConnection;
import com.cbbase.core.jdbc.JdbcHelper;
import com.cbbase.core.tools.StringUtil;
 

/**
 * 
 * @author changbo
 *
 */
public class DataBaseTable{
	
	private String database = "";
	
	public DataBaseTable(String database){
		this.database = database;
	}
	
	public List<Map<String, Object>> queryColumns(String tableName){
		return queryColumns(tableName, null);
	}
	
	public List<Map<String, Object>> queryColumns(String tableName, String tableSchema){
		String sql = "";
		if(JdbcConnection.isOracle(database)){
			sql = "select col.column_name, col.data_type, col.data_length, col.data_scale, decode(col.column_name, 'ID', 'Y', 'N') is_primary, col.nullable, com.comments  ";
			sql += "from user_tab_columns col ";
			sql += "left join user_col_comments com ";
			sql += "on com.table_name = col.table_name and com.column_name = col.column_name ";
			sql += "where col.table_name='" + tableName +"' ";
		}else if(JdbcConnection.isMysql(database)){
			sql = "select column_name, data_type, character_maximum_length data_length, numeric_scale data_scale, if(column_key = 'PRI', 'Y', 'N') is_primary, if(is_nullable = 'YES', 'Y', 'N') nullable, column_comment comments  "
				+ "from information_schema.COLUMNS where table_name = '" + tableName +"'";
			if(StringUtil.hasValue(tableSchema)) {
				sql += " AND table_schema = '"+tableSchema+"'";
			}
		}
		List<Map<String, Object>> list = JdbcHelper.getJdbcHelper(database).query(sql);
		
		return list;
	}
	
	
	public List<Map<String, Object>> queryIndex(String tableName){
		String sql = "";
		if(JdbcConnection.isOracle(database)){
			sql = "select * from user_indexes where table_name='"+tableName+"';";
		}else if(JdbcConnection.isMysql(database)){
			sql = "show index from " + tableName;
		}
		List<Map<String, Object>> list = JdbcHelper.getJdbcHelper(database).query(sql);
		
		return list;
	}
 

	public List<Map<String, Object>> queryTables(){
		String sql = "";
		if(JdbcConnection.isOracle(database)){
			sql = "select table_name from user_tables ";
		}else if(JdbcConnection.isMysql(database)){
			sql = "select table_name, table_comment from information_schema.tables ";
		}
		List<Map<String, Object>> list = JdbcHelper.getJdbcHelper(database).query(sql);
		
		return list;
	}
	
	public String getTableComment(String tableName) {
		List<Map<String, Object>> list = queryTables();
		for(Map<String, Object> map : list) {
			if(StringUtil.isEqualIgnoreCase(tableName, StringUtil.getValue(map.get("table_name")))) {
				if(map.get("table_comment") == null) {
					return null;
				}
				return map.get("table_comment").toString();
			}
		}
		return null;
	}
}
