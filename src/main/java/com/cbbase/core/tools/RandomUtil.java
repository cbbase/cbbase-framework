package com.cbbase.core.tools;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 不要使用UUID.randomUUID()创建uuid,这个方法占用资源特别高,效率很低
 * @author changbo
 * 
 */
public class RandomUtil{
	
	public static final char[] NUM_STR = "0123456789".toCharArray();
	public static final char[] HEX_STR = "0123456789ABCDEF".toCharArray();
	public static final char[] WORD_STR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

	public static ThreadLocalRandom getRandom() {
		return ThreadLocalRandom.current();
	}
	
	private static String random(char[] str, int length){
		StringBuilder sb = new StringBuilder();
		ThreadLocalRandom random = getRandom();
		for(int i=0; i<length; i++){
			int index = random.nextInt(str.length);
			sb.append(str[index]);
		}
		return sb.toString();
	}
	
	public static String hex(int length){
		return random(HEX_STR, length);
	}

	public static String number(int length){
		return random(NUM_STR, length);
	}
	
	public static String word(int length){
		return random(WORD_STR, length);
	}
	
	public static int random(int start, int end){
		return getRandom().nextInt(end - start) + start;
	}
	
	public static String uuid() {
		long time = System.currentTimeMillis();
		return Long.toHexString(time).toUpperCase()+hex(21);
	}
}
