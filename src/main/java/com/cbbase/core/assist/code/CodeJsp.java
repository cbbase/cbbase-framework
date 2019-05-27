package com.cbbase.core.assist.code;

import com.cbbase.core.tools.FileUtil;
import com.cbbase.core.tools.StringUtil;

public class CodeJsp extends CodeAssist {
	
	public static void create(){
		
		/**
		 * 添加页面
		 */
		StringBuilder jsp_add = new StringBuilder();
		jsp_add.append("<%@ page language=\"java\" import=\"java.util.*\" pageEncoding=\"UTF-8\"%>").append("\r\n");
		jsp_add.append("<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\"%>").append("\r\n");
		jsp_add.append("").append("\r\n");
		jsp_add.append("<!DOCTYPE html>").append("\r\n");
		jsp_add.append("<html lang=\"zh\">").append("\r\n");
		jsp_add.append("<head>").append("\r\n");
		jsp_add.append("	<meta charset=\"UTF-8\">").append("\r\n");
		jsp_add.append("	<meta name=\"renderer\" content=\"webkit|ie-comp|ie-stand\">").append("\r\n");
		jsp_add.append("	<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">").append("\r\n");
		jsp_add.append("	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">").append("\r\n");
		jsp_add.append("	<meta http-equiv=\"Cache-Control\" content=\"no-siteapp\" />").append("\r\n");
		jsp_add.append("	<title></title>").append("\r\n");
		jsp_add.append("</head>").append("\r\n");
		jsp_add.append("<body>").append("\r\n");
		jsp_add.append("	<!-- 添加界面 -->").append("\r\n");
		jsp_add.append("	<div class=\"layerDiv mainDiv\">").append("\r\n");
		jsp_add.append("		<form id=\"addForm\" class=\"layui-form\" action=\"\">").append("\r\n");
		int fieldCount = 0;
		for(int i=0; i<columns.size(); i++){
			String field_name = StringUtil.formatCamel(columns.get(i).get("column_name").toString());
			String title = getFieldTitle(i);
			if("id".equals(field_name) || isBaseEntityField(field_name)){
				continue;
			}
			if(fieldCount%2 == 0) {
				jsp_add.append("			<div class=\"layui-form-item\">").append("\r\n");
			}
			jsp_add.append("				<div class=\"layui-inline\">").append("\r\n");
			jsp_add.append("					<label class=\"layui-form-label\">"+title+"</label>").append("\r\n");
			jsp_add.append("					<div class=\"layui-input-inline\">").append("\r\n");
			jsp_add.append("						<input type=\"text\" name=\""+field_name+"\" class=\"layui-input\">").append("\r\n");
			jsp_add.append("					</div>").append("\r\n");
			jsp_add.append("				</div>").append("\r\n");
			if(fieldCount%2 == 1) {
				jsp_add.append("			</div>").append("\r\n");
			}
			fieldCount++;
		}
		if(fieldCount%2 == 1) {
			jsp_add.append("			</div>").append("\r\n");
		}
		jsp_add.append("		</form>").append("\r\n");
		jsp_add.append("	</div>").append("\r\n");
		jsp_add.append("</body>").append("\r\n");
		jsp_add.append("").append("\r\n");
		jsp_add.append("<!-- 业务逻辑 -->").append("\r\n");
		jsp_add.append("<script type=\"text/javascript\">").append("\r\n");
		jsp_add.append("").append("\r\n");
		jsp_add.append("	$(function() {").append("\r\n");
		jsp_add.append("		form.render();").append("\r\n");
		jsp_add.append("	})").append("\r\n");
		jsp_add.append("	").append("\r\n");
		jsp_add.append("</script>").append("\r\n");
		jsp_add.append("</html>").append("\r\n");
		jsp_add.append("").append("\r\n");
		
		/**
		 * 主页面
		 */
		StringBuilder jsp_index = new StringBuilder();
		jsp_index.append("<%@ page language=\"java\" import=\"java.util.*\" pageEncoding=\"UTF-8\"%>").append("\r\n");
		jsp_index.append("<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\"%>").append("\r\n");
		if(addAuth) {
			jsp_index.append("<%@ taglib prefix=\"tag\" uri=\"http://www.cbbase.com/framework/taglib\"%>").append("\r\n");
		}
		jsp_index.append("<%@ include file=\"/header.jsp\" %>").append("\r\n");
		jsp_index.append("").append("\r\n");
		jsp_index.append("<!DOCTYPE html>").append("\r\n");
		jsp_index.append("<html lang=\"zh\">").append("\r\n");
		jsp_index.append("<head>").append("\r\n");
		jsp_index.append("  	<meta charset=\"UTF-8\">").append("\r\n");
		jsp_index.append("  	<meta name=\"renderer\" content=\"webkit|ie-comp|ie-stand\">").append("\r\n");
		jsp_index.append("  	<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">").append("\r\n");
		jsp_index.append("  	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">").append("\r\n");
		jsp_index.append("  	<meta http-equiv=\"Cache-Control\" content=\"no-siteapp\" />").append("\r\n");
		jsp_index.append("  	<title></title>").append("\r\n");
		jsp_index.append("</head>").append("\r\n");
		jsp_index.append("<body>").append("\r\n");
		jsp_index.append("	<div class=\"mainDiv\">").append("\r\n");
		jsp_index.append("		<form id=\"queryForm\" class=\"layui-form\" action=\"\">").append("\r\n");
		jsp_index.append("			<div class=\"layui-form-item\">").append("\r\n");
		for(int i=0; i<columns.size(); i++){
			String column_name = columns.get(i).get("column_name").toString();
			String field_name = StringUtil.formatCamel(columns.get(i).get("column_name").toString());
			String title = getFieldTitle(i);
			if("id".equals(field_name) || isBaseEntityField(field_name)){
				continue;
			}
			if(column_name.endsWith("code") || column_name.endsWith("name")){
				jsp_index.append("				<div class=\"layui-inline\">").append("\r\n");
				jsp_index.append("					<label class=\"layui-form-label\">"+title+"</label>").append("\r\n");
				jsp_index.append("					<div class=\"layui-input-inline\">").append("\r\n");
				jsp_index.append("						<input type=\"tel\" name=\""+field_name+"\" class=\"layui-input\">").append("\r\n");
				jsp_index.append("					</div>").append("\r\n");
				jsp_index.append("				</div>").append("\r\n");
			}
		}
		jsp_index.append("				<div class=\"layui-inline\">").append("\r\n");
		jsp_index.append("					<button class=\"layui-btn\" onclick=\"reloadTable();\" type=\"button\">查询</button>").append("\r\n");
		jsp_index.append("				</div>").append("\r\n");
		jsp_index.append("			</div>").append("\r\n");
		jsp_index.append("			<div class=\"layui-form-item\">").append("\r\n");
		if(addAuth) {
			jsp_index.append("				<tag:Auth auth=\""+getAuthName()+".modify\">").append("\r\n");
			jsp_index.append("					<button class=\"layui-btn layui-btn-normal\" onclick=\"openAdd();\" type=\"button\">新增</button>").append("\r\n");
			jsp_index.append("					<button class=\"layui-btn layui-btn-danger\" onclick=\"doBatchDelete();\" type=\"button\">批量删除</button>").append("\r\n");
			jsp_index.append("					<input type=\"hidden\" id=\"hasAuthModify\" value=\"1\">").append("\r\n");
			jsp_index.append("				</tag:Auth>").append("\r\n");
		}else {
			jsp_index.append("				<button class=\"layui-btn layui-btn-normal\" onclick=\"openAdd();\" type=\"button\">新增</button>").append("\r\n");
			jsp_index.append("				<button class=\"layui-btn layui-btn-danger\" onclick=\"doBatchDelete();\" type=\"button\">批量删除</button>").append("\r\n");
		}
		jsp_index.append("				<table id=\"tableObj\"></table>").append("\r\n");
		jsp_index.append("			</div>").append("\r\n");
		jsp_index.append("		</form>").append("\r\n");
		jsp_index.append("	</div>").append("\r\n");
		jsp_index.append("</body>").append("\r\n");
		jsp_index.append("").append("\r\n");
		jsp_index.append("<%@ include file=\"/footer.jsp\"%>").append("\r\n");
		jsp_index.append("<!-- 业务逻辑 -->").append("\r\n");
		jsp_index.append("<script type=\"text/javascript\">").append("\r\n");
		jsp_index.append("	$(function() {").append("\r\n");
		jsp_index.append("		initTable();").append("\r\n");
		jsp_index.append("	});").append("\r\n");
		jsp_index.append("	").append("\r\n");
		jsp_index.append("	function initTable(){").append("\r\n");
		jsp_index.append("		loadTable({").append("\r\n");
		jsp_index.append("	    	elem: '#tableObj'").append("\r\n");
		jsp_index.append("	    	,url: basePath+'/web/"+model_path+"/selectPage' //数据接口").append("\r\n");
		jsp_index.append("	    	,where: $(\"#queryForm\").serializeJson()	//数据接口参数").append("\r\n");
		jsp_index.append("			,cols: [[ //表格数据").append("\r\n");
		jsp_index.append("				{type:'checkbox'}").append("\r\n");
		for(int i=0; i<columns.size(); i++){
			String field_name = StringUtil.formatCamel(columns.get(i).get("column_name").toString());
			String title = getFieldTitle(i);
			if("id".equals(field_name) || isBaseEntityField(field_name)){
				continue;
			}
			jsp_index.append("				,{field: '"+field_name+"', title: '"+title+"'}").append("\r\n");
		}
		jsp_index.append("				,{field: 'id', title: '操作', width: 150, templet: getOperateHtml}").append("\r\n");
		jsp_index.append("			]]").append("\r\n");
		jsp_index.append("		});").append("\r\n");
		jsp_index.append("	}").append("\r\n");
		jsp_index.append("	").append("\r\n");
		jsp_index.append("	function getOperateHtml(row){").append("\r\n");
		jsp_index.append("		var html = \"\";").append("\r\n");
		if(addAuth) {
			jsp_index.append("		if($(\"#hasAuthModify\").val() == \"1\"){").append("\r\n");
			jsp_index.append("			html = html + '<button class=\"layui-btn layui-btn-sm layui-btn-normal\" onclick=openUpdate(\"'+row.id+'\"); type=\"button\">修改</button>';").append("\r\n");
			jsp_index.append("			html = html + '<button class=\"layui-btn layui-btn-sm layui-btn-danger\" onclick=doDelete(\"'+row.id+'\"); type=\"button\">删除</button>';").append("\r\n");
			jsp_index.append("		}").append("\r\n");
		}else {
			jsp_index.append("		html = html + '<button class=\"layui-btn layui-btn-sm layui-btn-normal\" onclick=openUpdate(\"'+row.id+'\"); type=\"button\">修改</button>';").append("\r\n");
			jsp_index.append("		html = html + '<button class=\"layui-btn layui-btn-sm layui-btn-danger\" onclick=doDelete(\"'+row.id+'\"); type=\"button\">删除</button>';").append("\r\n");
		}
		jsp_index.append("		return html;").append("\r\n");
		jsp_index.append("	}").append("\r\n");
		jsp_index.append("	").append("\r\n");
		jsp_index.append("	function reloadTable(){").append("\r\n");
		jsp_index.append("		table.reload(\"tableObj\", {where:$(\"#queryForm\").serializeJson()});").append("\r\n");
		jsp_index.append("	}").append("\r\n");
		jsp_index.append("	").append("\r\n");
		jsp_index.append("	function openAdd(){").append("\r\n");
		jsp_index.append("		$.post(basePath+'/pages/"+package_last+"/"+model_path+"/add.jsp', {}, function(html){").append("\r\n");
		jsp_index.append("			layer.open({").append("\r\n");
		jsp_index.append("			    type: 1,").append("\r\n");
		jsp_index.append("			    title: \"新增\",").append("\r\n");
		jsp_index.append("			    content: html,").append("\r\n");
		jsp_index.append("			    area: [\"60%\", \"80%\"],").append("\r\n");
		jsp_index.append("			    btn: ['确定', '取消'],").append("\r\n");
		jsp_index.append("				yes: function(index, layero){").append("\r\n");
		jsp_index.append("					saveAdd();").append("\r\n");
		jsp_index.append("				},").append("\r\n");
		jsp_index.append("				btn2: function(index, layero){").append("\r\n");
		jsp_index.append("				    layer.close(index);").append("\r\n");
		jsp_index.append("				}").append("\r\n");
		jsp_index.append("			});").append("\r\n");
		jsp_index.append("		});").append("\r\n");
		jsp_index.append("	}").append("\r\n");
		jsp_index.append("	").append("\r\n");
		jsp_index.append("	function saveAdd(){").append("\r\n");
		jsp_index.append("		doFormRequest(\"addForm\", basePath+\"/web/"+model_path+"/add\", function(){").append("\r\n");
		jsp_index.append("			layer.close(layer.index);").append("\r\n");
		jsp_index.append("			reloadTable();").append("\r\n");
		jsp_index.append("		});").append("\r\n");
		jsp_index.append("	}").append("\r\n");
		jsp_index.append("").append("\r\n");
		jsp_index.append("	function openUpdate(id){").append("\r\n");
		jsp_index.append("		$.post(basePath+'/pages/"+package_last+"/"+model_path+"/update.jsp?id='+id, {}, function(html){").append("\r\n");
		jsp_index.append("			layer.open({").append("\r\n");
		jsp_index.append("			    type: 1,").append("\r\n");
		jsp_index.append("			    content: html,").append("\r\n");
		jsp_index.append("			    area: [\"60%\", \"80%\"],").append("\r\n");
		jsp_index.append("			    btn: ['确定', '取消'],").append("\r\n");
		jsp_index.append("				yes: function(index, layero){").append("\r\n");
		jsp_index.append("					saveUpdate();").append("\r\n");
		jsp_index.append("				},").append("\r\n");
		jsp_index.append("				btn2: function(index, layero){").append("\r\n");
		jsp_index.append("				    layer.close(index);").append("\r\n");
		jsp_index.append("				}").append("\r\n");
		jsp_index.append("			});").append("\r\n");
		jsp_index.append("		});").append("\r\n");
		jsp_index.append("	}").append("\r\n");
		jsp_index.append("	").append("\r\n");
		jsp_index.append("	function saveUpdate(){").append("\r\n");
		jsp_index.append("		doFormRequest(\"updateForm\", basePath+\"/web/"+model_path+"/update\", function(){").append("\r\n");
		jsp_index.append("			layer.close(layer.index);").append("\r\n");
		jsp_index.append("			reloadTable();").append("\r\n");
		jsp_index.append("		});").append("\r\n");
		jsp_index.append("	}").append("\r\n");
		jsp_index.append("	").append("\r\n");
		jsp_index.append("	function doDelete(id){").append("\r\n");
		jsp_index.append("		openConfirm(\"你确认删除这条数据吗?\", function(){").append("\r\n");
		jsp_index.append("			var ids = [id];").append("\r\n");
		jsp_index.append("			doParamRequest({\"ids\": ids}, basePath+'/web/"+model_path+"/delete', reloadTable);").append("\r\n");
		jsp_index.append("		});").append("\r\n");
		jsp_index.append("	}").append("\r\n");
		jsp_index.append("	").append("\r\n");
		jsp_index.append("	function doBatchDelete(){").append("\r\n");
		jsp_index.append("  		var ids = getSelectedIds('tableObj');").append("\r\n");
		jsp_index.append("  		if(ids == null || ids.length == 0){").append("\r\n");
		jsp_index.append("  			layer.msg(\"请选择数据\");").append("\r\n");
		jsp_index.append("  			return;").append("\r\n");
		jsp_index.append("  		}").append("\r\n");
		jsp_index.append("		openConfirm(\"你确认删除所选中的数据吗?\", function(){").append("\r\n");
		jsp_index.append("			doParamRequest({\"ids\": ids}, basePath+'/web/"+model_path+"/delete', reloadTable);").append("\r\n");
		jsp_index.append("		});").append("\r\n");
		jsp_index.append("	}").append("\r\n");
		jsp_index.append("	").append("\r\n");
		jsp_index.append("</script>").append("\r\n");
		jsp_index.append("</html>").append("\r\n");
		jsp_index.append("").append("\r\n");
		

		/**
		 * 修改页面
		 */
		StringBuilder jsp_update = new StringBuilder();
		jsp_update.append("<%@ page language=\"java\" import=\"java.util.*\" pageEncoding=\"UTF-8\"%>").append("\r\n");
		jsp_update.append("<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\"%>").append("\r\n");
		jsp_update.append("").append("\r\n");
		jsp_update.append("<!DOCTYPE html>").append("\r\n");
		jsp_update.append("<html lang=\"zh\">").append("\r\n");
		jsp_update.append("<head>").append("\r\n");
		jsp_update.append("	<meta charset=\"UTF-8\">").append("\r\n");
		jsp_update.append("	<meta name=\"renderer\" content=\"webkit|ie-comp|ie-stand\">").append("\r\n");
		jsp_update.append("	<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">").append("\r\n");
		jsp_update.append("	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">").append("\r\n");
		jsp_update.append("	<meta http-equiv=\"Cache-Control\" content=\"no-siteapp\" />").append("\r\n");
		jsp_update.append("	<title></title>").append("\r\n");
		jsp_update.append("</head>").append("\r\n");
		jsp_update.append("<body>").append("\r\n");
		jsp_update.append("	<!-- 修改界面 -->").append("\r\n");
		jsp_update.append("	<div class=\"layerDiv mainDiv\">").append("\r\n");
		jsp_update.append("		<form id=\"updateForm\" class=\"layui-form\" action=\"\">").append("\r\n");
		jsp_update.append("			<input type=\"hidden\" name=\"id\" value=\"${param.id}\">").append("\r\n");
		fieldCount = 0;
		for(int i=0; i<columns.size(); i++){
			String field_name = StringUtil.formatCamel(columns.get(i).get("column_name").toString());
			String title = getFieldTitle(i);
			if("id".equals(field_name) || isBaseEntityField(field_name)){
				continue;
			}
			if(fieldCount%2 == 0) {
				jsp_update.append("			<div class=\"layui-form-item\">").append("\r\n");
			}
			jsp_update.append("				<div class=\"layui-inline\">").append("\r\n");
			jsp_update.append("					<label class=\"layui-form-label\">"+title+"</label>").append("\r\n");
			jsp_update.append("					<div class=\"layui-input-inline\">").append("\r\n");
			jsp_update.append("						<input type=\"text\" name=\""+field_name+"\" class=\"layui-input\">").append("\r\n");
			jsp_update.append("					</div>").append("\r\n");
			jsp_update.append("				</div>").append("\r\n");
			if(fieldCount%2 == 1) {
				jsp_update.append("			</div>").append("\r\n");
			}
			fieldCount++;
		}
		if(fieldCount%2 == 1) {
			jsp_update.append("			</div>").append("\r\n");
		}
		jsp_update.append("		</form>").append("\r\n");
		jsp_update.append("	</div>").append("\r\n");
		jsp_update.append("</body>").append("\r\n");
		jsp_update.append("<!-- 业务逻辑 -->").append("\r\n");
		jsp_update.append("<script type=\"text/javascript\">").append("\r\n");
		jsp_update.append("").append("\r\n");
		jsp_update.append("	$(function() {").append("\r\n");
		jsp_update.append("		form.render();").append("\r\n");
		jsp_update.append("		//填充数据").append("\r\n");
		jsp_update.append("		var id = $(\"#updateForm [name=id]\").val();").append("\r\n");
		jsp_update.append("		initFormData(\"updateForm\", basePath+\"/web/"+model_path+"/select\", {\"id\":id});").append("\r\n");
		jsp_update.append("	})").append("\r\n");
		jsp_update.append("	").append("\r\n");
		jsp_update.append("</script>").append("\r\n");
		jsp_update.append("	").append("\r\n");
		jsp_update.append("</html>").append("\r\n");
		jsp_update.append("").append("\r\n");
		

		if(showContent) {
			System.out.println("===============================");
			System.out.println(jsp_index.toString());
			System.out.println("===============================");
			System.out.println(jsp_add.toString());
			System.out.println("===============================");
			System.out.println(jsp_update.toString());
		}
		
		if(writeFile) {
			String file_path = root_path + jsp_path + package_last + "\\" + model_path;
			FileUtil.createFileByString(file_path+"\\add.jsp", jsp_add.toString());
			FileUtil.createFileByString(file_path+"\\index.jsp", jsp_index.toString());
			FileUtil.createFileByString(file_path+"\\update.jsp", jsp_update.toString());
		}

		
	}
}
