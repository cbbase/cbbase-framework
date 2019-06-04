package com.cbbase.core.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import com.cbbase.core.annotation.Text;
import com.cbbase.core.container.SelectVO;

/**
 * 	常量类工具
 * @author changbo
 *
 */
public class ConstantManager {
	
    public static List<SelectVO> getSelectList(Class<?> clazz) {
        List<SelectVO> list = new ArrayList<>();
        try {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Text.class)) {
                    Text annotation = field.getAnnotation(Text.class);
                    list.add(new SelectVO(String.valueOf(field.get(null)), annotation.value()));
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return list;
    }
    
    public static String getText(Class<?> clazz, Object key) {
        try {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Text.class)) {
                    Text annotation = field.getAnnotation(Text.class);
                    if(String.valueOf(field.get(null)).equals(key.toString())) {
                    	return annotation.value();
                    }
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    	
    }

}