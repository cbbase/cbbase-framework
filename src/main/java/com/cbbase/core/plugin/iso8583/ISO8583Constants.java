package com.cbbase.core.plugin.iso8583;


public class ISO8583Constants {
	
	/** 8583消息域大小 */
	public static final int FIELD_MAX_SIZE = 64;
	/** 报文最大长度 */
	public static final int MESSAGE_MAX_LENGTH = 10000;
	/** TPDU长度 */
	public static final int MESSAGE_TPDU_LENGTH = 5;
	/** 报文头长度 */
	public static final int MESSAGE_HEADER_LENGTH = 6;
	/** 消息类型长度 */
	public static final int MESSAGE_TYPE_LENGTH = 2;
	/** 位图长度 */
	public static final int BITMAP_LENGTH = 8;
	/** 两位可变长度 */
	public static final int LLVAR = -2;
	/** 三位可变长度 */
	public static final int LLLVAR = -3;
	
	
}
