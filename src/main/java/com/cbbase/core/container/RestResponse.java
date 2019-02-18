package com.cbbase.core.container;

import com.cbbase.core.tools.JsonUtil;
import com.cbbase.core.tools.StringUtil;

public class RestResponse {

	public static final int SUCCESS_CODE = 0;
	public static final int FAIL_CODE = 1;
	
	private int code = 0;
	private String msg = null;
	private Object data = null;
	
	public RestResponse() {
		super();
	}
	
	public RestResponse(Object data) {
		super();
		this.data = data;
	}
	
	public RestResponse(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	
	public RestResponse(int code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public static RestResponse fromJson(String json) {
		if(StringUtil.isEmpty(json)) {
			return null;
		}
		try {
			return JsonUtil.toObject(json, RestResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
