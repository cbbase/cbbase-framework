package com.cbbase.core.tools;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {
	
	public static List<?> get(List<?> list, String attr, Object attrValue){
		List<Object> result = new ArrayList<Object>();
		for(Object obj : list){
			if(obj != null && attrValue != null){
				if(attrValue.equals(ObjectUtil.getFieldValue(obj, attr))){
					result.add(obj);
				}
			}
		}
		return result;
	}

}
