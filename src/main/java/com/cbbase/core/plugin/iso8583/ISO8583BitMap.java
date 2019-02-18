package com.cbbase.core.plugin.iso8583;

import java.util.BitSet;

import com.cbbase.core.tools.StringUtil;


public class ISO8583BitMap {

	private BitSet bitSet;

	public ISO8583BitMap(int size) {
		bitSet = new BitSet(size);
	}

	/**
	 * 将指定索引处的位设置为 true
	 * 
	 * @param index
	 *            索引
	 */
	public void set(int index) {
		bitSet.set(index);
	}

	/**
	 * 将指定索引处的位设置为指定值
	 * 
	 * @param index
	 *            索引
	 * @param value
	 */
	public void set(int index, boolean value) {
		bitSet.set(index, value);
	}

	public BitSet getBitSet() {
		return bitSet;
	}

	/**
	 * 返回指定索引处的位值
	 * 
	 * @param index
	 * @return
	 */
	public boolean get(int index) {
		return bitSet.get(index);
	}

	/**
	 * 返回一个新的 BitSet,它由此 BitSet 中从 fromIndex(包括)到 toIndex(不包括)范围内的位组成
	 * 
	 * @param fromIndex
	 * @param toIndex
	 * @return
	 */
	public BitSet get(int fromIndex, int toIndex) {
		return bitSet.get(fromIndex, toIndex);
	}

	/**
	 * 获取位图
	 * 
	 * @return
	 */
	public byte[] getBitMapByte() {
		return bitSet2Byte(bitSet);
	}

	/**
	 * 获取位图信息并转换为十六进制
	 * 
	 * @return
	 */
	public String getBitMapHex() {
		return StringUtil.bytesToHexStr(bitSet2Byte(bitSet));
	}

	/**
	 * 转换为Byte
	 * 
	 * @param bitSet
	 * @return
	 */
	private byte[] bitSet2Byte(BitSet bitSet) {
		byte[] bytes = new byte[bitSet.size() / 8];
		for (int i = 0; i < bitSet.size(); i++) {
			int index = i / 8;
			int offset = 7 - i % 8;
			bytes[index] |= (bitSet.get(i) ? 1 : 0) << offset;
		}

		return bytes;
	}
}
