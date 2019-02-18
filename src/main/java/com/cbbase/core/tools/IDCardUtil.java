package com.cbbase.core.tools;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * 身份证验证
 * @author changbo
 *
 */
public class IDCardUtil {
	public static final String[] CHECK = new String[]{"1","0","X","9","8","7","6","5","4","3","2"};
	public static final Pattern CHINESE_NAME = Pattern.compile("^[\u4e00-\u9fa5]{2,20}([·][\u4e00-\u9fa5]{2,20})*$");
	public static final Pattern ID_FORMAT = Pattern.compile("^[0-9]{17}[0-9Xx]$");
	
	public static boolean checkID(String id){
    	if(id == null || !ID_FORMAT.matcher(id).matches()){
    		return false;
    	}
    	if("00".equals(id.substring(0, 2))){
    		return false;
    	}
    	if("00".equals(id.substring(2, 4))){
    		return false;
    	}
    	if("00".equals(id.substring(4, 6))){
    		return false;
    	}
    	if(getAddrCode(id) == null){
    		return false;
    	}
    	try{
        	String birthday = id.substring(6, 14);
    		Date date = DateUtil.stringToDateNum(birthday);
        	Date date2 = DateUtil.stringToDateNum("19000101");
        	if(date.getTime() < date2.getTime()){
        		return false;
        	}
    	}catch(Exception e){
    		return false;
    	}
    	
    	String check = genCheckValue(id);
    	if(!id.toUpperCase().endsWith(check)){
    		return false;
    	}
    	return true;
    }
    
    public static boolean checkName(String name){
    	//name
    	if(name == null || name.length() < 2 || name.length() > 10){
    		return false;
    	}
    	//必须是汉字（少数民族可以有点）
		if(!CHINESE_NAME.matcher(name).matches()){
			return false;
		}
		return true;
    }
    
    /**
     * 1男0女
     * @param id
     * @return
     */
    public static String getGender(String id){
    	if(StringUtil.isEmpty(id) || id.length() <= 17){
    		return null;
    	}
    	char c = id.charAt(16);
    	int g = (c - '0')%2;
    	return ""+g;
    }
    
    public static String getBirthday(String id){
    	if(StringUtil.isEmpty(id) || id.length() <= 14){
    		return null;
    	}
    	String birthday = null;
    	try{
        	birthday = id.substring(6, 14);
    		Date date = DateUtil.stringToDateNum(birthday);
    		birthday = DateUtil.dateToString(date);
    	}catch(Exception e){
    		return null;
    	}
    	return birthday;
    }
    public static String getAge(String id){
    	String birthday = getBirthday(id);
    	if(birthday == null){
    		return null;
    	}
    	birthday = birthday.substring(0, 4).replaceAll("-", "");
    	int y = StringUtil.toInt(birthday);
    	int c = StringUtil.toInt(DateUtil.getCurrentDateStringNum().substring(0, 4));
    	return ""+(c-y);
    }
    
    public static String getAddrCode(String id){
    	String code = null;
    	if(StringUtil.hasValue(id) && id.length() > 6){
    		code = id.substring(0, 6);
    	}
    	return code;
    }
    
    public static String genCheckValue(String data){
    	if(StringUtil.isEmpty(data) || data.length() < 17){
    		return null;
    	}
    	String check = "";
    	if(data.length() > 17){
    		data = data.substring(0, 17);
    	}
    	int[] p = new int[]{7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1};
    	int t = 0;
    	for(int k=0; k<17; k++){
    		int n = data.charAt(k)-48;
    		t += n*p[k];
    	}
    	t = t%11;
    	check = CHECK[t];
    	return check;
    }
	
	public static String createId(String year){
		String dd = DateUtil.getCurrentDateStringNum().substring(4, 8);
		String ss = "110112"+year+dd;
		int i = RandomUtil.random(100, 900);
		String id = ss + i;
    	id = id + genCheckValue(id);
    	return id;
	}
	
}
