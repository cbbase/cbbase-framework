package com.cbbase.core.extension.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * 
 * @author changbo
 *
 */
public abstract class BaseBodyTaglib extends BodyTagSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected HttpServletRequest getHttpServletRequest(){
		return (HttpServletRequest)pageContext.getRequest();
	}
	protected HttpSession getHttpSession(){
		return getHttpServletRequest().getSession();
	}
	protected void writeJsp(String str){
		try {
			pageContext.getOut().print(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected String getBasePath(){
		return getHttpServletRequest().getContextPath();
	}
	
}
