package com.cbbase.core.extension.validator;

import java.util.List;

public class FieldValidate {
	
	private String code;
	private String name;
	private boolean required;
	private int minLength;
	private int maxLength;
	private String regex;
	private String regexMsg;
	private List<FieldValidate> subField;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean getRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public int getMinLength() {
		return minLength;
	}
	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}
	public int getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	public String getRegex() {
		return regex;
	}
	public void setRegex(String regex) {
		this.regex = regex;
	}
	public String getRegexMsg() {
		return regexMsg;
	}
	public void setRegexMsg(String regexMsg) {
		this.regexMsg = regexMsg;
	}
	public List<FieldValidate> getSubField() {
		return subField;
	}
	public void setSubField(List<FieldValidate> subField) {
		this.subField = subField;
	}

}
