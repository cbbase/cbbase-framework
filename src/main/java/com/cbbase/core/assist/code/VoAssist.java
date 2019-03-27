package com.cbbase.core.assist.code;

import java.util.List;

import com.cbbase.core.tools.ObjectUtil;
import com.cbbase.core.tools.StringUtil;

public class VoAssist {
	
	public static void voMapper(Class<?> clazz) {
		List<String> list = ObjectUtil.getFields(clazz);
		StringBuffer sb = new StringBuffer("\n");
		sb.append("	<resultMap id=\"result\" type=\""+clazz.getName()+"\" >").append("\n");
		for(String field : list) {
			sb.append("		<result property=\""+field+"\" column=\""+StringUtil.camelToColumn(field)+"\" />").append("\n");
		}
		sb.append("	</resultMap>").append("\n");
		System.out.println(sb.toString());
	}
	
	public static void voColumns(Class<?> clazz) {
		List<String> list = ObjectUtil.getFields(clazz);
		StringBuffer sb = new StringBuffer("\n");
		for(String field : list) {
			sb.append(StringUtil.camelToColumn(field) + " as " + field + ",\n");
		}
		sb.append("\n");
		System.out.println(sb.toString());
	}

}
