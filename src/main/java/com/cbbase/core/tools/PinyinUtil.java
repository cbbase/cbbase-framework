package com.cbbase.core.tools;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {

	public static String chineseToPinyin(String str) {
		return chineseToPinyin(str, true, false, null);
	}
	
	public static String chineseToPinyin(String str, boolean full, boolean upperFirst, String separator) {
		
		StringBuffer pinyin = new StringBuffer();
		for(int i=0; i<str.length(); i++) {
			HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
			format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			String s = "";
			try {
				s = PinyinHelper.toHanYuPinyinString(str.substring(i, i+1), format, "", true);
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
			if(!full) {
				s = s.substring(0, 1);
			}
			if(upperFirst) {
				s = s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
			}
			pinyin.append(s);
			if(separator != null) {
				pinyin.append(separator);
			}
		}
		return pinyin.toString();
	}

}
