package com.cbbase.core.tools;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class EntityUtil {
    
    public static void mergeField(Object fromEntity, Object toEntity){
    	List<String> fieldList = ObjectUtil.getFields(fromEntity.getClass());
    	for(String field : fieldList){
    		Object fieldValue = ObjectUtil.getFieldValue(fromEntity, field);
    		if(fieldValue != null && fieldValue.toString().trim().length() > 0){
        		ObjectUtil.setFieldValue(toEntity, field, fieldValue);
    		}
    	}
    }
    
    public static void mergeField(Object fromEntity, Object toEntity, String[] filedList){
    	List<String> fieldList = ObjectUtil.getFields(fromEntity.getClass());
    	for(String field : fieldList){
    		Object fieldValue = ObjectUtil.getFieldValue(fromEntity, field);
    		ObjectUtil.setFieldValue(toEntity, field, fieldValue);
    	}
    }
    
    public static boolean checkEmptyField(Object entity, String[] fields){
    	if(entity == null || fields == null){
    		return false;
    	}

    	for(String field : fields){
    		Object fieldValue = ObjectUtil.getFieldValue(entity, field);
    		if(StringUtil.isEmpty(fieldValue)){
        		return false;
    		}
    	}
    	return true;
    }
    
	public static void mapToEntity(Map<String, ?> map, Object obj){
		if(map == null || map.size() == 0){
			return;
		}
		for(Entry<String, ?> entry : map.entrySet()){
			ObjectUtil.setFieldValue(obj, entry.getKey(), entry.getValue());
		}
	}

	public static long getEentityCRC32(Object o, String[] fields){
		if(o == null || fields == null){
			return 0;
		}
		String text = "";
		for(String field : fields){
			Object f = ObjectUtil.getFieldValue(o, field);
			String s = (f == null) ? "" : f.toString();
			text = text + s;
		}
		return HashUtil.getCRC32(text);
	}
}
