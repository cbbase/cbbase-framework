package com.cbbase.core.jdbc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author changbo
 * 
 */
public class CallParameter {
	
	private Map<Integer, Object> params = new HashMap<Integer, Object>();
	private Map<Integer, Integer> outType = new HashMap<Integer, Integer>();
	
	private Map<Integer, Object> outValue = new HashMap<Integer, Object>();
	private Map<Integer, List<Object>> outValueList = new HashMap<Integer, List<Object>>();
	
	public CallParameter(){
		
	}

	public static CallParameter create(){
		return new CallParameter();
	}
	
	public CallParameter addParam(Integer index, Object obj){
		if(params == null){
			params = new HashMap<Integer, Object>();
		}
		params.put(index, obj);
		return this;
	}
	
	public CallParameter addOutType(Integer index, Integer obj){
		if(outType == null){
			outType = new HashMap<Integer, Integer>();
		}
		outType.put(index, obj);
		return this;
	}

	public Map<Integer, Object> getParams() {
		return params;
	}

	public void setParams(Map<Integer, Object> params) {
		this.params = params;
	}

	public Map<Integer, Integer> getOutType() {
		return outType;
	}

	public void setOutType(Map<Integer, Integer> outType) {
		this.outType = outType;
	}

	public Map<Integer, Object> getOutValue() {
		return outValue;
	}
	
	public void setOutValue(Map<Integer, Object> outValue) {
		this.outValue = outValue;
	}

	public Map<Integer, List<Object>> getOutValueList() {
		return outValueList;
	}

	public void setOutValueList(Map<Integer, List<Object>> outValueList) {
		this.outValueList = outValueList;
	}
	
}
