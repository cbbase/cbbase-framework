package com.cbbase.core.encrypt;  
  
import java.security.KeyFactory;  
import java.security.KeyPair;  
import java.security.KeyPairGenerator;  
import java.security.NoSuchAlgorithmException;  
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;  
import java.security.interfaces.RSAPublicKey;  
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;  
import java.util.Map;

import javax.crypto.Cipher;  

import com.cbbase.core.tools.StringUtil;

/**
 * 
 * @author changbo
 *
 */
public class RSA {
	
    public static final String KEY_ALGORITHM = "RSA";
    
	public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
	
    public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    
    public static final String PUBLIC_KEY = "RSAPublicKey";
    public static final String PRIVATE_KEY = "RSAPrivateKey";
    
    /** RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024 */
    public static final int KEY_SIZE = 1024;
    public static final String CHARACTER_SET = "UTF-8";
    
    public static Map<String, String> createKeyMap(){
    	return createKeyMap(KEY_SIZE, null);
    }
    
    public static Map<String, String> createKeyMap(int keySize, String seed){
        Map<String, String> keyMap = new HashMap<String, String>();
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
    		SecureRandom secureRandom = new SecureRandom();
    		if(seed != null){
    			secureRandom.setSeed(seed.getBytes());
    		}
            keyPairGenerator.initialize(keySize, secureRandom);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            keyMap.put(PUBLIC_KEY, StringUtil.encodeBase64(publicKey.getEncoded()));
            keyMap.put(PRIVATE_KEY, StringUtil.encodeBase64(privateKey.getEncoded()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keyMap;
    }
    
    public static String encrypt(String data, String publicKey){
    	return encrypt(data, publicKey, CIPHER_ALGORITHM);
    }
    
    public static String encrypt(String data, String publicKey, String cipherAlgorithm){
    	String r = null;
        try {
        	X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(StringUtil.decodeBase64(publicKey));
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey public_key = factory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, public_key);
            r = StringUtil.encodeBase64(cipher.doFinal(data.getBytes(CHARACTER_SET)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return r;
    }

    public static String decrypt(String data, String privateKey){
    	return decrypt(data, privateKey, CIPHER_ALGORITHM);
    }

    public static String decrypt(String data, String privateKey, String cipherAlgorithm){
    	String r = null;
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(StringUtil.decodeBase64(privateKey));
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey private_key = factory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, private_key);
            r = new String(cipher.doFinal(StringUtil.decodeBase64(data)), CHARACTER_SET);
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return r;
    }

    /**
     * SHA1withRSA
     */
	public static String sign(String data, String privateKey) {
		return sign(data, privateKey, SIGNATURE_ALGORITHM);
	}
	
    /**
     * signatureAlgorithm: MD5withRSA, SHA1withRSA
     */
	public static String sign(String data, String privateKey, String signatureAlgorithm) {
		try {
			byte[] keyBytes = StringUtil.decodeBase64(privateKey);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
			PrivateKey priKey = factory.generatePrivate(keySpec);
			Signature signature = Signature.getInstance(signatureAlgorithm);
			signature.initSign(priKey);
			signature.update(data.getBytes());
			return StringUtil.encodeBase64(signature.sign());
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

    /**
     * SHA1withRSA
     */
	public static boolean verify(String data, String publicKey, String sign) {
		return verify(data, publicKey, sign, SIGNATURE_ALGORITHM);
	}

    /**
     * signatureAlgorithm: MD5withRSA, SHA1withRSA
     */
	public static boolean verify(String data, String publicKey, String sign, String signatureAlgorithm) {
		try {
			byte[] keyBytes = StringUtil.decodeBase64(publicKey);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			PublicKey pubKey = keyFactory.generatePublic(keySpec);
	
			Signature signature = Signature.getInstance(signatureAlgorithm);
			signature.initVerify(pubKey);
			signature.update(data.getBytes());
	
			return signature.verify(StringUtil.decodeBase64(sign));
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}  