package com.cbbase.core.tools;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cbbase.core.annotation.Text;


public class ConstantUtil {
    public static List<Map<String, String>> getList(Class<?> clazz) {
        List<Map<String, String>> list = new ArrayList<>();
        try {
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(Text.class)) {
                    Text annotation = field.getAnnotation(Text.class);
                    Map<String, String> map = new HashMap<>(2);
                    map.put("key", String.valueOf(field.get(null)));
                    map.put("text", annotation.value());
                    list.add(map);
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return list;
    }
}
