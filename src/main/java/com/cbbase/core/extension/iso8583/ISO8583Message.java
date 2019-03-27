package com.cbbase.core.extension.iso8583;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author changbo
 *
 */
public class ISO8583Message {
	
	/** 8583位图 */
	private ISO8583BitMap bitMap = new ISO8583BitMap(ISO8583Constants.FIELD_MAX_SIZE);
	
	/** 报文数据 */
	private String messageTpdu;
	private String messageHeader;
	private String messageType;
	
	private Map<Integer, Field> fieldMap= new HashMap<Integer, Field>();
	
	private int messageLength;
	private boolean macVerified = true;
	
	/**
	 * 添加域
	 * 
	 * @param index
	 * @param type
	 */
	public void addField(int index, String content) {
		if(content == null){
			return;
		}
		// 设置位图
		bitMap.set(index - 1);
		// 设置域
		Field field = new Field();
		field.content = content;
		field.index = index;
		fieldMap.put(index, field);
	}
	
	public String removeField(int index){
		bitMap.set(index - 1, false);
		Field field = fieldMap.remove(index);
		if(field != null){
			return field.content;
		}
		return null;
	}
	
	public String getField(int index){
		Field field = fieldMap.get(index);
		if(field != null){
			return field.content;
		}
		return null;
	}
	
	public String getMessageTpdu() {
		return messageTpdu;
	}

	public void setMessageTpdu(String messageTpdu) {
		this.messageTpdu = messageTpdu;
	}

	public String getMessageHeader() {
		return messageHeader;
	}

	public void setMessageHeader(String messageHeader) {
		this.messageHeader = messageHeader;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public ISO8583BitMap getBitMap() {
		return bitMap;
	}

	public void setBitMap(ISO8583BitMap bitMap) {
		this.bitMap = bitMap;
	}

	public boolean getMacVerified() {
		return macVerified;
	}

	public void setMacVerified(boolean macVerified) {
		this.macVerified = macVerified;
	}
	
	public int getMessageLength() {
		return messageLength;
	}

	public void setMessageLength(int messageLength) {
		this.messageLength = messageLength;
	}

	public Map<Integer, Field> getFieldMap() {
		return fieldMap;
	}
	
	
}
