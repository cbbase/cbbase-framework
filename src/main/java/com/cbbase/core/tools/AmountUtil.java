package com.cbbase.core.tools;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * 
 * 金额计算,请尽量用分为单位计算,避免精度问题
 * @author changbo
 *
 */
public class AmountUtil {
	
	public static final String[] CN_NUM = new String[]{"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
	public static final String[] CN_SYS = new String[]{"仟", "佰", "拾", "亿", "仟", "佰", "拾", "万", "仟", "佰", "拾", ""};
	public static final String[] RMB_UNIT = new String[]{"元", "角", "分"};
	public static final DecimalFormat MONEY_FORMAT = new DecimalFormat("###0.00");
	
	/**
	 * 	数组转汉字
	 * @param num
	 * @return
	 */
	public static String numToChinese(long num){
		String cn = "";
		String temp = StringUtil.toString(num);
		int last = 1;
		for(int i=0; i < temp.length(); i++){
			int n = StringUtil.toInt(""+temp.charAt(i));
			String ns = CN_NUM[n];
			String ss = CN_SYS[CN_SYS.length-temp.length()+i];
			if(n == 0){
				if(last != 0){
					cn = cn + ns;
				}
			}else{
				cn = cn + ns + ss;
			}
			last = n;
		}
		if(cn.length() > 1 && cn.endsWith("零")) {
			cn = cn.substring(0, cn.length()-2);
		}
		return cn;
	}
	
	/**
	 * 	浮点型数组转汉字,保留小数点后2为
	 * @param num
	 * @return
	 */
	public static String amountToChinese(double num){
		String tt = toShowView(num);
		String cn = "";
		//元
		String temp = tt.substring(0, tt.length()-3);
		long tn = StringUtil.stringToLong(temp);
		if(tn > 0){
			cn = cn + numToChinese(StringUtil.stringToLong(temp)) + RMB_UNIT[0];
		}
		if(cn.length() > 2 && tt.endsWith(".00")) {
			cn = cn + "整";
			return cn;
		}
		//角
		temp = tt.substring(tt.length()-2, tt.length()-1);
		tn = StringUtil.stringToLong(temp);
		if(tn > 0){
			cn = cn + numToChinese(StringUtil.stringToLong(temp)) + RMB_UNIT[1];
		}
		//分
		temp = tt.substring(tt.length()-1, tt.length());
		tn = StringUtil.stringToLong(temp);
		if(tn > 0){
			cn = cn + numToChinese(StringUtil.stringToLong(temp)) + RMB_UNIT[2];
		}
		
		return cn;
	}
	
	/**
	 * 	字符串转浮点数字
	 * @param amt
	 * @return
	 */
	public double parseAmount(String amt){
		BigDecimal b = new BigDecimal(amt);
		b = b.setScale(3, BigDecimal.ROUND_HALF_UP);
		return b.doubleValue();
	}
	
	public double parseAmount(double amt){
		BigDecimal b = new BigDecimal(amt);
		b = b.setScale(3, BigDecimal.ROUND_HALF_UP);
		return b.doubleValue();
	}

	public static long yuanToFen(double d){
		BigDecimal bd = new BigDecimal(d);
		BigDecimal bd2 = new BigDecimal(100);
		bd = bd.multiply(bd2);
		return bd.longValue();
	}
	
	public static long yuanToFen(String d){
		BigDecimal bd = new BigDecimal(d);
		BigDecimal bd2 = new BigDecimal(100);
		bd = bd.multiply(bd2);
		return bd.longValue();
	}
	
	public static double fenToYuan(long d){
		BigDecimal bd = new BigDecimal(d);
		BigDecimal bd2 = new BigDecimal(100);
		bd = bd.divide(bd2, 2, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}
	
	public static String fenToYuanString(long d){
		return toShowView(fenToYuan(d));
	}
	
	public static String toShowView(Object o){
		return MONEY_FORMAT.format(o);
	}
	
	public static double formatYuan(String str) throws ParseException{
		return MONEY_FORMAT.parse(str).doubleValue();
	}
	
	public static String add(String a, String b){
		return ""+(StringUtil.stringToLong(a)+StringUtil.stringToLong(b));
	}
	
	public static String sub(String a, String b){
		return ""+(StringUtil.stringToLong(a)-StringUtil.stringToLong(b));
	}
	
	public static String mul(String a, String b){
		return ""+(StringUtil.stringToLong(a)*StringUtil.stringToLong(b));
	}
	
	public static String div(String a, String b){
		return ""+(StringUtil.stringToLong(a)/StringUtil.stringToLong(b));
	}
	

	public static long calcRate(long amount, long discountRate){
		return calcRate(amount, discountRate, 100L);
	}
	
	/**
	 * 	四舍五入的方式计算优惠折扣
	 */
	public static long calcRate(long amount, long discountRate, long base){
		BigDecimal bd1 = new BigDecimal(amount);
		BigDecimal bd2 = new BigDecimal(discountRate);
		bd1 = bd1.multiply(bd2).divide(new BigDecimal(base), 0, BigDecimal.ROUND_HALF_UP);
    	return bd1.longValue();
	}
	

	public static long calcExchange(long amount, long exchangeRate){
		return calcExchange(amount, exchangeRate, 100L);
	}
	
	/**
	 * 	四舍五入的方式计算兑换需要的值
	 */
	public static long calcExchange(long amount, long exchangeRate, long base){
		BigDecimal bd1 = new BigDecimal(amount);
		BigDecimal bd2 = new BigDecimal(exchangeRate);
		bd1 = bd1.divide(bd2, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(base));
    	return bd1.longValue();
	}
	
	/**
	 * 	四舍五入的方式计算手续费,手续费单位为:万分之
	 * @param amount
	 * @param feeRate
	 * @param maxFee
	 * @return
	 */
	public static long calcRoundFee(long amount, long feeRate, long maxFee){
		BigDecimal bd1 = new BigDecimal(amount);
		BigDecimal bd2 = new BigDecimal(feeRate);
		bd1 = bd1.multiply(bd2).divide(new BigDecimal(10000), 0, BigDecimal.ROUND_HALF_UP);
		long fee = bd1.longValue();
		if(maxFee > 0 && fee > maxFee){
			fee = maxFee;
		}
    	return fee;
	}
	
	/**
	 * 	用公平的银行家算法的方式计算手续费(四舍六进,五则看情况:后面一位为奇数则进,为偶则舍)<br>
	 * 	手续费单位为:万分之
	 * @param amount
	 * @param feeRate
	 * @param maxFee
	 * @return
	 */
	public static long calcFairFee(long amount, long feeRate, long maxFee){
		BigDecimal bd1 = new BigDecimal(amount);
		BigDecimal bd2 = new BigDecimal(feeRate);
		bd1 = bd1.multiply(bd2).divide(new BigDecimal(10000));
		double fee = bd1.doubleValue();
		
		if(maxFee > 0 && fee > maxFee){
			fee = maxFee;
		}
		long feeLong = (long)fee;
		long plus = 0;
		
		long temp = (long)(fee*100);
		temp = temp%100;
		if(temp < 50){
			plus = 0;
		}else if(temp >= 60){
			plus = 1;
		}else if(temp >= 50 && temp <= 59){
			temp = temp%2;
			if(temp == 1){
				plus = 1;
			}else{
				plus = 0;
			}
		}
    	return feeLong + plus;
	}
	
}
