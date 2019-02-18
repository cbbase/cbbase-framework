package com.cbbase.core.tools;

public class BCDUtil {

	/**
	 *	 字符串转BCD编码
	 * @param asc
	 * @return
	 */
	public static byte[] strToBCD(String asc) {
		int len = asc.length();
		int mod = len % 2;
		if (mod != 0) {
			asc = "0" + asc;
			len = asc.length();
		}
		byte ascBytes[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}
		byte result[] = new byte[len];
		ascBytes = asc.getBytes();
		int j, k;
		for (int p = 0; p < asc.length() / 2; p++) {
			if ((ascBytes[2 * p] >= '0') && (ascBytes[2 * p] <= '9')) {
				j = ascBytes[2 * p] - '0';
			} else if ((ascBytes[2 * p] >= 'a') && (ascBytes[2 * p] <= 'z')) {
				j = ascBytes[2 * p] - 'a' + 0x0a;
			} else {
				j = ascBytes[2 * p] - 'A' + 0x0a;
			}
			if ((ascBytes[2 * p + 1] >= '0') && (ascBytes[2 * p + 1] <= '9')) {
				k = ascBytes[2 * p + 1] - '0';
			} else if ((ascBytes[2 * p + 1] >= 'a') && (ascBytes[2 * p + 1] <= 'z')) {
				k = ascBytes[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = ascBytes[2 * p + 1] - 'A' + 0x0a;
			}
			int a = (j << 4) + k;
			byte b = (byte) a;
			result[p] = b;
		}
		return result;
	}

	/**
	 * BCD编码转字符串
	 * 
	 * @param buffer
	 * @return
	 */
	public static String bcdToStr(byte[] bytes) {
		StringBuilder temp = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
			temp.append((byte) (bytes[i] & 0x0f));
		}
		return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp.toString().substring(1) : temp.toString();
	}
	
}