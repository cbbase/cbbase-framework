package com.cbbase.core.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.cbbase.core.common.AuthManager;

/**
 * 权限标签,在拥有权限时,显示内部标签
 * @author changbo
 *
 */
public class AuthTag extends BodyTagSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String auth;
	
	@Override
	public int doStartTag() throws JspException {
		if(!AuthManager.checkAuth(auth)) {
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
