package com.cbbase.core.extension.taglib.impl;

import com.cbbase.core.common.SessionConstants;
import com.cbbase.core.common.GlobalManager;
import com.cbbase.core.extension.taglib.BaseTaglib;
import com.cbbase.core.tools.RandomUtil;

/**
 * 
 * @author changbo
 *
 */
public class TokenTag extends BaseTaglib {
	
    public void doShow() {
		String token = RandomUtil.hex(32);
		GlobalManager.setSession(SessionConstants.FORM_TOKEN, token);
		writeJsp("<input type=\"hidden\" id=\""+SessionConstants.FORM_TOKEN+"\" name=\""+SessionConstants.FORM_TOKEN+"\" value=\""+token+"\"/>");
	}
	
}
