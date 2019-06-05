package com.cbbase.core.assist.code;

import com.cbbase.core.tools.FileUtil;
import com.cbbase.core.tools.StringUtil;

public class CodeJsp extends CodeAssist {
	
	public static void create(){
		
		/**
		 * 添加页面
		 */
		StringBuilder jspAdd = new StringBuilder();
		jspAdd.append("<%@ page language=\"java\" import=\"java.util.*\" pageEncoding=\"UTF-8\"%>").append("\r\n");
		jspAdd.append("<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\"%>").append("\r\n");
		jspAdd.append("").append("\r\n");
		jspAdd.append("<!DOCTYPE html>").append("\r\n");
		jspAdd.append("<html lang=\"zh\">").append("\r\n");
		jspAdd.append("<head>").append("\r\n");
		jspAdd.append("	<meta charset=\"UTF-8\">").append("\r\n");
		jspAdd.append("	<meta name=\"renderer\" content=\"webkit|ie-comp|ie-stand\">").append("\r\n");
		jspAdd.append("	<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">").append("\r\n");
		jspAdd.append("	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">").append("\r\n");
		jspAdd.append("	<meta http-equiv=\"Cache-Control\" content=\"no-siteapp\" />").append("\r\n");
		jspAdd.append("	<title></title>").append("\r\n");
		jspAdd.append("</head>").append("\r\n");
		jspAdd.append("<body>").append("\r\n");
		jspAdd.append("	<!-- 添加界面 -->").append("\r\n");
		jspAdd.append("	<div class=\"layerDiv mainDiv\">").append("\r\n");
		jspAdd.append("		<form id=\"addForm\" class=\"layui-form\" action=\"\">").append("\r\n");
		int fieldCount = 0;
		for(int i=0; i<columns.size(); i++){
			String field_name = StringUtil.formatCamel(columns.get(i).get("column_name").toString());
			String title = getFieldTitle(i);
			if("id".equals(field_name) || isBaseEntityField(field_name)){
				continue;
			}
			if(fieldCount%2 == 0) {
				jspAdd.append("			<div class=\"layui-form-item\">").append("\r\n");
			}
			if(isSelectField(field_name)) {
				jspAdd.append("				<div class=\"layui-inline\">").append("\r\n");
				jspAdd.append("					<label class=\"layui-form-label\">"+title+"</label>").append("\r\n");
				jspAdd.append("					<div class=\"layui-input-inline\">").append("\r\n");
				jspAdd.append("						<select name=\""+field_name+"\">").append("\r\n");
				jspAdd.append("						</select>").append("\r\n");
				jspAdd.append("					</div>").append("\r\n");
				jspAdd.append("				</div>").append("\r\n");
			}else if(field_name.toLowerCase().endsWith("id")) {
				jspAdd.append("				<div class=\"layui-inline\">").append("\r\n");
				jspAdd.append("					<label class=\"layui-form-label\">"+title+"</label>").append("\r\n");
				jspAdd.append("					<div class=\"layui-input-inline\">").append("\r\n");
				jspAdd.append("						<select name=\""+field_name+"\">").append("\r\n");
				String listVar = field_name.substring(0, field_name.length()-2)+"List";
				jspAdd.append("							<c:forEach items=\"${"+listVar+"}\" var=\"v\">").append("\r\n");
				jspAdd.append("		                       <option value=\"${v.id}\">${v.id}</option>").append("\r\n");
				jspAdd.append("		                	</c:forEach>").append("\r\n");
				jspAdd.append("						</select>").append("\r\n");
				jspAdd.append("					</div>").append("\r\n");
				jspAdd.append("				</div>").append("\r\n");
			}else {
				jspAdd.append("				<div class=\"layui-inline\">").append("\r\n");
				jspAdd.append("					<label class=\"layui-form-label\">"+title+"</label>").append("\r\n");
				jspAdd.append("					<div class=\"layui-input-inline\">").append("\r\n");
				jspAdd.append("						<input type=\"text\" name=\""+field_name+"\" class=\"layui-input\">").append("\r\n");
				jspAdd.append("					</div>").append("\r\n");
				jspAdd.append("				</div>").append("\r\n");
			}
			if(fieldCount%2 == 1) {
				jspAdd.append("			</div>").append("\r\n");
			}
			fieldCount++;
		}
		if(fieldCount%2 == 1) {
			jspAdd.append("			</div>").append("\r\n");
		}
		jspAdd.append("		</form>").append("\r\n");
		jspAdd.append("	</div>").append("\r\n");
		jspAdd.append("</body>").append("\r\n");
		jspAdd.append("").append("\r\n");
		jspAdd.append("<!-- 业务逻辑 -->").append("\r\n");
		jspAdd.append("<script type=\"text/javascript\">").append("\r\n");
		jspAdd.append("").append("\r\n");
		jspAdd.append("	$(function() {").append("\r\n");
		jspAdd.append("		form.render();").append("\r\n");
		jspAdd.append("	})").append("\r\n");
		jspAdd.append("	").append("\r\n");
		jspAdd.append("</script>").append("\r\n");
		jspAdd.append("</html>").append("\r\n");
		jspAdd.append("").append("\r\n");
		
		/**
		 * 主页面
		 */
		StringBuilder jspIndex = new StringBuilder();
		jspIndex.append("<%@ page language=\"java\" import=\"java.util.*\" pageEncoding=\"UTF-8\"%>").append("\r\n");
		jspIndex.append("<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\"%>").append("\r\n");
		if(addAuth) {
			jspIndex.append("<%@ taglib prefix=\"tag\" uri=\"http://www.cbbase.com/core/taglib\"%>").append("\r\n");
		}
		jspIndex.append("<%@ include file=\"/header.jsp\" %>").append("\r\n");
		jspIndex.append("").append("\r\n");
		jspIndex.append("<!DOCTYPE html>").append("\r\n");
		jspIndex.append("<html lang=\"zh\">").append("\r\n");
		jspIndex.append("<head>").append("\r\n");
		jspIndex.append("  	<meta charset=\"UTF-8\">").append("\r\n");
		jspIndex.append("  	<meta name=\"renderer\" content=\"webkit|ie-comp|ie-stand\">").append("\r\n");
		jspIndex.append("  	<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">").append("\r\n");
		jspIndex.append("  	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">").append("\r\n");
		jspIndex.append("  	<meta http-equiv=\"Cache-Control\" content=\"no-siteapp\" />").append("\r\n");
		jspIndex.append("  	<title></title>").append("\r\n");
		jspIndex.append("</head>").append("\r\n");
		jspIndex.append("<body>").append("\r\n");
		jspIndex.append("	<div class=\"mainDiv\">").append("\r\n");
		jspIndex.append("		<form id=\"queryForm\" class=\"layui-form\" action=\"\">").append("\r\n");
		jspIndex.append("			<div class=\"layui-form-item\">").append("\r\n");
		for(int i=0; i<columns.size(); i++){
			String column_name = columns.get(i).get("column_name").toString().toLowerCase();
			String field_name = StringUtil.formatCamel(columns.get(i).get("column_name").toString());
			String title = getFieldTitle(i);
			if(isBaseEntityField(field_name)){
				continue;
			}
			if(column_name.endsWith("code") || column_name.endsWith("name")){
				jspIndex.append("				<div class=\"layui-inline\">").append("\r\n");
				jspIndex.append("					<label class=\"layui-form-label\">"+title+"</label>").append("\r\n");
				jspIndex.append("					<div class=\"layui-input-inline\">").append("\r\n");
				jspIndex.append("						<input type=\"text\" name=\""+field_name+"\" class=\"layui-input\">").append("\r\n");
				jspIndex.append("					</div>").append("\r\n");
				jspIndex.append("				</div>").append("\r\n");
			}
			if(isSelectField(field_name)) {
				jspIndex.append("				<div class=\"layui-inline\">").append("\r\n");
				jspIndex.append("					<label class=\"layui-form-label\">"+title+"</label>").append("\r\n");
				jspIndex.append("					<div class=\"layui-input-inline\">").append("\r\n");
				jspIndex.append("						<select name=\""+field_name+"\">").append("\r\n");
				jspIndex.append("							<option value=\"\">全部</option>").append("\r\n");
				jspIndex.append("						</select>").append("\r\n");
				jspIndex.append("					</div>").append("\r\n");
				jspIndex.append("				</div>").append("\r\n");
			}
			if(field_name.toLowerCase().endsWith("id")) {
				jspIndex.append("				<div class=\"layui-inline\">").append("\r\n");
				jspIndex.append("					<label class=\"layui-form-label\">"+title+"</label>").append("\r\n");
				jspIndex.append("					<div class=\"layui-input-inline\">").append("\r\n");
				jspIndex.append("						<select name=\""+field_name+"\">").append("\r\n");
				jspIndex.append("							<option value=\"\">全部</option>").append("\r\n");
				String listVar = field_name.substring(0, field_name.length()-2)+"List";
				jspIndex.append("							<c:forEach items=\"${"+listVar+"}\" var=\"v\">").append("\r\n");
				jspIndex.append("		                       <option value=\"${v.id}\">${v.id}</option>").append("\r\n");
				jspIndex.append("		                	</c:forEach>").append("\r\n");
				jspIndex.append("						</select>").append("\r\n");
				jspIndex.append("					</div>").append("\r\n");
				jspIndex.append("				</div>").append("\r\n");
			}
		}
		jspIndex.append("				<div class=\"layui-inline\">").append("\r\n");
		jspIndex.append("					<button class=\"layui-btn\" onclick=\"reloadTable();\" type=\"button\">查询</button>").append("\r\n");
		jspIndex.append("				</div>").append("\r\n");
		jspIndex.append("			</div>").append("\r\n");
		jspIndex.append("			<div class=\"layui-form-item\">").append("\r\n");
		if(addAuth) {
			jspIndex.append("				<tag:Auth auth=\""+getAuthName()+".modify\">").append("\r\n");
			jspIndex.append("					<button class=\"layui-btn layui-btn-sm layui-btn-normal\" onclick=\"openAdd();\" type=\"button\">新增</button>").append("\r\n");
			jspIndex.append("					<button class=\"layui-btn layui-btn-sm layui-btn-danger\" onclick=\"doBatchDelete();\" type=\"button\">批量删除</button>").append("\r\n");
			jspIndex.append("					<input type=\"hidden\" id=\"hasAuthModify\" value=\"1\">").append("\r\n");
			jspIndex.append("				</tag:Auth>").append("\r\n");
		}else {
			jspIndex.append("				<button class=\"layui-btn layui-btn-sm layui-btn-normal\" onclick=\"openAdd();\" type=\"button\">新增</button>").append("\r\n");
			jspIndex.append("				<button class=\"layui-btn layui-btn-sm layui-btn-danger\" onclick=\"doBatchDelete();\" type=\"button\">批量删除</button>").append("\r\n");
		}
		jspIndex.append("				<table id=\"tableObj\"></table>").append("\r\n");
		jspIndex.append("			</div>").append("\r\n");
		jspIndex.append("		</form>").append("\r\n");
		jspIndex.append("	</div>").append("\r\n");
		jspIndex.append("</body>").append("\r\n");
		jspIndex.append("").append("\r\n");
		jspIndex.append("<%@ include file=\"/footer.jsp\"%>").append("\r\n");
		jspIndex.append("<!-- 业务逻辑 -->").append("\r\n");
		jspIndex.append("<script type=\"text/javascript\">").append("\r\n");
		jspIndex.append("	$(function() {").append("\r\n");
		jspIndex.append("		initTable();").append("\r\n");
		jspIndex.append("	});").append("\r\n");
		jspIndex.append("	").append("\r\n");
		jspIndex.append("	function initTable(){").append("\r\n");
		jspIndex.append("		loadTable({").append("\r\n");
		jspIndex.append("	    	elem: '#tableObj'").append("\r\n");
		jspIndex.append("	    	,url: basePath+'/web/"+entityVar+"/selectPage' //数据接口").append("\r\n");
		jspIndex.append("	    	,where: $(\"#queryForm\").serializeJson()	//数据接口参数").append("\r\n");
		jspIndex.append("			,cols: [[ //表格数据").append("\r\n");
		jspIndex.append("				{type:'checkbox'}").append("\r\n");
		for(int i=0; i<columns.size(); i++){
			String field_name = StringUtil.formatCamel(columns.get(i).get("column_name").toString());
			String title = getFieldTitle(i);
			if("id".equals(field_name) || isBaseEntityField(field_name)){
				continue;
			}
			jspIndex.append("				,{field: '"+field_name+"', title: '"+title+"'}").append("\r\n");
		}
		jspIndex.append("				,{field: 'id', title: '操作', width: 150, templet: getOperateHtml}").append("\r\n");
		jspIndex.append("			]]").append("\r\n");
		jspIndex.append("		});").append("\r\n");
		jspIndex.append("	}").append("\r\n");
		jspIndex.append("	").append("\r\n");
		jspIndex.append("	function getOperateHtml(row){").append("\r\n");
		jspIndex.append("		var html = \"\";").append("\r\n");
		if(addAuth) {
			jspIndex.append("		if($(\"#hasAuthModify\").val() == \"1\"){").append("\r\n");
			jspIndex.append("			html = html + '<button class=\"layui-btn layui-btn-sm layui-btn-normal\" onclick=openUpdate(\"'+row.id+'\"); type=\"button\">修改</button>';").append("\r\n");
			jspIndex.append("			html = html + '<button class=\"layui-btn layui-btn-sm layui-btn-danger\" onclick=doDelete(\"'+row.id+'\"); type=\"button\">删除</button>';").append("\r\n");
			jspIndex.append("		}").append("\r\n");
		}else {
			jspIndex.append("		html = html + '<button class=\"layui-btn layui-btn-sm layui-btn-normal\" onclick=openUpdate(\"'+row.id+'\"); type=\"button\">修改</button>';").append("\r\n");
			jspIndex.append("		html = html + '<button class=\"layui-btn layui-btn-sm layui-btn-danger\" onclick=doDelete(\"'+row.id+'\"); type=\"button\">删除</button>';").append("\r\n");
		}
		jspIndex.append("		return html;").append("\r\n");
		jspIndex.append("	}").append("\r\n");
		jspIndex.append("	").append("\r\n");
		jspIndex.append("	function reloadTable(){").append("\r\n");
		jspIndex.append("		table.reload(\"tableObj\", {where:$(\"#queryForm\").serializeJson()});").append("\r\n");
		jspIndex.append("	}").append("\r\n");
		jspIndex.append("	").append("\r\n");
		jspIndex.append("	function openAdd(){").append("\r\n");
		jspIndex.append("		$.post(basePath+'/web/"+entityVar+"/add', {}, function(html){").append("\r\n");
		jspIndex.append("			layer.open({").append("\r\n");
		jspIndex.append("			    type: 1,").append("\r\n");
		jspIndex.append("			    title: \"新增\",").append("\r\n");
		jspIndex.append("			    content: html,").append("\r\n");
		jspIndex.append("			    area: [\"60%\", \"80%\"],").append("\r\n");
		jspIndex.append("			    btn: ['确定', '取消'],").append("\r\n");
		jspIndex.append("				yes: function(index, layero){").append("\r\n");
		jspIndex.append("					saveAdd();").append("\r\n");
		jspIndex.append("				},").append("\r\n");
		jspIndex.append("				btn2: function(index, layero){").append("\r\n");
		jspIndex.append("				    layer.close(index);").append("\r\n");
		jspIndex.append("				}").append("\r\n");
		jspIndex.append("			});").append("\r\n");
		jspIndex.append("		});").append("\r\n");
		jspIndex.append("	}").append("\r\n");
		jspIndex.append("	").append("\r\n");
		jspIndex.append("	function saveAdd(){").append("\r\n");
		jspIndex.append("		var param = $(\"#addForm\").serializeJson();").append("\r\n");
		jspIndex.append("		doParamRequest(basePath+\"/web/"+entityVar+"/saveAdd\", param, function(){").append("\r\n");
		jspIndex.append("			layer.close(layer.index);").append("\r\n");
		jspIndex.append("			reloadTable();").append("\r\n");
		jspIndex.append("		});").append("\r\n");
		jspIndex.append("	}").append("\r\n");
		jspIndex.append("").append("\r\n");
		jspIndex.append("	function openUpdate(id){").append("\r\n");
		jspIndex.append("		$.post(basePath+'/web/"+entityVar+"/update?id='+id, {}, function(html){").append("\r\n");
		jspIndex.append("			layer.open({").append("\r\n");
		jspIndex.append("			    type: 1,").append("\r\n");
		jspIndex.append("			    content: html,").append("\r\n");
		jspIndex.append("			    area: [\"60%\", \"80%\"],").append("\r\n");
		jspIndex.append("			    btn: ['确定', '取消'],").append("\r\n");
		jspIndex.append("				yes: function(index, layero){").append("\r\n");
		jspIndex.append("					saveUpdate();").append("\r\n");
		jspIndex.append("				},").append("\r\n");
		jspIndex.append("				btn2: function(index, layero){").append("\r\n");
		jspIndex.append("				    layer.close(index);").append("\r\n");
		jspIndex.append("				}").append("\r\n");
		jspIndex.append("			});").append("\r\n");
		jspIndex.append("		});").append("\r\n");
		jspIndex.append("	}").append("\r\n");
		jspIndex.append("	").append("\r\n");
		jspIndex.append("	function saveUpdate(){").append("\r\n");
		jspIndex.append("		var param = $(\"#updateForm\").serializeJson();").append("\r\n");
		jspIndex.append("		doParamRequest(basePath+\"/web/"+entityVar+"/saveUpdate\", param, function(){").append("\r\n");
		jspIndex.append("			layer.close(layer.index);").append("\r\n");
		jspIndex.append("			reloadTable();").append("\r\n");
		jspIndex.append("		});").append("\r\n");
		jspIndex.append("	}").append("\r\n");
		jspIndex.append("	").append("\r\n");
		jspIndex.append("	function doDelete(id){").append("\r\n");
		jspIndex.append("		openConfirm(\"你确认删除这条数据吗?\", function(){").append("\r\n");
		jspIndex.append("			var ids = [id];").append("\r\n");
		jspIndex.append("			doParamRequest(basePath+'/web/"+entityVar+"/delete', {\"ids\": ids}, reloadTable);").append("\r\n");
		jspIndex.append("		});").append("\r\n");
		jspIndex.append("	}").append("\r\n");
		jspIndex.append("	").append("\r\n");
		jspIndex.append("	function doBatchDelete(){").append("\r\n");
		jspIndex.append("  		var ids = getSelectedIds('tableObj');").append("\r\n");
		jspIndex.append("  		if(ids == null || ids.length == 0){").append("\r\n");
		jspIndex.append("  			layer.msg(\"请选择数据\");").append("\r\n");
		jspIndex.append("  			return;").append("\r\n");
		jspIndex.append("  		}").append("\r\n");
		jspIndex.append("		openConfirm(\"你确认删除所选中的数据吗?\", function(){").append("\r\n");
		jspIndex.append("			doParamRequest(basePath+'/web/"+entityVar+"/delete', {\"ids\": ids}, reloadTable);").append("\r\n");
		jspIndex.append("		});").append("\r\n");
		jspIndex.append("	}").append("\r\n");
		jspIndex.append("	").append("\r\n");
		jspIndex.append("</script>").append("\r\n");
		jspIndex.append("</html>").append("\r\n");
		jspIndex.append("").append("\r\n");
		

		/**
		 * 修改页面
		 */
		StringBuilder jspUpdate = new StringBuilder();
		jspUpdate.append("<%@ page language=\"java\" import=\"java.util.*\" pageEncoding=\"UTF-8\"%>").append("\r\n");
		jspUpdate.append("<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\"%>").append("\r\n");
		jspUpdate.append("").append("\r\n");
		jspUpdate.append("<!DOCTYPE html>").append("\r\n");
		jspUpdate.append("<html lang=\"zh\">").append("\r\n");
		jspUpdate.append("<head>").append("\r\n");
		jspUpdate.append("	<meta charset=\"UTF-8\">").append("\r\n");
		jspUpdate.append("	<meta name=\"renderer\" content=\"webkit|ie-comp|ie-stand\">").append("\r\n");
		jspUpdate.append("	<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">").append("\r\n");
		jspUpdate.append("	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">").append("\r\n");
		jspUpdate.append("	<meta http-equiv=\"Cache-Control\" content=\"no-siteapp\" />").append("\r\n");
		jspUpdate.append("	<title></title>").append("\r\n");
		jspUpdate.append("</head>").append("\r\n");
		jspUpdate.append("<body>").append("\r\n");
		jspUpdate.append("	<!-- 修改界面 -->").append("\r\n");
		jspUpdate.append("	<div class=\"layerDiv mainDiv\">").append("\r\n");
		jspUpdate.append("		<form id=\"updateForm\" class=\"layui-form\" action=\"\">").append("\r\n");
		jspUpdate.append("			<input type=\"hidden\" name=\"id\" value=\"${entity.id}\">").append("\r\n");
		fieldCount = 0;
		for(int i=0; i<columns.size(); i++){
			String field_name = StringUtil.formatCamel(columns.get(i).get("column_name").toString());
			String title = getFieldTitle(i);
			if("id".equals(field_name) || isBaseEntityField(field_name)){
				continue;
			}
			if(fieldCount%2 == 0) {
				jspUpdate.append("			<div class=\"layui-form-item\">").append("\r\n");
			}
			if(isSelectField(field_name)) {
				jspUpdate.append("				<div class=\"layui-inline\">").append("\r\n");
				jspUpdate.append("					<label class=\"layui-form-label\">"+title+"</label>").append("\r\n");
				jspUpdate.append("					<div class=\"layui-input-inline\">").append("\r\n");
				jspUpdate.append("						<select name=\""+field_name+"\" value=\"${entity."+field_name+"}\">").append("\r\n");
				jspUpdate.append("						</select>").append("\r\n");
				jspUpdate.append("					</div>").append("\r\n");
				jspUpdate.append("				</div>").append("\r\n");
			}else if(field_name.toLowerCase().endsWith("id")) {
				jspUpdate.append("				<div class=\"layui-inline\">").append("\r\n");
				jspUpdate.append("					<label class=\"layui-form-label\">"+title+"</label>").append("\r\n");
				jspUpdate.append("					<div class=\"layui-input-inline\">").append("\r\n");
				jspUpdate.append("						<select name=\""+field_name+"\">").append("\r\n");
				String listVar = field_name.substring(0, field_name.length()-2)+"List";
				jspUpdate.append("							<c:forEach items=\"${"+listVar+"}\" var=\"v\">").append("\r\n");
				jspUpdate.append("		                       <option value=\"${v.id}\">${v.id}</option>").append("\r\n");
				jspUpdate.append("		                	</c:forEach>").append("\r\n");
				jspUpdate.append("						</select>").append("\r\n");
				jspUpdate.append("					</div>").append("\r\n");
				jspUpdate.append("				</div>").append("\r\n");
			}else {
				jspUpdate.append("				<div class=\"layui-inline\">").append("\r\n");
				jspUpdate.append("					<label class=\"layui-form-label\">"+title+"</label>").append("\r\n");
				jspUpdate.append("					<div class=\"layui-input-inline\">").append("\r\n");
				jspUpdate.append("						<input type=\"text\" name=\""+field_name+"\" value=\"${entity."+field_name+"}\" class=\"layui-input\">").append("\r\n");
				jspUpdate.append("					</div>").append("\r\n");
				jspUpdate.append("				</div>").append("\r\n");
			}
			if(fieldCount%2 == 1) {
				jspUpdate.append("			</div>").append("\r\n");
			}
			fieldCount++;
		}
		if(fieldCount%2 == 1) {
			jspUpdate.append("			</div>").append("\r\n");
		}
		jspUpdate.append("		</form>").append("\r\n");
		jspUpdate.append("	</div>").append("\r\n");
		jspUpdate.append("</body>").append("\r\n");
		jspUpdate.append("<!-- 业务逻辑 -->").append("\r\n");
		jspUpdate.append("<script type=\"text/javascript\">").append("\r\n");
		jspUpdate.append("").append("\r\n");
		jspUpdate.append("	$(function() {").append("\r\n");
		jspUpdate.append("		form.render();").append("\r\n");
		jspUpdate.append("	})").append("\r\n");
		jspUpdate.append("	").append("\r\n");
		jspUpdate.append("</script>").append("\r\n");
		jspUpdate.append("	").append("\r\n");
		jspUpdate.append("</html>").append("\r\n");
		jspUpdate.append("").append("\r\n");
		
		if(showContent) {
			System.out.println("===============================");
			System.out.println(jspIndex.toString());
			System.out.println("===============================");
			System.out.println(jspAdd.toString());
			System.out.println("===============================");
			System.out.println(jspUpdate.toString());
		}
		
		if(writeFile) {
			String filePath = rootPath + jspPath + modelName + "\\" + entityVar;
			FileUtil.createFileByString(filePath+"\\add.jsp", jspAdd.toString());
			FileUtil.createFileByString(filePath+"\\index.jsp", jspIndex.toString());
			FileUtil.createFileByString(filePath+"\\update.jsp", jspUpdate.toString());
		}

		
	}
}
