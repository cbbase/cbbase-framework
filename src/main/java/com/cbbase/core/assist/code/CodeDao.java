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
		dao.append("public interface "+entityName+"Dao extends BaseDao {").append("\r\n");
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
			String filePath = rootPath + javaPath + packageFolder + "\\dao\\";
			FileUtil.createFileByString(filePath + entityName +"Dao.java", text);
		}
		
	}

}
