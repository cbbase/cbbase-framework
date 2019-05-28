package com.cbbase.core.assist.code;

import com.cbbase.core.tools.FileUtil;

public class CodeDao extends CodeAssist {
	
	public static void create(){


		StringBuilder dao = new StringBuilder();
		dao.append("package "+package_name+".dao;").append("\r\n");
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


		String text = dao.toString();
		if(coverCore) {
			text = text.replaceAll("com.cbbase", basePackage);
		}
		if(showContent) {
			System.out.println("===============================");
			System.out.println(text);
		}
		
		if(writeFile) {
			String file_path = root_path + java_path + package_folder + "\\dao\\";
			FileUtil.createFileByString(file_path + entity_name +"Dao.java", text);
		}
		
	}

}
