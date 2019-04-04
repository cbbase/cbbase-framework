package com.cbbase.core.assist.database;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import com.cbbase.core.assist.jdbc.JdbcHelper;
import com.cbbase.core.tools.CommonUtil;
import com.cbbase.core.tools.FileUtil;
import com.cbbase.core.tools.ObjectUtil;
import com.cbbase.core.tools.StringUtil;

/**
 * 
 * @author changbo
 *
 */
public class SqlScriptUtil {
	
	public static void createSqlByPkg(String sqlPath, String pkgName){
		
		String folderPath = CommonUtil.getProjectPath()+"/src/"+pkgName.replaceAll("\\.", "/");
		System.out.println("folderPath:"+folderPath);
		File folder = new File(folderPath);
		File[] files = folder.listFiles(new FilenameFilter(){
			
			public boolean accept(File dir, String name) {
				if(name.endsWith(".java")){
					return true;
				}
				return false;
			}
		});
		if(files == null) {
			return;
		}
		for(File file : files){
			String name = file.getName();
			String className = pkgName + "." + name.replace(".java", "");
			Class<?> clazz = null;
			try {
				clazz = Class.forName(className);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				continue;
			}
			String clazzName = clazz.getSimpleName();
			String tableName = "t_"+StringUtil.camelToColumn(clazzName);
			System.out.println(clazzName);
			createSqlByEntity(sqlPath, clazz, tableName, "MYSQL");
		}
	}
	
	public static void createSqlByEntity(String sqlPath, Class<?> clazz){
		String clazzName = clazz.getSimpleName();
		String tableName = "t_"+StringUtil.camelToColumn(clazzName);
		createSqlByEntity(sqlPath, clazz, tableName, "MYSQL");
	}

	public static void createSqlByEntity(String sqlPath, Class<?> clazz, String tableName){
		createSqlByEntity(sqlPath, clazz, tableName, "MYSQL");
	}
	
	public static void createSqlByEntity(String sqlPath, Class<?> clazz, String tableName, String datbaseType){
		List<String> list = ObjectUtil.getFields(clazz);
		String sql = "";
		if("MYSQL".equalsIgnoreCase(datbaseType)){
			sql = createSqlByEntityForMysql(list, clazz, tableName);
		}
		if("ORACLE".equalsIgnoreCase(datbaseType)){
			sql = createSqlByEntityForOracle(list, clazz, tableName);
		}
		FileUtil.createFileByString(sqlPath+"/"+tableName+".sql", sql);
	}
	
	private static String createSqlByEntityForMysql(List<String> list, Class<?> clazz, String tableName){
		StringBuilder sb = new StringBuilder("");
		sb.append("DROP TABLE IF EXISTS "+tableName.toLowerCase()+";").append("\n");
		sb.append("CREATE TABLE IF NOT EXISTS "+tableName.toLowerCase()+"(").append("\n");
		for(String field : list){
			if(field.equals("sqlStatement")){
				continue;
			}
			String d_type = DataTypeUtil.toDBType(ObjectUtil.getFieldClass(clazz, field), "MYSQL");
			sb.append("	"+StringUtil.camelToColumn(field)+" "+d_type+" COMMENT '',").append("\n");
		}
		sb.append("	PRIMARY KEY (id)").append("\n");
		sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='';").append("\n");
		
		return sb.toString();
	}
	
	private static String createSqlByEntityForOracle(List<String> list, Class<?> clazz, String tableName){
		StringBuilder sb = new StringBuilder("");
		sb.append("DROP TABLE "+tableName.toUpperCase()+";").append("\n");
		sb.append("CREATE TABLE "+tableName.toUpperCase()+"(").append("\n");
		for(String field : list){
			if(field.equals("sqlStatement")){
				continue;
			}
			String d_type = DataTypeUtil.toDBType(ObjectUtil.getFieldClass(clazz, field), "ORACLE");
			sb.append("	"+field+" "+d_type+",").append("\n");
		}
		sb.append("	PRIMARY KEY (id)").append("\n");
		sb.append(");").append("\n");
		
		return sb.toString();
	}
	

	public static void executeFile(String database, String fileName, String profix){
		executeFile(database, new File(fileName), profix);
	}

	public static void executeFile(String database, File file, String profix){
		if(file == null || !file.exists()){
			return;
		}
		if(file.isDirectory()){
			for(File f : file.listFiles()){
				executeFile(database, f, profix);
			}
		}else{
			String fileName = file.getName().toLowerCase();
			if(!fileName.startsWith(profix)){
				return;
			}
			List<String> list = FileUtil.readAllLines(fileName);
			String text = "";
			for(String line : list) {
				line = line.trim();
				if(line.length() > 0 && !line.startsWith("--")){
					text = text + line + "\r\n";
				}
			}
			System.out.println("[SqlScriptUtil]execute:" + fileName);
			if(fileName.startsWith("f_")){
				int index = text.indexOf(";");
				String sql = text.substring(0, index);
				JdbcHelper.getJdbcHelper(database).execute(sql);
				sql = text.substring(index+1, text.length());
				JdbcHelper.getJdbcHelper(database).execute(sql);
			}else{
				String[] sqls = text.split(";");
				for(String sql : sqls){
					JdbcHelper.getJdbcHelper(database).execute(sql);
				}
			}
		}
	}

}
