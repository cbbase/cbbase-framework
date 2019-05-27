package com.cbbase.core.assist.code;

import com.cbbase.core.tools.FileUtil;

public class CodeController extends CodeAssist {
	
	public static void create(){

		/**
		 * action
		 */
		StringBuilder action = new StringBuilder();
		action.append("package "+packageName+".controller;").append("\r\n");
		action.append("\r\n");
		action.append("import java.util.List;");
		action.append("\r\n");
		action.append("import org.springframework.beans.factory.annotation.Autowired;").append("\r\n");
		action.append("import org.springframework.stereotype.Controller;").append("\r\n");
		action.append("import org.springframework.web.bind.annotation.RequestMapping;").append("\r\n");
		action.append("import org.springframework.web.bind.annotation.RequestParam;").append("\r\n");
		action.append("import org.springframework.web.bind.annotation.ResponseBody;").append("\r\n");
		action.append("\r\n");
		action.append("import "+packageName+".entity."+entity_name+";").append("\r\n");
		action.append("import "+packageName+".service."+entity_name+"Service;").append("\r\n");
		if(addAuth) {
			action.append("import com.cbbase.core.annotation.Authority;").append("\r\n");
		}
		action.append("import com.cbbase.core.base.BaseController;").append("\r\n");
		action.append("import com.cbbase.core.container.RestResponse;").append("\r\n");
		action.append("\r\n");
		action.append("@Controller").append("\r\n");
		action.append("@RequestMapping(\"/web/"+model_path+"\")").append("\r\n");
		action.append("public class "+entity_name+"Controller extends BaseController {").append("\r\n");
		action.append("\r\n");
		action.append("	@Autowired").append("\r\n");
		action.append("	private "+entity_name+"Service "+entity_var+"Service;").append("\r\n");
		action.append("\r\n");
		action.append("	@ResponseBody").append("\r\n");
		action.append("	@RequestMapping(\"selectPage\")").append("\r\n");
		if(addAuth) {
			action.append("	@Authority(\""+auth_name+".view\")").append("\r\n");
		}
		action.append("	public RestResponse selectPage("+entity_name+" param){").append("\r\n");
		action.append("		return getSuccess("+entity_var+"Service.selectPage(getPageContainer(param)));").append("\r\n");
		action.append("	}").append("\r\n");
		action.append("\r\n");
		action.append("	@ResponseBody").append("\r\n");
		action.append("	@RequestMapping(\"select\")").append("\r\n");
		if(addAuth) {
			action.append("	@Authority(\""+auth_name+".view\")").append("\r\n");
		}
		action.append("	public RestResponse select("+entity_name+" param){").append("\r\n");
		action.append("		return getSuccess("+entity_var+"Service.select(param));").append("\r\n");
		action.append("	}").append("\r\n");
		action.append("\r\n");
		action.append("	@ResponseBody").append("\r\n");
		action.append("	@RequestMapping(\"add\")").append("\r\n");
		if(addAuth) {
			action.append("	@Authority(\""+auth_name+".modify\")").append("\r\n");
		}
		action.append("	public RestResponse add("+entity_name+" param){").append("\r\n");
		action.append("		return getSuccess("+entity_var+"Service.insert(param));").append("\r\n");
		action.append("	}").append("\r\n");
		action.append("\r\n");
		action.append("	@ResponseBody").append("\r\n");
		action.append("	@RequestMapping(\"update\")").append("\r\n");
		if(addAuth) {
			action.append("	@Authority(\""+auth_name+".modify\")").append("\r\n");
		}
		action.append("	public RestResponse update("+entity_name+" param){").append("\r\n");
		action.append("		return getSuccess("+entity_var+"Service.update(param));").append("\r\n");
		action.append("	}").append("\r\n");
		action.append("\r\n");
		action.append("	@ResponseBody").append("\r\n");
		action.append("	@RequestMapping(\"delete\")").append("\r\n");
		if(addAuth) {
			action.append("	@Authority(\""+auth_name+".modify\")").append("\r\n");
		}
		action.append("	public RestResponse delete(@RequestParam(\"ids\") List<Long> ids){").append("\r\n");
		action.append("		return getSuccess("+entity_var+"Service.batchDelete(ids));").append("\r\n");
		action.append("	}").append("\r\n");
		action.append("}").append("\r\n");

		if(showContent) {
			System.out.println("===============================");
			System.out.println(action.toString());
		}
		
		if(writeFile) {
			String file_path = root_path + java_path + package_folder + "\\controller\\";
			FileUtil.createFileByString(file_path + entity_name +"Controller.java", action.toString());
		}
	}

}
