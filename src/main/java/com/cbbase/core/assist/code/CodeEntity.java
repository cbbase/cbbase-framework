package com.cbbase.core.assist.code;

import java.util.HashSet;
import java.util.Set;

import com.cbbase.core.assist.database.DataTypeUtil;
import com.cbbase.core.tools.FileUtil;
import com.cbbase.core.tools.StringUtil;

public class CodeEntity extends CodeAssist {

	public static void create(){

		StringBuilder entity = new StringBuilder();
		entity.append("package "+packageName+".entity;").append("\r\n");
		entity.append("\r\n");
		
		Set<String> importSet = new HashSet<String>();
		for(int i=0; i<columns.size(); i++){
			String column_type = columns.get(i).get("data_type").toString().toUpperCase();
			Object data_scale = columns.get(i).get("data_scale");
			column_type = DataTypeUtil.toJavaFullType(column_type, data_scale);
			if(column_type.startsWith("java.lang")) {
				continue;
			}
			if(importSet.contains(column_type)) {
				continue;
			}
			entity.append("import "+column_type+";").append("\r\n");
			importSet.add(column_type);
		}
		if(!importSet.isEmpty()) {
			entity.append("\r\n");
		}
		String ext = "";
		if(extendBaseEntity){
			entity.append("import com.cbbase.core.base.BaseEntity;").append("\r\n");
			entity.append("\r\n");
			ext = " extends BaseEntity";
		}
		entity.append("/**").append("\r\n");
		entity.append(" * @author ").append("\r\n");
		entity.append(" * @Description "+tableComment).append("\r\n");
		entity.append(" **/").append("\r\n");
		entity.append("public class "+entityName+ext+" {").append("\r\n");
		entity.append("\r\n");
		for(int i=0; i<columns.size(); i++){
			String column_name = StringUtil.formatCamel(columns.get(i).get("column_name").toString());
			String column_type = columns.get(i).get("data_type").toString().toUpperCase();
			Object data_scale = columns.get(i).get("data_scale");
			String conmment = getFieldConmment(i);
			column_type = DataTypeUtil.toJavaType(column_type, data_scale);
			if(extendBaseEntity && isBaseEntityField(column_name)){
				continue;
			}
			entity.append("	private "+column_type+" "+column_name+";//"+conmment).append("\r\n");
		}
		entity.append("\r\n");
		for(int i=0; i<columns.size(); i++){
			String column_name = StringUtil.formatCamel(columns.get(i).get("column_name").toString());
			String column_type = columns.get(i).get("data_type").toString().toUpperCase();
			Object data_scale = columns.get(i).get("data_scale");
			column_type = DataTypeUtil.toJavaType(column_type, data_scale);
			if(extendBaseEntity && isBaseEntityField(column_name)){
				continue;
			}
			entity.append("	public "+column_type+" get"+StringUtil.upperFirst(column_name)+"() {").append("\r\n");
			entity.append("		return "+column_name+";").append("\r\n");
			entity.append("	}").append("\r\n");
			entity.append("	public void set"+StringUtil.upperFirst(column_name)+"("+column_type+" "+column_name+") {").append("\r\n");
			entity.append("		this."+column_name+" = "+column_name+";").append("\r\n");
			entity.append("	}").append("\r\n");
		}
		entity.append("\r\n");
		entity.append("}").append("\r\n");
		
		
		String text = entity.toString();
		if(coverCore) {
			text = text.replaceAll("com.cbbase", basePackage);
		}
		if(showContent) {
			System.out.println("===============================");
			System.out.println(text);
		}
		
		if(writeFile) {
			String filePath = rootPath + javaPath + packageFolder + "\\entity\\";
			FileUtil.createFileByString(filePath + entityName +".java", text);
		}
	}
}
