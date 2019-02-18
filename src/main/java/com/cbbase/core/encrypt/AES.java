package com.cbbase.core.encrypt;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.cbbase.core.tools.StringUtil;

/**
 * 16位定长秘钥
 * @author changbo
 * 
 */
public class AES {

	public static final String KEY_ALGORITHM = "AES";
	public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    public static final int KEY_SIZE = 16;
    public static final String CHARACTER_SET = "UTF-8";
	

	public static String encrypt(String data, String key) {
		return encrypt(data, key, CIPHER_ALGORITHM, KEY_SIZE);
	}
    
	public static String encrypt(String data, String key, String cipherAlgorithm, int keySize) {
		try {
			SecretKeySpec skeySpec = getKey(key, keySize);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(data.getBytes(CHARACTER_SET));
			return StringUtil.encodeBase64(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decrypt(String data, String key) {
		return decrypt(data, key, CIPHER_ALGORITHM, KEY_SIZE);
	}
	
	public static String decrypt(String data, String key, String cipherAlgorithm, int keySize) {
		try {
			SecretKeySpec skeySpec = getKey(key, keySize);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] original = cipher.doFinal(StringUtil.decodeBase64(data));
			return new String(original, CHARACTER_SET);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 16位定长秘钥,不足补0
	 * @param strKey
	 * @return
	 */
	private static SecretKeySpec getKey(String strKey, int keySize) {
		byte[] keyBytes = strKey.getBytes();
		byte[] key16 = new byte[keySize];
		for (int i = 0; i < keyBytes.length && i < key16.length; i++) {
			key16[i] = keyBytes[i];
		}
		return new SecretKeySpec(key16, KEY_ALGORITHM);
	}

}
