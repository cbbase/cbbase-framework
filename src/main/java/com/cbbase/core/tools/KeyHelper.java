package com.cbbase.core.tools;

/**
 * java自带uuid是无序的
 * </br>twitter的snowflake在机器时间往回拨时,会产生相同id
 * </br>自定义主键生成:使用毫秒数+n位16进制随机数(总共32位16进制字符串),能保证毫秒级升序且不重复
 * </br>优点:效率高(每秒生成200万+),升序,不重复
 * </br>缺点:太长(32位)
 * @author changbo
 *
 */
public class KeyHelper {
	
	public static String nextKey(){
		String msec = Long.toHexString(System.currentTimeMillis()).toUpperCase();
		return "A" + msec + RandomUtil.hex(31-msec.length());
	}
	
}