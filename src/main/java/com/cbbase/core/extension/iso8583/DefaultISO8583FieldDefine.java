package com.cbbase.core.extension.iso8583;

import java.util.HashMap;
import java.util.Map;


public class DefaultISO8583FieldDefine {
	
	public static Map<Integer, FieldDefine> DEFAULT_DEFINE_MAP = new HashMap<Integer, FieldDefine>();
	
	static{
		// 0域,消息类型
		DEFAULT_DEFINE_MAP.put(0, new FieldDefine(FieldDefine.Type.BCD, 4, "消息类型"));
		// 1域,位图信息
		DEFAULT_DEFINE_MAP.put(1, new FieldDefine(FieldDefine.Type.BINRARY, 8, "位图信息"));
		// 2域,主账号
		DEFAULT_DEFINE_MAP.put(2, new FieldDefine(FieldDefine.Type.BCD, ISO8583Constants.LLVAR, "主账号"));
		// 3域,交易处理码
		DEFAULT_DEFINE_MAP.put(3, new FieldDefine(FieldDefine.Type.BCD, 6, "交易处理码"));
		// 4域,交易金额
		DEFAULT_DEFINE_MAP.put(4, new FieldDefine(FieldDefine.Type.BCD, 12, "交易金额", '0', FieldDefine.FixType.LEFT));
		// 5域,转账手续费
		DEFAULT_DEFINE_MAP.put(5, new FieldDefine(FieldDefine.Type.BCD, 12, "转账手续费", '0', FieldDefine.FixType.LEFT));
		// 7域,终端时间
		DEFAULT_DEFINE_MAP.put(7, new FieldDefine(FieldDefine.Type.BCD, 10, "终端时间"));
		// 11域,受卡方系统跟踪号
		DEFAULT_DEFINE_MAP.put(11, new FieldDefine(FieldDefine.Type.BCD, 6, "受卡方系统跟踪号", '0', FieldDefine.FixType.LEFT));
		// 12域,受卡方所在地时间(格式：hhmmss)
		DEFAULT_DEFINE_MAP.put(12, new FieldDefine(FieldDefine.Type.BCD, 6, "受卡方所在地时间"));
		// 13域,受卡方所在地日期(格式：MMDD)
		DEFAULT_DEFINE_MAP.put(13, new FieldDefine(FieldDefine.Type.BCD, 4, "受卡方所在地日期"));
		// 14域,卡有效期(格式：YYMM)
		DEFAULT_DEFINE_MAP.put(14, new FieldDefine(FieldDefine.Type.BCD, 4, "卡有效期"));
		// 15域,清算日期(格式：MMDD)
		DEFAULT_DEFINE_MAP.put(15, new FieldDefine(FieldDefine.Type.BCD, 4, "清算日期"));
		// 22域,服务点输入方式码
		DEFAULT_DEFINE_MAP.put(22, new FieldDefine(FieldDefine.Type.BCD, 4, "服务点输入方式码", '0', FieldDefine.FixType.LEFT));
		// 23域,卡序列号
		DEFAULT_DEFINE_MAP.put(23, new FieldDefine(FieldDefine.Type.BCD, 4, "卡序列号", '0', FieldDefine.FixType.LEFT));
		// 25域,服务点条件码
		DEFAULT_DEFINE_MAP.put(25, new FieldDefine(FieldDefine.Type.BCD, 2, "服务点条件码"));
		// 26域,服务点PIN获取码
		DEFAULT_DEFINE_MAP.put(26, new FieldDefine(FieldDefine.Type.BCD, 2, "服务点PIN获取码"));
		// 32域,受理方标识码
		DEFAULT_DEFINE_MAP.put(32, new FieldDefine(FieldDefine.Type.BCD, ISO8583Constants.LLVAR, "受理方标识码"));
		// 35域,2磁道数据
		DEFAULT_DEFINE_MAP.put(35, new FieldDefine(FieldDefine.Type.BCD, ISO8583Constants.LLVAR, "2磁道数据"));
		// 36域,3磁道数据
		DEFAULT_DEFINE_MAP.put(36, new FieldDefine(FieldDefine.Type.BCD, ISO8583Constants.LLLVAR, "3磁道数据"));
		// 37域,检索参考号
		DEFAULT_DEFINE_MAP.put(37, new FieldDefine(FieldDefine.Type.ASCII, 12, "检索参考号"));
		// 38域,授权标识应答码
		DEFAULT_DEFINE_MAP.put(38, new FieldDefine(FieldDefine.Type.ASCII, 6, "授权标识应答码"));
		// 39域,应答码
		DEFAULT_DEFINE_MAP.put(39, new FieldDefine(FieldDefine.Type.ASCII, 2, "应答码"));
		// 40域,终端请求ID
		DEFAULT_DEFINE_MAP.put(40, new FieldDefine(FieldDefine.Type.ASCII, 12, "终端请求ID"));
		// 41域,受卡机终端标识码
		DEFAULT_DEFINE_MAP.put(41, new FieldDefine(FieldDefine.Type.ASCII, 8, "受卡机终端标识码"));
		// 42域,受卡方标识码
		DEFAULT_DEFINE_MAP.put(42, new FieldDefine(FieldDefine.Type.ASCII, 15, "受卡方标识码"));
		// 44域,附加响应数据
		DEFAULT_DEFINE_MAP.put(44, new FieldDefine(FieldDefine.Type.ASCII, ISO8583Constants.LLVAR, "附加响应数据"));
		// 45域,附加响应数据
		DEFAULT_DEFINE_MAP.put(45, new FieldDefine(FieldDefine.Type.ASCII, ISO8583Constants.LLVAR, "附加响应数据"));
		// 48域,附加数据
		DEFAULT_DEFINE_MAP.put(48, new FieldDefine(FieldDefine.Type.BCD, ISO8583Constants.LLLVAR, "附加数据"));
		// 49域,交易货币代码
		DEFAULT_DEFINE_MAP.put(49, new FieldDefine(FieldDefine.Type.ASCII, 3, "交易货币代码"));
		// 52域,个人标识码数据
		DEFAULT_DEFINE_MAP.put(52, new FieldDefine(FieldDefine.Type.BINRARY, 8, "个人标识码数据"));
		// 53域,安全控制信息
		DEFAULT_DEFINE_MAP.put(53, new FieldDefine(FieldDefine.Type.BCD, 16, "安全控制信息"));
		// 54域,余额
		DEFAULT_DEFINE_MAP.put(54, new FieldDefine(FieldDefine.Type.BCD, ISO8583Constants.LLVAR, "余额"));
		// 55域,IC卡数据域
		DEFAULT_DEFINE_MAP.put(55, new FieldDefine(FieldDefine.Type.BINRARY, ISO8583Constants.LLLVAR, "IC卡数据域"));
		// 57域,自定义域
		DEFAULT_DEFINE_MAP.put(57, new FieldDefine(FieldDefine.Type.BINRARY, ISO8583Constants.LLLVAR, "自定义域"));
		// 58域,PBOC电子钱包标准的交易信息
		DEFAULT_DEFINE_MAP.put(58, new FieldDefine(FieldDefine.Type.ASCII, ISO8583Constants.LLLVAR, "PBOC电子钱包标准的交易信息"));
		// 59域,自定义域
		DEFAULT_DEFINE_MAP.put(59, new FieldDefine(FieldDefine.Type.ASCII, ISO8583Constants.LLLVAR, "自定义域"));
		// 60域,自定义域
		DEFAULT_DEFINE_MAP.put(60, new FieldDefine(FieldDefine.Type.BCD, ISO8583Constants.LLLVAR, "自定义域"));
		// 61域,原始信息域
		DEFAULT_DEFINE_MAP.put(61, new FieldDefine(FieldDefine.Type.BCD, ISO8583Constants.LLLVAR, "原始信息域"));
		// 62域,自定义域
		DEFAULT_DEFINE_MAP.put(62, new FieldDefine(FieldDefine.Type.BINRARY, ISO8583Constants.LLLVAR, "自定义域"));
		// 63域,自定义域
		DEFAULT_DEFINE_MAP.put(63, new FieldDefine(FieldDefine.Type.ASCII, ISO8583Constants.LLLVAR, "自定义域"));
		// 64域,报文鉴别码
		DEFAULT_DEFINE_MAP.put(64, new FieldDefine(FieldDefine.Type.BINRARY, 8, "报文鉴别码"));
		
	}
	
	
}
