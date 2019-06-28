package com.cbbase.core.taglib.base;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 
 * @author changbo
 *
 */
public abstract class BaseTaglib extends SimpleTagSupport {
	
	protected PageContext getPageContext(){
		return (PageContext)getJspContext();
	}
	protected HttpServletRequest getHttpServletRequest(){
		return (HttpServletRequest)getPageContext().getRequest();
	}
	protected HttpSession getHttpSession(){
		return getHttpServletRequest().getSession();
	}
	protected JspWriter getJspWriter(){
		return getJspContext().getOut();
	}
	protected void writeJsp(String str){
		try {
			getJspWriter().print(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	protected void writeBody(){
		try {
			getJspBody().invoke(getJspWriter());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected String getBasePath(){
		return getHttpServletRequest().getContextPath();
	}
	

	@Override
    public void doTag() {
		try {
			doShow();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public abstract void doShow();
}
