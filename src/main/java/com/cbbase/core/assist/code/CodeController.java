package com.cbbase.core.assist.code;

import com.cbbase.core.tools.FileUtil;

public class CodeController extends CodeAssist {
	
	public static void create(){

		/**
		 * controller
		 */
		StringBuilder controller = new StringBuilder();
		controller.append("package "+package_name+".controller;").append("\r\n");
		controller.append("\r\n");
		controller.append("import java.util.List;");
		controller.append("\r\n");
		controller.append("import org.springframework.beans.factory.annotation.Autowired;").append("\r\n");
		controller.append("import org.springframework.stereotype.Controller;").append("\r\n");
		controller.append("import org.springframework.web.bind.annotation.RequestMapping;").append("\r\n");
		controller.append("import org.springframework.web.bind.annotation.RequestParam;").append("\r\n");
		controller.append("import org.springframework.web.bind.annotation.ResponseBody;").append("\r\n");
		controller.append("\r\n");
		controller.append("import "+package_name+".entity."+entity_name+";").append("\r\n");
		controller.append("import "+package_name+".service."+entity_name+"Service;").append("\r\n");
		if(addAuth) {
			controller.append("import com.cbbase.core.annotation.Authority;").append("\r\n");
		}
		controller.append("import com.cbbase.core.base.BaseController;").append("\r\n");
		controller.append("import com.cbbase.core.container.RestResponse;").append("\r\n");
		controller.append("\r\n");
		controller.append("@Controller").append("\r\n");
		controller.append("@RequestMapping(\"/web/"+entity_var+"\")").append("\r\n");
		controller.append("public class "+entity_name+"Controller extends BaseController {").append("\r\n");
		controller.append("\r\n");
		controller.append("	@Autowired").append("\r\n");
		controller.append("	private "+entity_name+"Service "+entity_var+"Service;").append("\r\n");
		controller.append("\r\n");
		controller.append("	@ResponseBody").append("\r\n");
		controller.append("	@RequestMapping(\"selectPage\")").append("\r\n");
		if(addAuth) {
			controller.append("	@Authority(\""+auth_name+".view\")").append("\r\n");
		}
		controller.append("	public RestResponse selectPage("+entity_name+" param){").append("\r\n");
		controller.append("		return getSuccess("+entity_var+"Service.selectPage(getPageContainer(param)));").append("\r\n");
		controller.append("	}").append("\r\n");
		controller.append("\r\n");
		controller.append("	@ResponseBody").append("\r\n");
		controller.append("	@RequestMapping(\"select\")").append("\r\n");
		if(addAuth) {
			controller.append("	@Authority(\""+auth_name+".view\")").append("\r\n");
		}
		controller.append("	public RestResponse select("+entity_name+" param){").append("\r\n");
		controller.append("		return getSuccess("+entity_var+"Service.select(param));").append("\r\n");
		controller.append("	}").append("\r\n");
		controller.append("\r\n");
		controller.append("	@ResponseBody").append("\r\n");
		controller.append("	@RequestMapping(\"add\")").append("\r\n");
		if(addAuth) {
			controller.append("	@Authority(\""+auth_name+".modify\")").append("\r\n");
		}
		controller.append("	public RestResponse add("+entity_name+" param){").append("\r\n");
		controller.append("		return getSuccess("+entity_var+"Service.insert(param));").append("\r\n");
		controller.append("	}").append("\r\n");
		controller.append("\r\n");
		controller.append("	@ResponseBody").append("\r\n");
		controller.append("	@RequestMapping(\"update\")").append("\r\n");
		if(addAuth) {
			controller.append("	@Authority(\""+auth_name+".modify\")").append("\r\n");
		}
		controller.append("	public RestResponse update("+entity_name+" param){").append("\r\n");
		controller.append("		return getSuccess("+entity_var+"Service.update(param));").append("\r\n");
		controller.append("	}").append("\r\n");
		controller.append("\r\n");
		controller.append("	@ResponseBody").append("\r\n");
		controller.append("	@RequestMapping(\"delete\")").append("\r\n");
		if(addAuth) {
			controller.append("	@Authority(\""+auth_name+".modify\")").append("\r\n");
		}
		controller.append("	public RestResponse delete(@RequestParam(\"ids\") List<Long> ids){").append("\r\n");
		controller.append("		return getSuccess("+entity_var+"Service.batchDelete(ids));").append("\r\n");
		controller.append("	}").append("\r\n");
		controller.append("}").append("\r\n");
		
		String text = controller.toString();
		if(coverCore) {
			text = text.replaceAll("com.cbbase", basePackage);
		}
		
		if(showContent) {
			System.out.println("===============================");
			System.out.println(text);
		}
		
		if(writeFile) {
			String file_path = root_path + java_path + package_folder + "\\controller\\";
			FileUtil.createFileByString(file_path + entity_name +"Controller.java", text);
		}
	}

}
