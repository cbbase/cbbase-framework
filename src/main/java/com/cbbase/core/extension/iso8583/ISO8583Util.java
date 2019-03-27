package com.cbbase.core.extension.iso8583;

import com.cbbase.core.encrypt.TripleDES;
import com.cbbase.core.tools.StringUtil;

/**
 * 
 * @author changbo
 *
 */
public class ISO8583Util {

	public static String decryptPin(String data, String pinKey, String cardAsn){
        String _data = TripleDES.decrypt(pinKey, data);
        byte[] result = StringUtil.hexStrToBytes(_data);
        
        String cardPanStr = "0000" + cardAsn.substring(cardAsn.length() - 13, cardAsn.length() - 1);
        byte[] cardPan = StringUtil.hexStrToBytes(cardPanStr);
        for(int i = 0; i < 8; i++){
            result[i] = (byte)(result[i] ^ cardPan[i]);
        }
        int len = result[0];
        boolean isJ = false;
        if(len % 2 != 0){
            len++;
            isJ = true;
        }
        int _len = len / 2;
        byte[] pinResult = new byte[_len];
        System.arraycopy(result, 1, pinResult, 0, _len);
        String pin = StringUtil.bytesToHexStr(pinResult);
        if(isJ){
            pin = pin.substring(0, len - 1);
        }
		return pin;
	}
	
	
    public static String encryptPin(String pin, String pinKey, String cardAsn){
        
        String cardPanStr = "0000" + cardAsn.substring(cardAsn.length() - 13, cardAsn.length() - 1);
        byte[] cardPan = StringUtil.hexStrToBytes(cardPanStr);
        int pinLen = pin.length();
        String strPinBlock = String.valueOf(pinLen) + pin;
        if(pinLen < 10){
            strPinBlock = "0" + strPinBlock;
        }
        strPinBlock = StringUtil.rightPad(strPinBlock, 'F', 16);
        byte[] pinBlock = StringUtil.hexStrToBytes(strPinBlock);
        byte[] result = new byte[8];
        for(int i = 0; i < 8; i++){
            result[i] = (byte)(pinBlock[i] ^ cardPan[i]);
        }
        return TripleDES.encrypt(pinKey, StringUtil.bytesToHexStr(result)).substring(0, 16);
    }
    

	public static String decryptTrack(String track, String key, int encryptType){
		if(StringUtil.isEmpty(track)){
			return track;
		}
		//没有加解密
		if(encryptType == 0){
			return track;
		}
		//常规加密(部分加密)
		if(encryptType == 1){
			return decryptTrackNormal(track, key);
		}
		//完全加密1(长度2位)
		if(encryptType == 2){
			return decryptTrackAll(track, key, 2);
		}
		//完全加密2(长度4位)
		if(encryptType == 3){
			return decryptTrackAll(track, key, 4);
		}
		
		//常规加解密
		return decryptTrackNormal(track, key);
	}
	

	private static String decryptTrackNormal(String track, String key){
		int len = track.length();
		int offset = 2;
		if(len % 2 != 0){
			offset = 1;
		}
		int offset1 = len - 16 - offset;
		String etrack = track.substring(offset1,len-offset);
		String dtrack = TripleDES.decrypt(key, etrack);
		dtrack = track.substring(0,offset1) + dtrack + track.substring(len-offset);
		return dtrack;
	}
	

	private static String decryptTrackAll(String track, String key, int headLength){
		String dtrack = TripleDES.decrypt(key, track);
		int len = Integer.parseInt(dtrack.substring(0, headLength));
		String _track = dtrack.substring(headLength, headLength+len);
		return _track;
	}
	
}
