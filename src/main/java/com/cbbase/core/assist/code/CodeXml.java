package com.cbbase.core.assist.code;

import com.cbbase.core.assist.database.DataTypeUtil;
import com.cbbase.core.jdbc.JdbcConnection;
import com.cbbase.core.tools.FileUtil;
import com.cbbase.core.tools.StringUtil;

public class CodeXml extends CodeAssist {

	public static void create(){

		StringBuffer xml = new StringBuffer("");
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\r\n");
		xml.append("<!DOCTYPE mapper  ").append("\r\n");
		xml.append("    PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"  ").append("\r\n");
		xml.append("    \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">  ").append("\r\n");
		xml.append("\r\n");
		xml.append("<mapper namespace=\""+packageName+".dao."+entity_name+"Dao\">").append("\r\n");
		xml.append("	<resultMap id=\"result\" type=\""+packageName+".entity."+entity_name+"\" > ").append("\r\n");
		for(int i=0; i<columns.size(); i++){
			String db_column = columns.get(i).get("column_name").toString().toLowerCase();
			String column_name = StringUtil.formatCamel(db_column);
			xml.append("		<result property=\""+column_name+"\" column=\""+db_column+"\" />").append("\r\n");
		}
		xml.append("	</resultMap>").append("\r\n");
		xml.append("\r\n");
		if(pageXml){
			xml.append("	<select id=\"selectPageList\" resultMap=\"result\" parameterType=\"com.cbbase.core.container.PageContainer\">").append("\r\n");
			if(JdbcConnection.isMysql(jdbc_name)){
				xml.append("		SELECT obj.* ").append("\r\n");
				xml.append("		FROM "+table+" obj ").append("\r\n");
				xml.append("		<where>").append("\r\n");
				xml.append("			<include refid=\"whereSql\"/>").append("\r\n");
				xml.append("		</where>").append("\r\n");
				xml.append("		<if test=\"sortField != null and sortField != '' and sortType != null\">").append("\r\n");
				xml.append("			order by obj.${sortField} ${sortType}").append("\r\n");
				xml.append("		</if>").append("\r\n");
				xml.append("		<if test=\"sortField == null\">").append("\r\n");
				xml.append("			order by obj.id desc").append("\r\n");
				xml.append("		</if>").append("\r\n");
				xml.append("		LIMIT ${startRow}, ${pageSize}").append("\r\n");
				
			}else if(JdbcConnection.isOracle(jdbc_name)){
				xml.append("		SELECT * FROM (SELECT A.*, ROWNUM RN FROM (").append("\r\n");
				xml.append("			SELECT * FROM "+table+" obj").append("\r\n");
				xml.append("			<where>").append("\r\n");
				xml.append("				<include refid=\"whereSql\"/>").append("\r\n");
				xml.append("			</where>").append("\r\n");
				xml.append("			<if test=\"sortField != null and sortField != '' and sortType != null\">").append("\r\n");
				xml.append("				order by obj.${sortField} ${sortType}").append("\r\n");
				xml.append("			</if>").append("\r\n");
				xml.append("			<if test=\"sortField == null\">").append("\r\n");
				xml.append("				order by obj.id desc").append("\r\n");
				xml.append("			</if>").append("\r\n");
				xml.append("			)A WHERE ROWNUM &lt;= ${endRow})").append("\r\n");
				xml.append("		WHERE RN &gt;= ${startRow}+1").append("\r\n");
			}
			xml.append("	</select>").append("\r\n");
			xml.append("\r\n");
			xml.append("	<select id=\"selectPageTotal\" resultType=\"int\" parameterType=\"com.cbbase.core.container.PageContainer\">").append("\r\n");
			xml.append("		select count(0) from "+table+" obj").append("\r\n");
			xml.append("		<where>").append("\r\n");
			xml.append("			<include refid=\"whereSql\"/>").append("\r\n");
			xml.append("		</where>").append("\r\n");
			xml.append("	</select>").append("\r\n");
			xml.append("\r\n");
			xml.append("	<sql id=\"whereSql\">").append("\r\n");
			for(int i=0; i<columns.size(); i++){
				String db_column = columns.get(i).get("column_name").toString().toLowerCase();
				String column_name = StringUtil.formatCamel(db_column);
				if(db_column.endsWith("id")){
					xml.append("		<if test=\"param."+column_name+" != null  and  param."+column_name+" != '' \">").append("\r\n");
					xml.append("			and obj."+db_column+" = #{param."+column_name+"}").append("\r\n");
					xml.append("		</if>").append("\r\n");
				}else if(db_column.endsWith("code") || db_column.endsWith("name")){
					xml.append("		<if test=\"param."+column_name+" != null  and  param."+column_name+" != '' \">").append("\r\n");
					xml.append("			and obj."+db_column+" like CONCAT('%', #{param."+column_name+"}, '%')").append("\r\n");
					xml.append("		</if>").append("\r\n");
				}
			}
			xml.append("	</sql>").append("\r\n");
			xml.append("\r\n");
		}
		xml.append("	<select id=\"selectList\" resultMap=\"result\" parameterType=\""+packageName+".entity."+entity_name+"\">").append("\r\n");
		xml.append("		select obj.* ").append("\r\n");
		xml.append("		from "+table+" obj ").append("\r\n");
		xml.append("		<where>").append("\r\n");
		for(int i=0; i<columns.size(); i++){
			String db_column = columns.get(i).get("column_name").toString().toLowerCase();
			String column_name = StringUtil.formatCamel(db_column);
			String column_type = columns.get(i).get("data_type").toString().toUpperCase();
			if(DataTypeUtil.isNumberType(column_type)){
				xml.append("			<if test=\""+column_name+" != null \">").append("\r\n");
			}else{
				xml.append("			<if test=\""+column_name+" != null  and  "+column_name+" != '' \">").append("\r\n");
			}
			xml.append("				and obj."+db_column+" = #{"+column_name+"}").append("\r\n");
			xml.append("			</if>").append("\r\n");
		}
		xml.append("		</where>").append("\r\n");
		xml.append("		order by obj.id desc").append("\r\n");
		xml.append("	</select>").append("\r\n");
		xml.append("\r\n");
		xml.append("	<update id=\"update\" parameterType=\""+packageName+".entity."+entity_name+"\">").append("\r\n");
		xml.append("	    update "+table+" set ").append("\r\n");
		for(int i=0; i<columns.size(); i++){
			String db_column = columns.get(i).get("column_name").toString().toLowerCase();
			String column_name = StringUtil.formatCamel(db_column);
			String str = "	    "+db_column+" = #{"+column_name+"},";
			if(i == columns.size()-1){
				str = "	    "+db_column+" = #{"+column_name+"}";
			}
			if("id".equals(column_name)){
				continue;
			}
			xml.append(str).append("\r\n");
		}
		xml.append("	    where id = #{id}").append("\r\n");
		xml.append("	</update>").append("\r\n");
		xml.append("\r\n");
		xml.append("	<select id=\"selectById\" resultMap=\"result\" parameterType=\"java.lang.String\">").append("\r\n");
		xml.append("		select obj.* ").append("\r\n");
		xml.append("		from "+table+" obj ").append("\r\n");
		xml.append("		where obj.id = #{id}").append("\r\n");
		xml.append("	</select>").append("\r\n");
		xml.append("\r\n");
		xml.append("	<insert id=\"insert\" parameterType=\""+packageName+".entity."+entity_name+"\">").append("\r\n");
		xml.append("	  	insert into "+table+"(");
		for(int i=0; i<columns.size(); i++){
			String db_column = columns.get(i).get("column_name").toString().toLowerCase();
			String str = db_column+", ";
			if(i == columns.size()-1){
				str = db_column;
			}
			xml.append(str);
		}
		xml.append(")").append("\r\n");
		xml.append("	  	values(");
		for(int i=0; i<columns.size(); i++){
			String db_column = columns.get(i).get("column_name").toString().toLowerCase();
			String column_name = StringUtil.formatCamel(db_column);
			String str = "#{"+column_name+"}, ";
			if(i == columns.size()-1){
				str = "#{"+column_name+"} ";
			}
			xml.append(str);
		}
		xml.append(")").append("\r\n");
		xml.append("	</insert>").append("\r\n");
		xml.append("\r\n");
		xml.append("	<delete id=\"delete\" parameterType=\"java.lang.String\">").append("\r\n");
		xml.append("		delete from "+table+"").append("\r\n");
		xml.append("		where id = #{id}").append("\r\n");
		xml.append("	</delete>").append("\r\n");
		xml.append("\r\n");
		xml.append("	<insert id=\"batchInsert\" parameterType=\"java.util.List\"> ").append("\r\n");
		xml.append("	  	insert into "+table+"(");
		for(int i=0; i<columns.size(); i++){
			String db_column = columns.get(i).get("column_name").toString().toLowerCase();
//			String column_name = StringUtil.formatCamel(db_column);
			String str = db_column+", ";
			if(i == columns.size()-1){
				str = db_column;
			}
			xml.append(str);
		}
		xml.append(")").append("\r\n");
		xml.append("	    values ").append("\r\n");
		xml.append("	    <foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\" >").append("\r\n");
		xml.append("	        (");
		for(int i=0; i<columns.size(); i++){
			String db_column = columns.get(i).get("column_name").toString().toLowerCase();
			String column_name = StringUtil.formatCamel(db_column);
			String str = "#{item."+column_name+"}, ";
			if(i == columns.size()-1){
				str = "#{item."+column_name+"} ";
			}
			xml.append(str);
		}
		xml.append(")").append("\r\n");
		xml.append("	    </foreach>").append("\r\n");
		xml.append("	</insert>").append("\r\n");
		xml.append("\r\n");
		xml.append("	<delete id=\"batchDelete\" parameterType=\"java.util.List\">").append("\r\n");
		xml.append("		delete from "+table+"").append("\r\n");
		xml.append("		where id in").append("\r\n");
		xml.append("		<foreach collection=\"list\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">").append("\r\n");
		xml.append("			#{item}").append("\r\n");
		xml.append("		</foreach>").append("\r\n");
		xml.append("	</delete>").append("\r\n");
		xml.append("</mapper >").append("\r\n");
		xml.append("\r\n");
		
		if(showContent) {
			System.out.println("===============================");
			System.out.println(xml.toString());
		}
		
		if(writeFile) {
			String file_path = root_path + java_path + package_folder + "\\dao\\";
			FileUtil.createFileByString(file_path + entity_name +"Dao.xml", xml.toString());
		}
		
		
	}
}
