package com.cbbase.core.tools;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.zip.CRC32;

public class HashUtil {

	public static String MD5 = "MD5";
	public static String SHA1 = "SHA-1";
	public static String SHA256 = "SHA-256";
	public static String SHA512 = "SHA-512";
	public static String ENCODING = "UTF-8";

	public static String getMD5(String text){
		return getHash(text, MD5);
	}
	public static String getMD5(File file){
		return getHash(file, MD5);
	}
	public static String getSHA1(String text){
		return getHash(text, SHA1);
	}
	public static String getSHA1(File file){
		return getHash(file, SHA1);
	}
	public static String getSHA256(String text){
		return getHash(text, SHA256);
	}
	public static String getSHA256(File file){
		return getHash(file, SHA256);
	}
	public static String getSHA512(String text){
		return getHash(text, SHA512);
	}
	public static String getSHA512(File file){
		return getHash(file, SHA512);
	}
	
	private static String getHash(String text, String sha){
		String md5 = "";
		//MD5 SHA
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(sha);
			md.update(text.getBytes(ENCODING));
	        byte[] b = md.digest();
	        return StringUtil.bytesToHexStr(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5;
	}
	
	private static String getHash(File file, String sha){
		String md5 = "";
		FileInputStream fis = null;
		try{
			MessageDigest md = MessageDigest.getInstance(sha);
			fis = new FileInputStream(file);
			FileChannel fc = fis.getChannel();
			ByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			md.update(bb);
			byte[] b = md.digest();
	        return StringUtil.bytesToHexStr(b);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			StreamUtil.close(fis);
		}
		return md5;
	}

	public static long getCRC32(String str){
		if(str == null){
			return 0;
		}
		CRC32 crc32 = new CRC32();
		crc32.update(str.getBytes());
		return crc32.getValue();
	}
	
	public static long getCRC32(File file) {
		if(file == null){
			return 0;
		}
	    CRC32 crc32 = new CRC32();  
	    FileInputStream fis = null;
	    try {
	    	fis = new FileInputStream(file);
	        byte[] buffer = new byte[8192];
	        int length;
	        while ((length = fis.read(buffer)) != -1) {
	            crc32.update(buffer,0, length);
	        }
	        return crc32.getValue();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	StreamUtil.close(fis);
	    }
	    return 0;
	}
	
}
