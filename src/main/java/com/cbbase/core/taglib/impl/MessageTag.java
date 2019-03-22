package com.cbbase.core.taglib.impl;

import com.cbbase.core.common.BeanFactory;
import com.cbbase.core.handler.MessageTranslateHandler;
import com.cbbase.core.taglib.base.BaseTaglib;

public class MessageTag extends BaseTaglib {
	
	private String key;

	@Override
	public void doShow() {
		MessageTranslateHandler mth = BeanFactory.getBean(MessageTranslateHandler.class);
		writeJsp(mth.translate(key));
	}
	
	

}
