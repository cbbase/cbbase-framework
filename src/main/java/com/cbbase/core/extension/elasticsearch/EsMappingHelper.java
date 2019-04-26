package com.cbbase.core.extension.elasticsearch;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cbbase.core.tools.StringUtil;

/**
 * 	生成ES字段映射
 * @author changbo
 *
 */
public class EsMappingHelper {
	
	public static Map<String, Object> getMapping(Class<?> clazz){
		Map<String, String> config = new HashMap<>();
		config.put("searchEnable", "true");
		config.put("searchTextMaxLength", "256");
		return getMapping(clazz, config);
	}
	
	public static Map<String, Object> getMapping(Class<?> clazz, Map<String, String> config){
		Map<String, Object> properties = new HashMap<>();
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields) {
			field.setAccessible(true);
			if(field.getType().isAssignableFrom(String.class)) {
				properties.put(field.getName(), getStringMap(config));
			}
			if(field.getType().isAssignableFrom(Short.class)) {
				properties.put(field.getName(), getFieldMap("short"));
			}
			if(field.getType().isAssignableFrom(Integer.class)) {
				properties.put(field.getName(), getFieldMap("integer"));
			}
			if(field.getType().isAssignableFrom(Long.class)) {
				properties.put(field.getName(), getFieldMap("long"));
			}
			if(field.getType().isAssignableFrom(Float.class)) {
				properties.put(field.getName(), getFieldMap("float"));
			}
			if(field.getType().isAssignableFrom(Double.class)) {
				properties.put(field.getName(), getFieldMap("double"));
			}
			if(field.getType().isAssignableFrom(BigDecimal.class)) {
				properties.put(field.getName(), getFieldMap("double"));
			}
			if(field.getType().isAssignableFrom(Date.class)) {
				properties.put(field.getName(), getFieldMap("long"));
			}
			if(field.getType().isAssignableFrom(List.class)) {
				Type genericType = field.getGenericType();
				if(genericType instanceof ParameterizedType){
	                ParameterizedType pt = (ParameterizedType) genericType;
	                //得到泛型里的class类型对象
	                Class<?> parameterClazz = (Class<?>)pt.getActualTypeArguments()[0];
	                Map<String, Object> subMap = getMapping(parameterClazz);
					properties.put(field.getName(), subMap);
				}
			}
			field.setAccessible(false);
		}
		Map<String, Object> mapping = new HashMap<>();
		mapping.put("properties", properties);
		
		return mapping;
	}
	
	private static Map<String, Object> getFieldMap(String type){
		Map<String, Object> mapping = new HashMap<>();
		mapping.put("type", type);
		mapping.put("index", true);
		return mapping;
	}
	
	private static Map<String, Object> getStringMap(Map<String, String> config){
		boolean searchEnable = StringUtil.toBoolean(config.get("searchEnable"));
		if(!searchEnable) {
			Map<String, Object> mapping = new HashMap<>();
			mapping.put("type", "text");
			mapping.put("index", false);
			return mapping;
		}
		
		Map<String, Object> keyword = new HashMap<>();
		keyword.put("type", "keyword");
		keyword.put("ignore_above", StringUtil.toInt(config.get("searchTextMaxLength")));
		
		Map<String, Object> fields = new HashMap<>();
		fields.put("keyword", keyword);
		
		Map<String, Object> mapping = new HashMap<>();
		mapping.put("type", "text");
		mapping.put("fields", fields);
		return mapping;
	}
	
}
