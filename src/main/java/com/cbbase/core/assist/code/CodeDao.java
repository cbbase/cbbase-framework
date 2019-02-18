package com.cbbase.core.assist.code;

import com.cbbase.core.tools.FileUtil;

public class CodeDao extends CodeAssist {
	
	public static void create(){


		StringBuilder dao = new StringBuilder();
		dao.append("package "+packageName+".dao;").append("\r\n");
		dao.append("\r\n");
		dao.append("import org.springframework.stereotype.Repository;").append("\r\n");
		dao.append("\r\n");
		dao.append("import com.cbbase.core.base.BaseDao;").append("\r\n");
		dao.append("\r\n");
		dao.append("@Repository").append("\r\n");
		dao.append("public interface "+entity_name+"Dao extends BaseDao {").append("\r\n");
		dao.append("\r\n");
		dao.append("}").append("\r\n");
		dao.append("").append("\r\n");

		
		if(showContent) {
			System.out.println("===============================");
			System.out.println(dao.toString());
		}
		
		if(writeFile) {
			String file_path = root_path + java_path + package_folder + "\\dao\\";
			FileUtil.createFileByString(file_path + entity_name +"Dao.java", dao.toString());
		}
		
	}

}
