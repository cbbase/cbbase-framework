package com.cbbase.core.plugin.iso8583;


import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

import com.cbbase.core.encrypt.MAC;
import com.cbbase.core.tools.ByteUtil;
import com.cbbase.core.tools.StringUtil;

/**
 * 
 * @author
 *
 */
public class ISO8583Parser {
	
	private boolean showFieldMessage = false;
	private Map<Integer, FieldDefine> fieldDefineMap = DefaultISO8583FieldDefine.DEFAULT_DEFINE_MAP;
	private int fieldMaxSize = ISO8583Constants.FIELD_MAX_SIZE;
	private ISO8583Message message;
	private String hexMacKey;
	private int length;
	private int srcPosition = 0;
	
	public ISO8583Parser(){
	}
	
	public ISO8583Parser(ISO8583Message message){
		this.message = message;
	}
	
	public byte[] packMessage() {
		
		initFieldDefine();
		
		length = 0;
		byte[] buffer = new byte[ISO8583Constants.MESSAGE_MAX_LENGTH];
		int bufferLength = packMessage(buffer);
		
		if(hexMacKey != null){
			int len = ISO8583Constants.MESSAGE_TPDU_LENGTH + ISO8583Constants.MESSAGE_HEADER_LENGTH;
			byte[] macData = new byte[bufferLength-len];
			System.arraycopy(buffer, len, macData, 0, bufferLength-len);
			byte[] macByte = MAC.clacMac(StringUtil.hexStrToBytes(hexMacKey), macData);
			String mac = StringUtil.bytesToHexStr(macByte);
			Field field = new Field();
			field.content = mac;
			field.define = fieldDefineMap.get(fieldMaxSize);
			field.index = fieldMaxSize;
			if(field != null){
				packField(buffer, field);
			}
			bufferLength = length;
		}
		
		byte[] messageByte = new byte[bufferLength+2];
		String lengthString = countMessageLength();
		System.arraycopy(StringUtil.hexStrToBytes(lengthString), 0, messageByte, 0, 2);
		System.arraycopy(buffer, 0, messageByte, 2, bufferLength);
		return messageByte;
	}
	
	/**
	 * 将message的每个域的定义找出来
	 */
	private void initFieldDefine(){
		
		if(hexMacKey != null){
			message.getBitMap().set(fieldMaxSize-1);
		}
		
		for(int index = 1; index <= fieldMaxSize; index++){
			Field field = message.getFieldMap().get(index);
			if(field != null){
				FieldDefine define = fieldDefineMap.get(index);
				if(define == null){
					//如果域未定义,则删除此域
					message.getFieldMap().remove(index);
				}else{
					field.define = define;
				}
				
				//针对定长数据域,进行填充
				int dataLength = field.define.length;
				if (dataLength != ISO8583Constants.LLLVAR && dataLength != ISO8583Constants.LLVAR) {
					if(field.define.fixType == FieldDefine.FixType.LEFT){
						field.content = StringUtil.leftPad(field.content, field.define.fixChar, dataLength);
					}else if(field.define.fixType == FieldDefine.FixType.RIGHT){
						field.content = StringUtil.rightPad(field.content, field.define.fixChar, dataLength);
					}
					if(field.define.type == FieldDefine.Type.BCD && field.content.length() != dataLength){
						String exception = String.format("数据域[%d]长度错误:%s(%s)", field.index, field.define.decribe, field.content);
						throw new RuntimeException(exception);
					}
					if(field.define.type == FieldDefine.Type.BINRARY && field.content.length()/2 != dataLength){
						String exception = String.format("数据域[%d]长度错误:%s(%s)", field.index, field.define.decribe, field.content);
						throw new RuntimeException(exception);
					}
				}
			}
		}
	}
	
	/**
	 * 打包8583报文
	 * 
	 * @param buffer
	 * @return 0 打包失败,打包成功返回报文长度
	 */
	private int packMessage(byte[] buffer) {
		if (null == buffer) {
			return 0;
		}
		
		try {
			packTpdu(buffer);
			packMessageHeader(buffer);
			packMessageType(buffer);
			packBitMap(buffer);
			
			for(int index=1; index < fieldMaxSize; index++){
				Field field = message.getFieldMap().get(index);
				if(field != null){
					packField(buffer, field);
				}
			}
			return length;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 设置域数据
	 * 
	 * @param buffer
	 * @param field
	 */
	private void packField(byte[] buffer, Field field) {
		showMessage(String.format("[%d]%s(%s)", field.index, field.define.decribe, field.content));
		int fieldLength = field.define.length;
		//数据转换
		if (ISO8583Constants.LLLVAR == fieldLength) {
			packLLLVarField(buffer, field);
		} else if (ISO8583Constants.LLVAR == fieldLength) {
			packLLVarField(buffer, field);
		} else {
			packFixedField(buffer, field);
		}
	}

	/**
	 * 设置三位变长数据域
	 */
	private void packLLLVarField(byte[] buffer, Field field) {
		packVarField(buffer, field, 2);
	}

	/**
	 * 设置两位变长数据域
	 */
	private void packLLVarField(byte[] buffer, Field field) {
		packVarField(buffer, field, 1);
	}

	/**
	 * 设置定长域
	 */
	private void packFixedField(byte[] buffer, Field field) {
		if (FieldDefine.Type.ASCII == field.define.type) {
			System.arraycopy(field.content.getBytes(), 0, buffer, length, field.define.length);
			length += field.define.length;
		} else if (FieldDefine.Type.BCD == field.define.type) {
			System.arraycopy(StringUtil.hexStrToBytes(field.content), 0, buffer, length, field.define.length / 2);
			length += field.define.length / 2;
		} else if (FieldDefine.Type.BINRARY == field.define.type) {
			System.arraycopy(StringUtil.hexStrToBytes(field.content), 0, buffer, length, field.define.length);
			length += field.define.length;
		}
	}

	/**
	 * 打包变长域数据
	 * 
	 * @param buffer
	 * @param field
	 * @param var
	 */
	private void packVarField(byte[] buffer, Field field, int var) {
		byte[] temp = new byte[var];
		int contentLength;

		if (FieldDefine.Type.ASCII == field.define.type) {
			contentLength = field.content.length();
			ByteUtil.intToBytes(contentLength, temp, var);
			System.arraycopy(temp, 0, buffer, length, var);
			length += var;
			
			System.arraycopy(field.content.getBytes(), 0, buffer, length, contentLength);
			length += contentLength;
		} else if (FieldDefine.Type.BCD == field.define.type) {
			contentLength = field.content.length();
			ByteUtil.intToBytes(contentLength, temp, var);
			System.arraycopy(temp, 0, buffer, length, var);
			length += var;

			if (contentLength % 2 == 1) {
				field.content = field.content + 0;
				contentLength = field.content.length();
			}

			System.arraycopy(StringUtil.hexStrToBytes(field.content), 0, buffer, length, contentLength / 2);
			length += contentLength / 2;
		} else if (FieldDefine.Type.BINRARY == field.define.type) {
			contentLength = field.content.length() / 2;
			ByteUtil.intToBytes(contentLength, temp, var);
			System.arraycopy(temp, 0, buffer, length, var);
			length += var;

			System.arraycopy(StringUtil.hexStrToBytes(field.content), 0, buffer, length, contentLength);
			length += contentLength;
		}
	}

	/**
	 * 设置位图
	 * 
	 * @param buffer
	 */
	private void packBitMap(byte[] buffer) {
		System.arraycopy(message.getBitMap().getBitMapByte(), 0, buffer, length, ISO8583Constants.BITMAP_LENGTH);
		length += ISO8583Constants.BITMAP_LENGTH;
	}

	/**
	 * 设置消息类型
	 * 
	 * @param buffer
	 */
	private void packMessageType(byte[] buffer) {
		System.arraycopy(StringUtil.hexStrToBytes(message.getMessageType()), 0, buffer, length, ISO8583Constants.MESSAGE_TYPE_LENGTH);
		length += ISO8583Constants.MESSAGE_TYPE_LENGTH;
	}

	/**
	 * 设置报文头
	 * 
	 * @param buffer
	 */
	private void packMessageHeader(byte[] buffer) {
		System.arraycopy(StringUtil.hexStrToBytes(message.getMessageHeader()), 0, buffer, length, ISO8583Constants.MESSAGE_HEADER_LENGTH);
		length += ISO8583Constants.MESSAGE_HEADER_LENGTH;
	}

	/**
	 * 设置TPDU
	 * 
	 * @param buffer
	 */
	private void packTpdu(byte[] buffer) {
		System.arraycopy(StringUtil.hexStrToBytes(message.getMessageTpdu()), 0, buffer, length, ISO8583Constants.MESSAGE_TPDU_LENGTH);
		length += ISO8583Constants.MESSAGE_TPDU_LENGTH;
	}
	
	/**
	 * 解析8583报文
	 * 
	 * @param buffer
	 * @return 0 解析失败,非零解析成功
	 */
	public ISO8583Message unpackMessage(byte[] buffer) {
		if (null == buffer || buffer.length < 2){
			return null;
		}
		srcPosition = 0;
		int length = Integer.parseInt(analysisTragetLengthData(buffer, 2), 16);
		String tpdu = analysisTragetLengthData(buffer, ISO8583Constants.MESSAGE_TPDU_LENGTH);
		String messageHeader = analysisTragetLengthData(buffer, ISO8583Constants.MESSAGE_HEADER_LENGTH);
		String messageType = analysisTragetLengthData(buffer, ISO8583Constants.MESSAGE_TYPE_LENGTH);
		
		ISO8583Message tempMessage = new ISO8583Message();
		tempMessage.setMessageLength(length);
		tempMessage.setMessageTpdu(tpdu);
		tempMessage.setMessageHeader(messageHeader);
		tempMessage.setMessageType(messageType);
		// 域信息
		List<Integer> fieldIndexList = analysisBitMap(buffer);
		for (Integer index : fieldIndexList) {
			FieldDefine define = fieldDefineMap.get(index);
			Field tempField = analysisFieldData(index, buffer, define);
			tempMessage.addField(index, tempField.content);
		}
		if(StringUtil.hasValue(hexMacKey)){
			int head = ISO8583Constants.MESSAGE_TPDU_LENGTH + ISO8583Constants.MESSAGE_HEADER_LENGTH + 2;
			int mac_len = 6;
			byte[] macData = new byte[length-head-mac_len];
			System.arraycopy(buffer, head, macData, 0, macData.length);
			byte[] macByte = MAC.clacMac(StringUtil.hexStrToBytes(hexMacKey), macData);
			String calc_mac = StringUtil.bytesToHexStr(macByte);
			if(StringUtil.isEqualIgnoreCase(tempMessage.getField(fieldMaxSize), calc_mac)){
				tempMessage.setMacVerified(true);
			}else{
				tempMessage.setMacVerified(false);
			}
		}
		
		return tempMessage;
	}

	/**
	 * 解析域数据
	 * 
	 * @param buffer
	 * @param define
	 */
	private Field analysisFieldData(int index, byte[] buffer, FieldDefine define) {
		Field field = null;
		if (ISO8583Constants.LLLVAR == define.length) {
			field = analysisLLLVarField(buffer, define);
		} else if (ISO8583Constants.LLVAR == define.length) {
			field = analysisLLVarField(buffer, define);
		} else {
			field = analysisFixedField(buffer, define);
		}

		field.index = index;
		field.define = define;
		showMessage(String.format("[%d]%s(%s)", field.index, field.define.decribe, field.content));
		
		return field;
	}

	/**
	 * 解析三位变长域数据
	 * 
	 * @param buffer
	 * @param define
	 */
	private Field analysisLLLVarField(byte[] buffer, FieldDefine define) {
		return analysisVarField(buffer, define, 2);
	}

	/**
	 * 解析两位变长域数据
	 * 
	 * @param buffer
	 * @param define
	 */
	private Field analysisLLVarField(byte[] buffer, FieldDefine define) {
		return analysisVarField(buffer, define, 1);
	}

	/**
	 * 解析变长域数据
	 * 
	 * @param buffer
	 * @param define
	 * @param var
	 * @return
	 */
	private Field analysisVarField(byte[] buffer, FieldDefine define, int var) {
		Field field = new Field();
		int length = ByteUtil.byteToInt(buffer, srcPosition, var);
		byte[] data = null;
		if (FieldDefine.Type.BCD == define.type) {
			int tempLen = length;
			length = (length + 1) / 2;
			data = new byte[length];
			System.arraycopy(buffer, srcPosition + var, data, 0, length);
			field.content = StringUtil.bytesToHexStr(data);
			if (field.content.length() > tempLen) {
				field.content = field.content.substring(0, tempLen);
			}
		} else if (FieldDefine.Type.ASCII == define.type) {
			data = new byte[length];
			System.arraycopy(buffer, srcPosition + var, data, 0, length);
			field.content = new String(data);
		} else if (FieldDefine.Type.BINRARY == define.type) {
			data = new byte[length];
			System.arraycopy(buffer, srcPosition + var, data, 0, length);
			field.content = StringUtil.bytesToHexStr(data);
		}
		srcPosition += length + var;
		return field;
	}

	/**
	 * 解析定长域数据
	 * 
	 * @param buffer
	 * @param define
	 */
	private Field analysisFixedField(byte[] buffer, FieldDefine define) {
		Field field = new Field();
		int length = define.length;
		byte[] data = null;
		if (FieldDefine.Type.BCD == define.type) {
			data = new byte[length / 2];
			System.arraycopy(buffer, srcPosition, data, 0, length / 2);
			field.content = StringUtil.bytesToHexStr(data);
			srcPosition += length / 2;
		} else if (FieldDefine.Type.ASCII == define.type) {
			data = new byte[length];
			System.arraycopy(buffer, srcPosition, data, 0, length);
			field.content = new String(data);
			srcPosition += length;
		} else if (FieldDefine.Type.BINRARY == define.type) {
			data = new byte[length];
			System.arraycopy(buffer, srcPosition, data, 0, length);
			field.content = StringUtil.bytesToHexStr(data);
			srcPosition += length;
		}

		return field;
	}

	/**
	 * 解析指定长度的数据
	 * 
	 * @param buffer
	 * @param length
	 * @return
	 */
	private String analysisTragetLengthData(byte[] buffer, int length) {
		byte[] temp = new byte[length];
		System.arraycopy(buffer, srcPosition, temp, 0, length);
		srcPosition += length;
		return StringUtil.bytesToHexStr(temp);
	}

	/**
	 * 解析位图数据
	 * 
	 * @param hex
	 *            十六进制位图信息
	 * @return 位图信息集合
	 */
	private List<Integer> analysisBitMap(byte[] buffer) {
		// 获取位图的数据
		byte[] temp = new byte[ISO8583Constants.BITMAP_LENGTH];
		System.arraycopy(buffer, srcPosition, temp, 0, ISO8583Constants.BITMAP_LENGTH);
		srcPosition += ISO8583Constants.BITMAP_LENGTH;

		List<Integer> fieldIndexList = new ArrayList<Integer>();
		BitSet bitSet = ByteUtil.byteToBitSet(temp);
		for (int i = 0; i < bitSet.size(); i++) {
			if (bitSet.get(i)) {
				fieldIndexList.add(i + 1);
			}
		}

		return fieldIndexList;
	}

	/**
	 * 获取十六进制的报文长度(2个字节表示4位长度)
	 * 
	 * @return
	 */
	private String countMessageLength() {
		String hex = Integer.toHexString(length).toUpperCase();
		if (hex.length() == 3) {
			hex = "0" + hex;
		} else if (hex.length() == 2) {
			hex = "00" + hex;
		} else if (hex.length() == 1) {
			hex = "000" + hex;
		}

		return hex;
	}
	
	private void showMessage(String msg){
		if(showFieldMessage){
			System.out.println(msg);
		}
	}
	
	public void setShowFieldMessage(boolean showFieldMessage) {
		this.showFieldMessage = showFieldMessage;
	}

	public void setHexMacKey(String hexMacKey) {
		this.hexMacKey = hexMacKey;
	}
	
	public void setFieldDefineMap(Map<Integer, FieldDefine> fieldDefineMap) {
		this.fieldDefineMap = fieldDefineMap;
	}
	
}
