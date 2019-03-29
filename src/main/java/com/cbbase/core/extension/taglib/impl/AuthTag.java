package com.cbbase.core.extension.taglib.impl;

import javax.servlet.jsp.JspException;

import com.cbbase.core.common.GlobalManager;
import com.cbbase.core.extension.taglib.BaseBodyTaglib;

/**
 * 权限标签,在拥有权限时,显示内部标签
 * @author changbo
 *
 */
public class AuthTag extends BaseBodyTaglib {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String auth;
	
	@Override
	public int doStartTag() throws JspException {
		if(!GlobalManager.checkAuth(auth)) {
			return SKIP_PAGE;
		}
		return EVAL_BODY_INCLUDE;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	
}
