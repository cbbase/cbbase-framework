package com.cbbase.core.encrypt;

import com.cbbase.core.tools.StringUtil;

public class MAC {

    public static byte[] clacMac(String hexKey, String hexInput) {  
    	return clacMac(StringUtil.hexStrToBytes(hexKey), StringUtil.hexStrToBytes(hexInput));
	}
    
    public static String clacMacStr(String hexKey, String hexInput) {  
    	return StringUtil.bytesToHexStr(clacMac(StringUtil.hexStrToBytes(hexKey), StringUtil.hexStrToBytes(hexInput)));
	}

	public static byte[] clacMac(byte[] seed, byte[] data) {
		int dataLen = data.length;
		byte[] source;
		if (dataLen % 8 == 0) {
			source = new byte[dataLen];
		} else {
			source = new byte[(dataLen / 8 + 1) * 8];
		}
		System.arraycopy(data, 0, source, 0, dataLen);
		for (int i = dataLen; i < source.length; i++) {
			source[i] = 0;
		}
		int block = source.length / 8;

		byte[] result = new byte[8];
		byte[] temp = new byte[8];
		for (int i = 0; i < block; i++) {
			for (int j = 0; j < 8; j++) {
				temp[j] = ((byte) (temp[j] ^ source[(i * 8 + j)]));
			}
		}
		String str = StringUtil.bytesToHexStr(temp).toUpperCase();
		byte[] temp16 = new byte[16];
		temp16 = str.getBytes();
		byte[] s1 = new byte[8];
		byte[] s2 = new byte[8];
		System.arraycopy(temp16, 0, s1, 0, 8);
		System.arraycopy(temp16, 8, s2, 0, 8);
		temp = TripleDES.encrypt(seed, s1);
		for (int i = 0; i < 8; i++) {
			s1[i] = ((byte) (temp[i] ^ s2[i]));
		}
		result = TripleDES.encrypt(seed, s1);
		byte[] macResult = new byte[8];
		System.arraycopy(result, 0, macResult, 0, 8);
		String hexStr = StringUtil.bytesToHexStr(macResult).toUpperCase();
		return hexStr.substring(0, 8).getBytes();
	}
}
