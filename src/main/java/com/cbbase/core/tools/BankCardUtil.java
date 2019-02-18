package com.cbbase.core.tools;

/**
 * 	银行卡工具类
 * @author changbo
 *
 */
public class BankCardUtil {
	
	public static boolean checkLuhm(String bankCard){
		if(bankCard == null || !StringUtil.isNumber(bankCard)){
			return false;
		}
		if(bankCard.length() > 19 || bankCard.length() < 12){
			return false;
		}
		char c = bankCard.charAt(bankCard.length()-1);
		int ck = c - 48;
		if(ck == getLuhm(bankCard.substring(0, bankCard.length()-1))){
			return true;
		}
		return false;
	}
	
	public static int getLuhm(String bankCard){
		if(bankCard == null || !StringUtil.isNumber(bankCard)){
			return 0;
		}
		int d = 0;
		for(int i=0; i<=bankCard.length()-1; i++){
			char n = bankCard.charAt(bankCard.length()-1-i);
			int nn = n - 48;
			if(i%2 == 0){
				nn = nn * 2;
				nn = nn/10 + nn%10;
			}
			d = d + nn;
		}
		d = d%10;
		return (10-d)%10;
	}
	
	public static String toStarString(String bankCard){
		if(bankCard == null){
			return "";
		}
		if(bankCard.length() < 10){
			return bankCard;
		}
		int l = bankCard.length();
		return bankCard.substring(0, 6) + "****" + bankCard.substring(l-4, l);
	}
	
}
