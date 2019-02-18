package com.cbbase.core.encrypt;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import com.cbbase.core.tools.StringUtil;

/**
 * 
 * @author changbo
 *
 */
public class DSA {
	
	public static final String KEY_ALGORITHM = "DSA";
	public static final String SIGNATURE_ALGORITHM = "DSA";
	
	public static final String PUBLIC_KEY = "DSAPublicKey";
	public static final String PRIVATE_KEY = "DSAPrivateKey";
	
    /** 密钥长度必须是64的倍数，在512~65536之间。默认是1024 */
    public static final int KEY_SIZE = 1024;
    
	public static Map<String, String> createKeyMap() {
		return createKeyMap(KEY_SIZE, null);
	}
	
	public static Map<String, String> createKeyMap(int keySize, String seed) {
		try{
			KeyPairGenerator keygen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			SecureRandom secureRandom = new SecureRandom();
			if(seed != null){
				secureRandom.setSeed(seed.getBytes());
			}
			keygen.initialize(keySize, secureRandom);
			
			KeyPair keys = keygen.genKeyPair();
			PrivateKey privateKey = keys.getPrivate();
			PublicKey publicKey = keys.getPublic();
	
			Map<String, String> map = new HashMap<String, String>();
			map.put(PUBLIC_KEY, StringUtil.encodeBase64(publicKey.getEncoded()));
			map.put(PRIVATE_KEY, StringUtil.encodeBase64(privateKey.getEncoded()));
			return map;
		}catch(Exception e){
			return null;
		}
	}
	
	public static String sign(String data, String privateKey) {
		return sign(data, privateKey, "DSA");
	}

	public static String sign(String data, String privateKey, String signatureAlgorithm) {
		try{
			byte[] keyBytes = StringUtil.decodeBase64(privateKey);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
			PrivateKey priKey = factory.generatePrivate(keySpec);
			Signature signature = Signature.getInstance(signatureAlgorithm);
			signature.initSign(priKey);
			signature.update(data.getBytes("UTF-8"));
			return StringUtil.encodeBase64(signature.sign());
		}catch(Exception e){
			return null;
		}
	}
	
	public static boolean verify(String data, String publicKey, String sign) {
		return verify(data, publicKey, sign, "DSA");
	}
	
	public static boolean verify(String data, String publicKey, String sign, String signatureAlgorithm) {
		try{
			byte[] keyBytes = StringUtil.decodeBase64(publicKey);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			PublicKey pubKey = keyFactory.generatePublic(keySpec);
	
			Signature signature = Signature.getInstance(signatureAlgorithm);
			signature.initVerify(pubKey);
			signature.update(data.getBytes("UTF-8"));
	
			return signature.verify(StringUtil.decodeBase64(sign));
		}catch(Exception e){
			return false;
		}
		
	}
}