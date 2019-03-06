package com.cbbase.core.tools;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import com.cbbase.core.annotation.Text;


public class ConstantUtil {
    public static List<SelectVO> getList(Class<?> clazz) {
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
}

class SelectVO {
	
	private String text;
    
	private String value;
	
	public SelectVO(String text, String value) {
		super();
		this.text = text;
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}