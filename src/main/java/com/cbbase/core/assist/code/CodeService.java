package com.cbbase.core.assist.code;

import com.cbbase.core.tools.FileUtil;

public class CodeService extends CodeAssist {

	public static void create(){

		StringBuilder service = new StringBuilder("");
		service.append("package "+packageName+".service;").append("\r\n");
		service.append("\r\n");
		service.append("import org.springframework.stereotype.Service;").append("\r\n");
		service.append("").append("\r\n");
		service.append("import "+packageName+".dao."+entity_name+"Dao;").append("\r\n");
		service.append("import com.cbbase.core.base.BaseService;").append("\r\n");
		service.append("").append("\r\n");
		service.append("@Service").append("\r\n");
		service.append("public class "+entity_name+"Service extends BaseService<"+entity_name+"Dao> {").append("\r\n");
		service.append("").append("\r\n");
		service.append("}").append("\r\n");
		service.append("").append("\r\n");

		if(showContent) {
			System.out.println("===============================");
			System.out.println(service.toString());
		}
		
		if(writeFile) {
			String file_path = root_path + java_path + package_folder + "\\service\\";
			FileUtil.createFileByString(file_path + entity_name +"Service.java", service.toString());
		}
		
	}
}