package com.cbbase.core.assist.database;

import java.io.File;
import java.util.List;

import com.cbbase.core.assist.jdbc.JdbcHelper;
import com.cbbase.core.tools.FileUtil;

/**
 * 
 * @author changbo
 *
 */
public class SqlScriptUtil {
	
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
