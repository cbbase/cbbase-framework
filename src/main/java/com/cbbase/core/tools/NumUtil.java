package com.cbbase.core.tools;

public class NumUtil {
	
	public static boolean isRange(long x, long start, long end) {
		if(x >= start && x <= end) {
			return true;
		}
		return false;
	}

	public static boolean isWhere(long x, long... ws) {
		if(ws == null) {
			return false;
		}
		for(long w : ws) {
			if(x == w) {
				return true;
			}
		}
		return false;
	}
	
}
