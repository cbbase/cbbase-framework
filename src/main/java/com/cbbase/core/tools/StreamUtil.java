package com.cbbase.core.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * 
 * @author changbo
 *
 */
public class StreamUtil {
	
	public static final int BUFF_LENGTH = 10240;

	public static boolean inToOut(InputStream in, OutputStream out){
        int byteread = 0; // 读取的字节数  
        try {  
            byte[] buffer = new byte[BUFF_LENGTH];
            while ((byteread = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteread);
            }
            out.flush();
            return true;
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	close(in);
        	close(out);
        }  
        return false;
	}
	
	public static byte[] readBytes(InputStream is) throws Exception{
		return readBytes(is, BUFF_LENGTH);
	}
	
	public static byte[] readBytes(InputStream is, int length) throws Exception{
		try {
			byte[] buff = new byte[length];
			int readed = is.read(buff);
			if(readed < 0){
				return null;
			}
			byte[] data = new byte[readed];
			System.arraycopy(buff, 0, data, 0, readed);
			return data;
		} finally {
        	close(is);
        }
	}
	
	public static String readString(InputStream is){
		return readString(is, 0);
	}
	
	public static String readString(InputStream is, int maxLength){
        return readString(new BufferedReader(new InputStreamReader(is)), maxLength);
	}

	public static String readString(BufferedReader br){
		return readString(br, 0);
	}

	public static String readString(BufferedReader br, int maxLength){
        StringBuilder sb = new StringBuilder();
        String buf = null;
        try {
			while ((buf = br.readLine()) != null) {  
			    sb.append(buf).append("\n");
			    if(maxLength > 0 && sb.length() > maxLength){
			    	sb.setLength(maxLength);
			    	break;
			    }
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
        	close(br);
        }
        return sb.toString();
	}
	
	public static void close(InputStream is){
		if(is != null){
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void flush(OutputStream out){
		if(out != null){
			try {
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(OutputStream out){
		if(out != null){
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void flushAndClose(OutputStream out){
		if(out != null){
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Reader reader){
		if(reader != null){
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Writer writer){
		if(writer != null){
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void flushAndClose(Writer writer){
		if(writer != null){
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
