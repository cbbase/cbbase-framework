package com.cbbase.core.tools;

import java.text.DecimalFormat;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;


/**
 * @author changbo
 * 
 */
public class StringUtil {
	
	public static final String ENCODING_SET = "UTF-8";
	
	//使用已编译的Pattern提高效率
	public static final Pattern PATTERN_DIGIT = Pattern.compile("[\\d]*");
	public static final Pattern PATTERN_DOUBLE = Pattern.compile("[\\d]+([\\.][\\d]+)?");
	public static final Pattern PATTERN_WORD = Pattern.compile("[\\w]*");
	public static final Pattern PATTERN_HEX = Pattern.compile("[a-fA-F0-9]*");
	public static final Pattern PATTERN_MOBILE = Pattern.compile("^[1][3-9]\\d{9}$");
	public static final Pattern PATTERN_EMAIL = Pattern.compile("^([\\w-])+@([\\w-])+(.[\\w-]+)+$");
	public static final Pattern PATTERN_CHINESE = Pattern.compile("^[\u4e00-\u9fa5]*$");
	public static final DecimalFormat df = new DecimalFormat("###0.##");
	
	public static boolean hasValue(String s){
		if(s != null && s.trim().length() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean hasValue(Object o){
		if(o != null && o.toString().trim().length() > 0){
			return true;
		}else{
			return false;
		}
	}

	public static boolean isEmpty(String... ss){
		for(String s : ss) {
			if(!hasValue(s)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isEmpty(String s){
		return !hasValue(s);
	}
	
	public static boolean isEmpty(Object o){
		return !hasValue(o);
	}
	
	public static int length(String s){
		if(isEmpty(s)){
			return 0;
		}
		return s.length();
	}
	
	public static int bytesLength(String s){
		if(isEmpty(s)){
			return 0;
		}
		return s.getBytes().length;
	}
	
	public static int charLength(String s){
		if(isEmpty(s)){
			return 0;
		}
		return s.getBytes().length;
	}
	
	public static String leftPad(String s, char c, int length){
		s = getValue(s);
		while(s.length() < length){
			s = c + s;
		}
		return s;
	}
	
	public static String rightPad(String s, char c, int length){
		s = getValue(s);
		while(s.length() < length){
			s = s + c;
		}
		return s;
	}
	
	public static String cutLength(String s, int length){
		if(hasValue(s) && s.length() > length){
			return s.substring(0, length);
		}
		return s;
	}
	
	public static String subStringByByte(String s, int begin, int end){
		
		byte[] b;
		try {
			b = s.getBytes(ENCODING_SET);
		} catch (Exception e) {
			b = s.getBytes();
		}
		byte[] b2 = new byte[end - begin];
		System.arraycopy(b, begin, b2, 0, b2.length);
		try {
			return new String(b2, ENCODING_SET);
		} catch (Exception e) {
			return new String(b2);
		}
	}
	
	public static String subStringByByte(String s, int begin){
		return subStringByByte(s, begin, bytesLength(s));
	}
	
	/**
	 * if null return false
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean isEqual(String s1, String s2){
		if(hasValue(s1) && s1.equals(s2)){
			return true;
		}
		return false;
	}
	
	/**
	 * if null return false
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean isEqualIgnoreCase(String s1, String s2){
		if(hasValue(s1) && s1.equalsIgnoreCase(s2)){
			return true;
		}
		return false;
	}

	public static String getValue(String s){
		if(s == null){
			return "";
		}
		return s.trim();
	}

	public static String decodeNull(String... strs){
		for(int i=0; strs!=null && i<strs.length; i++){
			if(strs[i] != null){
				return strs[i];
			}
		}
		return null;
	}

	public static String decodeEmpty(String... strs){
		for(int i=0; strs!=null && i<strs.length; i++){
			if(hasValue(strs[i])){
				return strs[i];
			}
		}
		return null;
	}
	
	public static String decode(String s, String val, String defaultValue){
		if(!isEqual(s, val)){
			return defaultValue;
		}
		return s;
	}
	
	public static String getValue(Object o){
		if(o == null || "null".equalsIgnoreCase(o.toString().trim())){
			return "";
		}
		return o.toString().trim();
	}

	public static String bytesToHexStr(byte[] b) {
		return bytesToHexStr(b, 0, b.length);
	}

	public static String bytesToHexStr(byte[] b, int start, int len) {
		StringBuilder str = new StringBuilder();
		for (int i = start; i < start + len; i++) {
			str.append(String.format("%02x", new Object[] { Byte.valueOf(b[i]) }));
		}
		return str.toString();
	}

	public static byte[] hexStrToBytes(String str) {
		if (str.length() % 2 != 0) {
			str = "0" + str;
		}
		byte[] temp = new byte[str.length() / 2];
		for (int i = 0; i < str.length(); i += 2) {
			temp[(i / 2)] = ((byte) (Byte.parseByte(str.substring(i, i + 1), 16) * 16
					+ Byte.parseByte(str.substring(i + 1, i + 2), 16)));
		}
		return temp;
	}

	public static boolean isNumber(String s){
		return matches(s, PATTERN_DIGIT);
	}
	public static boolean isNumber(String s, int length){
		if(isNumber(s) && s.length() <= length){
			return true;
		}
		return false;
	}
	
	public static boolean isWord(String s){
		return matches(s, PATTERN_WORD);
	}
	public static boolean isWord(String s, int length){
		if(isWord(s) && s.length() == length){
			return true;
		}
		return false;
	}
	public static boolean isChinese(String s){
		return matches(s, PATTERN_WORD);
	}
	public static boolean isHttpUrl(String s){
		if(isEmpty(s)){
			return false;
		}
		if(s.trim().startsWith("http://") || s.trim().startsWith("https://")){
			return true;
		}
		return false;
	}
	
	public static boolean matches(String str, String regex){
		if(isEmpty(str)){
			return false;
		}
		if(str.matches(regex)){
			return true;
		}
		return false;
	}
	
	public static boolean matches(String str, Pattern regex){
		if(isEmpty(str)){
			return false;
		}
		if(regex.matcher(str).matches()){
			return true;
		}
		return false;
	}
	
	public static int toInt(String s){
		if(isEmpty(s)){
			return 0;
		}
		s = s.trim();
		try{
			return Integer.parseInt(s);
		}catch (Exception e) {
		}
		return 0;
	}
	
	public static long toLong(String s){
		if(isEmpty(s)){
			return 0;
		}
		s = s.trim();
		try{
			return Long.parseLong(s);
		}catch (Exception e) {
		}
		return 0;
	}
	
	public static double toDouble(String s){
		if(isEmpty(s)){
			return 0;
		}
		s = s.trim();
		try{
			return Double.parseDouble(s);
		}catch (Exception e) {
		}
		return 0;
	}
	
	public static boolean toBoolean(String s){
		if(isEmpty(s)){
			return false;
		}
		s = s.trim();
		if("true".equalsIgnoreCase(s) || "1".equalsIgnoreCase(s) 
				|| "y".equalsIgnoreCase(s) || "yes".equalsIgnoreCase(s)){
			return true;
		}
		return false;
	}
	
	public static String formatCamel(String str){
		if(isEmpty(str)){
			return "";
		}
		String temp = str.trim().toLowerCase();
		String s = "";
		for(int i=0; i<temp.length(); i++){
			char c = temp.charAt(i);
			if(c == '_' && (i+1) <= temp.length()){
				char next = temp.charAt(i+1);
				String up = (""+next).toUpperCase();
				s = s + up;
				i++;
			}else{
				s = s + c;
			}
		}
		return s;
	}
	public static String camelToColumn(String str){
		if(isEmpty(str)){
			return "";
		}
		String temp = lowerFirst(str.trim());
		String s = "";
		for(int i=0; i<temp.length(); i++){
			char c = temp.charAt(i);
			if(c >= 'A' && c <= 'Z'){
				String low = (""+c).toLowerCase();
				s = s + "_" + low;
			}else{
				s = s + c;
			}
		}
		return s;
	}
	
	public static String upperFirst(String str){
		if(isEmpty(str)){
			return "";
		}
		String f = str.substring(0, 1);
		return f.toUpperCase()+str.substring(1);
	}
	
	public static String lowerFirst(String str){
		if(isEmpty(str)){
			return "";
		}
		String f = str.substring(0, 1);
		return f.toLowerCase()+str.substring(1);
	}
	
    public static boolean whereIn(String s, String... strs){
    	if(s == null || strs == null){
    		return false;
    	}
    	for(String t : strs){
    		if(isEqual(s, t)){
    			return true;
    		}
    	}
		return false;
	}

	public static boolean isMobile(String mobile){
		return matches(mobile, PATTERN_MOBILE);
	}
	
	public static boolean isEmail(String email){
		return matches(email, PATTERN_EMAIL);
	}
	
	public static String toString(double d){
		return df.format(d);
	}

	public static String toString(float f){
		return df.format(f);
	}
	
	public static String toString(long l){
		return new Long(l).toString();
	}
	
	public static String toString(List<?> list){
		return toString(list, ";");
	}
	
	public static String toString(List<?> list, String delimiter){
		StringBuilder sb = new StringBuilder();
		for(int i=0; list!=null && i<list.size(); i++){
			sb.append(toString(list.get(i)));
			if(i < list.size()-1){
				sb.append(delimiter);
			}
		}
		return sb.toString();
	}
	
	public static String toString(Object[] strArray){
		return toString(strArray, 0, ";");
	}
	
	public static String toString(Object[] strArray, int offset, String delimiter){
		StringBuilder sb = new StringBuilder();
		for(int i=offset; strArray!=null && i<strArray.length; i++){
			sb.append(toString(strArray[i]));
			if(i < strArray.length-1){
				sb.append(delimiter);
			}
		}
		return sb.toString();
	}
	
	public static String toString(Object o){
		return getValue(o);
	}

	
	public static int matchCount(String content, String key){
		if(StringUtil.isEmpty(content) || StringUtil.isEmpty(key)){
			return 0;
		}
		int l1 = content.length();
		int l2 = content.replaceAll(key, "").length();
		int count = (l1-l2)/key.length();
		return count;
	}
	
	/**
	 * base on java8
	 */
	public static String encodeBase64(byte[] bytes){
		return Base64.getEncoder().encodeToString(bytes);
	}

	/**
	 * base on java8
	 */
	public static byte[] decodeBase64(String str){
		return Base64.getDecoder().decode(str);
	}
	
	public static String unicodeDecode(String unicode){  
		if(isEmpty(unicode)){
			return unicode;
		}
		StringBuilder sb = new StringBuilder();  
		int i = -1;  
		int pos = 0;  
		while((i=unicode.indexOf("\\u", pos)) != -1){  
			sb.append(unicode.substring(pos, i));  
			if(i+5 < unicode.length()){  
				pos = i+6;  
				sb.append((char)Integer.parseInt(unicode.substring(i+2, i+6), 16));  
			}  
		}  
		return sb.toString();  
	}
	
	public static String unicodeEncode(String string) {
		if(isEmpty(string)){
			return string;
		}
		StringBuffer unicode = new StringBuffer();
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			unicode.append("\\u" + Integer.toHexString(c));
		}
		return unicode.toString();
	}
}
