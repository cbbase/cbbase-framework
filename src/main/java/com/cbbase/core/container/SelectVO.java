package com.cbbase.core.container;

/**
 * 	下拉框数据
 * @author changbo
 *
 */
public class SelectVO {
	
	//值
	private String value;
	
	//显示数据
	private String text;
	
	public SelectVO() {
		super();
	}
	
	public SelectVO(String value, String text) {
		super();
		this.value = value;
		this.text = text;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
