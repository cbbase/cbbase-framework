package com.cbbase.core.assist.code;

import com.cbbase.core.tools.FileUtil;

public class CodeController extends CodeAssist {
	
	public static void create(){

		/**
		 * controller
		 */
		StringBuilder controller = new StringBuilder();
		controller.append("package "+packageName+".controller;").append("\r\n");
		controller.append("\r\n");
		controller.append("import java.util.List;");
		controller.append("\r\n");
		controller.append("import org.springframework.beans.factory.annotation.Autowired;").append("\r\n");
		controller.append("import org.springframework.stereotype.Controller;").append("\r\n");
		controller.append("import org.springframework.ui.Model;").append("\r\n");
		controller.append("import org.springframework.web.bind.annotation.RequestMapping;").append("\r\n");
		controller.append("import org.springframework.web.bind.annotation.RequestParam;").append("\r\n");
		controller.append("import org.springframework.web.bind.annotation.ResponseBody;").append("\r\n");
		controller.append("\r\n");
		controller.append("import "+packageName+".entity."+entityName+";").append("\r\n");
		controller.append("import "+packageName+".service."+entityName+"Service;").append("\r\n");
		if(addAuth) {
			controller.append("import com.cbbase.core.annotation.Authority;").append("\r\n");
		}
		controller.append("import com.cbbase.core.base.BaseController;").append("\r\n");
		controller.append("import com.cbbase.core.container.PageContainer;").append("\r\n");
		controller.append("import com.cbbase.core.container.RestResponse;").append("\r\n");
		controller.append("\r\n");
		controller.append("@Controller").append("\r\n");
		controller.append("@RequestMapping(\"/web/"+entityVar+"\")").append("\r\n");
		controller.append("public class "+entityName+"Controller extends BaseController {").append("\r\n");
		controller.append("\r\n");
		controller.append("	@Autowired").append("\r\n");
		controller.append("	private "+entityName+"Service "+entityVar+"Service;").append("\r\n");
		controller.append("\r\n");
		//index
		if(jspUrl) {
			controller.append("	@RequestMapping(\"index\")").append("\r\n");
			if(addAuth) {
				controller.append("	@Authority(\""+authName+".view\")").append("\r\n");
			}
			controller.append("	public String index(Model model){").append("\r\n");
			controller.append("		return \""+modelName+"/"+entityVar+"/index\";").append("\r\n");
			controller.append("	}").append("\r\n");
			controller.append("\r\n");
		}
		//selectPage
		controller.append("	@ResponseBody").append("\r\n");
		controller.append("	@RequestMapping(\"selectPage\")").append("\r\n");
		if(addAuth) {
			controller.append("	@Authority(\""+authName+".view\")").append("\r\n");
		}
		controller.append("	public RestResponse selectPage("+entityName+" param){").append("\r\n");
		controller.append("		PageContainer page = getPageContainer(param);").append("\r\n");
		controller.append("		return getSuccess("+entityVar+"Service.selectPage(page));").append("\r\n");
		controller.append("	}").append("\r\n");
		controller.append("\r\n");
		//add
		if(jspUrl) {
			controller.append("	@RequestMapping(\"add\")").append("\r\n");
			if(addAuth) {
				controller.append("	@Authority(\""+authName+".modify\")").append("\r\n");
			}
			controller.append("	public String add(Model model){").append("\r\n");
			controller.append("		return \""+modelName+"/"+entityVar+"/add\";").append("\r\n");
			controller.append("	}").append("\r\n");
			controller.append("\r\n");
		}
		//saveAdd
		controller.append("	@ResponseBody").append("\r\n");
		controller.append("	@RequestMapping(\"saveAdd\")").append("\r\n");
		if(addAuth) {
			controller.append("	@Authority(\""+authName+".modify\")").append("\r\n");
		}
		controller.append("	public RestResponse saveAdd("+entityName+" param){").append("\r\n");
		controller.append("		return getSuccess("+entityVar+"Service.insert(param));").append("\r\n");
		controller.append("	}").append("\r\n");
		controller.append("\r\n");
		//update
		if(jspUrl) {
			controller.append("	@RequestMapping(\"update\")").append("\r\n");
			if(addAuth) {
				controller.append("	@Authority(\""+authName+".modify\")").append("\r\n");
			}
			controller.append("	public String update(Model model, Long id){").append("\r\n");
			controller.append("		model.addAttribute(\"entity\", "+entityVar+"Service.selectById(id));").append("\r\n");
			controller.append("		return \""+modelName+"/"+entityVar+"/update\";").append("\r\n");
			controller.append("	}").append("\r\n");
			controller.append("\r\n");
		}
		//saveUpdate
		controller.append("	@ResponseBody").append("\r\n");
		controller.append("	@RequestMapping(\"saveUpdate\")").append("\r\n");
		if(addAuth) {
			controller.append("	@Authority(\""+authName+".modify\")").append("\r\n");
		}
		controller.append("	public RestResponse saveUpdate("+entityName+" param){").append("\r\n");
		controller.append("		return getSuccess("+entityVar+"Service.update(param));").append("\r\n");
		controller.append("	}").append("\r\n");
		controller.append("\r\n");
		//delete
		controller.append("	@ResponseBody").append("\r\n");
		controller.append("	@RequestMapping(\"delete\")").append("\r\n");
		if(addAuth) {
			controller.append("	@Authority(\""+authName+".modify\")").append("\r\n");
		}
		controller.append("	public RestResponse delete(@RequestParam(\"ids\") List<Long> ids){").append("\r\n");
		controller.append("		return getSuccess("+entityVar+"Service.batchDelete(ids));").append("\r\n");
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
			String filePath = rootPath + javaPath + packageFolder + "\\controller\\";
			FileUtil.createFile(filePath + entityName +"Controller.java", text);
		}
	}

}
