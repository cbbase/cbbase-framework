package com.cbbase.core.encrypt;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.cbbase.core.tools.StreamUtil;
import com.cbbase.core.tools.StringUtil;

/**
 * 
 * @author changbo
 *
 */
public class CertFileUtil {
	
	public static final String PUBLIC_KEY = "PublicKey";
	public static final String PRIVATE_KEY = "PrivateKey";
	
	static{
		Security.addProvider(new BouncyCastleProvider());
	}
	
	public static String keyToBase64(Key key){
		return StringUtil.encodeBase64(key.getEncoded());
	}
	
	public static PublicKey readCer(InputStream is){
		try {
			CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
			Certificate Cert = certificatefactory.generateCertificate(is);
			return Cert.getPublicKey();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
	    	StreamUtil.close(is);
		}
		return null;
	}
	
	public static PublicKey readCer(String fileName){
		try {
			return readCer(new FileInputStream(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static PublicKey readCer(byte[] bytes){
		try {
			return readCer(new ByteArrayInputStream(bytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static PublicKey readPublicPem(String fileName, String keyAlgorithm) {
		try {
			return readPublicPem(new FileInputStream(fileName), keyAlgorithm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static PublicKey readPublicPem(byte[] bytes, String keyAlgorithm) {
		try {
			return readPublicPem(new ByteArrayInputStream(bytes), keyAlgorithm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static PublicKey readPublicPem(InputStream in, String keyAlgorithm) {
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) != '-'){
					sb.append(readLine);
					sb.append('\r');
				}
			}
			X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(StringUtil.decodeBase64(sb.toString()));
			KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
			return keyFactory.generatePublic(pubX509);
		}catch(Exception e){
			e.printStackTrace();
	    }finally{
	    	StreamUtil.close(in);
	    }
		return null;
    }
	
	public static PrivateKey readPrivatePem(byte[] bytes, String keyAlgorithm, String password) {
		try {
			return readPrivatePem(new ByteArrayInputStream(bytes), keyAlgorithm, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static PrivateKey readPrivatePem(String fileName, String keyAlgorithm, String password) {
		try {
			return readPrivatePem(new FileInputStream(fileName), keyAlgorithm, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static PrivateKey readPrivatePem(InputStream in, String keyAlgorithm, String password) {
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) != '-'){
					sb.append(readLine);
					sb.append('\r');
				}
			}
		    PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(StringUtil.decodeBase64(sb.toString()));
		    KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
			return keyFactory.generatePrivate(priPKCS8);
		}catch(Exception e){
			e.printStackTrace();
	    }finally{
	    	StreamUtil.close(in);
	    }
		return null;
	}
	
	public static Map<String, Key> readPfx(InputStream is, String password) {
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			char[] nPassword = null;
			if(password == null || password.trim().length() == 0) {
				nPassword = null;
			}else{
				nPassword = password.toCharArray();
			}
			ks.load(is, nPassword);
			is.close();
			Enumeration<String> enumas = ks.aliases();
			String keyAlias = null;
			if (enumas.hasMoreElements()) {
				keyAlias = enumas.nextElement();
			}
			PrivateKey privateKey = (PrivateKey) ks.getKey(keyAlias, nPassword);
			Certificate cert = ks.getCertificate(keyAlias);
			PublicKey publicKey = cert.getPublicKey();
			
			Map<String, Key> map = new HashMap<String, Key>();
			map.put(PUBLIC_KEY, publicKey);
			map.put(PRIVATE_KEY, privateKey);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Map<String, Key> readPfx(byte[] bytes, String password) {
		try {
			return readPfx(new ByteArrayInputStream(bytes), password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Map<String, Key> readPfx(String pfxFile, String password) {
		try {
			return readPfx(new FileInputStream(pfxFile), password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public static KeyStore loadKeyStore(String certFile, String certKey){
		FileInputStream fis = null;
	    try{
			certKey = StringUtil.getValue(certKey);
		    KeyStore keyStore = KeyStore.getInstance("PKCS12");
		    fis = new FileInputStream(new File(certFile));
		    char[] keyChars = null;
			if(StringUtil.hasValue(certKey)) {
				keyChars = certKey.toCharArray();
			}
	    	keyStore.load(fis, keyChars);
	    	return keyStore;
	    }catch (Exception e) {
	    	e.printStackTrace();
		}finally{
	    	StreamUtil.close(fis);
	    }
	    return null;
	}
	
}
