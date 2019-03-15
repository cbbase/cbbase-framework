package com.cbbase.core.tools;

import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 * @author changbo
 *
 */
public class JwtHelper {
	
    public static String create(String key, Object obj, int seconds) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] encodedKey = Base64.decodeBase64(key);
        SecretKey secretKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        JwtBuilder builder = Jwts.builder();
        builder.claim("data", obj);
        builder.setExpiration(DateUtil.nextSeconds(new Date(), seconds));
        builder.setNotBefore(new Date());
        builder.signWith(signatureAlgorithm, secretKey);
        return builder.compact();
    }
    
    public static <T> T parse(String key, String jwt, Class<T> clazz) {
        byte[] encodedKey = Base64.decodeBase64(key);
        Claims claims = Jwts.parser().setSigningKey(encodedKey).parseClaimsJws(jwt).getBody();
        return claims.get("data", clazz);
    }
    
    public static void main(String[] args) {
		String token = JwtHelper.create("key", "123123", 6000);
		String data = JwtHelper.parse("key", token, String.class);
		System.out.println(token);
		System.out.println(data);
	}

}
