package com.cbbase.core.tools;

import java.util.BitSet;

public class ByteUtil {
	

	public static int intToBytes(int dec, byte[] bcd, int length) {
		for (int i = length - 1; i >= 0; i--) {
			int temp = dec % 100;
			bcd[i] = (byte) (((temp / 10) << 4) + ((temp % 10) & 0x0F));
			dec /= 100;
		}
		return 0;
	}
	
	public static int byteToInt(byte bcd[], int offset, int length) {
		int result = 0;
		for (int i = 0 + offset; i < length + offset; i++) {
			int tmp = ((bcd[i] >> 4) & 0x0F) * 10 + (bcd[i] & 0x0F);
			result += tmp * power(100, length + offset - 1 - i);
		}
		return result;
	}
	

	private static long power(int x, int n) {
		int tmp = 1;
		for (int i = 0; i < n; i++) {
			tmp = tmp * x;
		}
		return tmp;
	}
	

	public static BitSet byteToBitSet(byte[] bytes) {
		BitSet bitSet = new BitSet(bytes.length * 8);
		int index = 0;
		for (int i = 0; i < bytes.length; i++) {
			for (int j = 7; j >= 0; j--) {
				bitSet.set(index++, (bytes[i] & (1 << j)) >> j == 1 ? true : false);
			}
		}
		return bitSet;
	}

}
