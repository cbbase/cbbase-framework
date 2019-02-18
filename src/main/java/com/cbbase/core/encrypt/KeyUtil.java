package com.cbbase.core.encrypt;

import com.cbbase.core.tools.StringUtil;

public class KeyUtil {
	
	public static String encryptKey(String data, String key){
		String key32 = key;
		String key16 = "0000000000000000";
		if(key == null){
			key32 = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";
		}
		String value1 = TripleDES.encrypt(key32, data);
		String value2 = TripleDES.encrypt(data, key16);
		return value1 + value2.substring(0, 8);
	}
	
	public static String decryptKey(String data, String key){
		String value = TripleDES.decrypt(key, data.substring(0, 32));
		String check1 = TripleDES.encrypt(value, "0000000000000000").substring(0, 8);
		String check2 = data.substring(32, 40);
		if(!StringUtil.isEqualIgnoreCase(check1, check2)){
			return null;
		}
		return value;
	}
	
}
