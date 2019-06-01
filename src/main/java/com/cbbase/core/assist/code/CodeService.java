package com.cbbase.core.assist.code;

import com.cbbase.core.tools.FileUtil;

public class CodeService extends CodeAssist {

	public static void create(){

		StringBuilder service = new StringBuilder("");
		service.append("package "+packageName+".service;").append("\r\n");
		service.append("\r\n");
		service.append("import org.springframework.stereotype.Service;").append("\r\n");
		service.append("").append("\r\n");
		service.append("import "+packageName+".dao."+entityName+"Dao;").append("\r\n");
		service.append("import com.cbbase.core.base.BaseService;").append("\r\n");
		service.append("").append("\r\n");
		service.append("@Service").append("\r\n");
		service.append("public class "+entityName+"Service extends BaseService<"+entityName+"Dao> {").append("\r\n");
		service.append("").append("\r\n");
		service.append("}").append("\r\n");
		service.append("").append("\r\n");

		
		String text = service.toString();
		if(coverCore) {
			text = text.replaceAll("com.cbbase", basePackage);
		}
		if(showContent) {
			System.out.println("===============================");
			System.out.println(text);
		}
		
		if(writeFile) {
			String filePath = rootPath + javaPath + packageFolder + "\\service\\";
			FileUtil.createFileByString(filePath + entityName +"Service.java", text);
		}
		
	}
}
