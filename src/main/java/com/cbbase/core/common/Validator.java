package com.cbbase.core.common;

import com.cbbase.core.tools.StringUtil;

/**
 * 
 * @author changbo
 *
 */
public class Validator {
	
	public static void isTrue(boolean b, String msg) {
		if(b) {
			throw new IllegalArgumentException(msg);
		}
	}
	
	public static void isNull(Object obj, String msg) {
		if(obj == null) {
			throw new IllegalArgumentException(msg);
		}
	}
	
	public static void isEmpty(Object obj, String msg) {
		if(StringUtil.isEmpty(obj)) {
			throw new IllegalArgumentException(msg);
		}
	}
}
