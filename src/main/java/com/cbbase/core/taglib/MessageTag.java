package com.cbbase.core.taglib;

import java.io.IOException;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.cbbase.core.common.BeanFactory;
import com.cbbase.core.handler.MessageTranslateHandler;

public class MessageTag extends SimpleTagSupport {
	
	private String key;

	@Override
	public void doTag() {
		MessageTranslateHandler mth = BeanFactory.getBean(MessageTranslateHandler.class);
		writeJsp(mth.translate(key));
	}
	
	protected void writeJsp(String str){
		PageContext pageContext = (PageContext)getJspContext();
		try {
			pageContext.getOut().print(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
