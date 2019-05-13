package com.cbbase.core.extension.validator;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cbbase.core.tools.FileUtil;
import com.cbbase.core.tools.JsonUtil;
import com.cbbase.core.tools.PropertiesHelper;
import com.cbbase.core.tools.StringUtil;

public class ValidatorManager {
	
	public static String check(HttpServletRequest request, String validator) {
		String file = PropertiesHelper.class.getClassLoader().getResource("validator/"+validator+".json").getFile();
		String json = FileUtil.readAsString(file);
		List<FieldValidate> list = JsonUtil.toList(json, FieldValidate.class);
		Map<String, String[]> param = request.getParameterMap();
		for(FieldValidate fv : list) {
			if(fv.getSubField() != null && fv.getSubField().size() > 0) {
				String[] values = param.get(fv.getCode());
				for(String value : values) {
					for(FieldValidate subFv : fv.getSubField()) {
						Map<String, String> subMap = JsonUtil.toMap(value);
						String result = checkField(subMap.get(fv.getCode()), subFv);
						if(result != null) {
							return result;
						}
					}
				}
			}
			String result = checkField(request.getParameter(fv.getCode()), fv);
			if(result != null) {
				return result;
			}
		}
		return null;
	}

	public static String checkField(String value, FieldValidate fv) {
		
		if(fv.getRequired()) {
			if(StringUtil.isEmpty(value)) {
				return fv.getName()+"为空";
			}
		}
		
		if(StringUtil.isEmpty(value)) {
			return null;
		}
		
		if(fv.getMaxLength() > 0) {
			if(value.length() > fv.getMaxLength()) {
				return fv.getName()+"最大长度为"+fv.getMaxLength();
			}
		}
		
		if(StringUtil.hasValue(fv.getRegex())) {
			if(!value.matches(fv.getRegex())) {
				if(StringUtil.hasValue(fv.getRegexMsg())) {
					return fv.getRegexMsg();
				}else {
					return fv.getName()+"格式错误";
				}
			}
		}
		return null;
	}

}
