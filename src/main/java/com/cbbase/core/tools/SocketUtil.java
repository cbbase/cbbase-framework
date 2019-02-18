package com.cbbase.core.tools;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 
 * @author changbo
 *
 */
public class SocketUtil {
	
	public static final int DEFAULT_TIMEOUT = 10000;
	public static final int DEFAULT_LINGER = 1;
	public static final int BUFF_LENGTH = 4096;
	

	public static String request(String ip, int port, String message) throws Exception{
		return request(ip, port, message, DEFAULT_TIMEOUT);
	}

	public static String request(String ip, int port, String message, int timeout) throws Exception{
		Socket socket = null;
		DataInputStream is = null;
		DataOutputStream os = null;
		String back = null;
		try{
			socket = new Socket(ip, port);
			socket.setSoTimeout(timeout);
			socket.setTcpNoDelay(false);
			socket.setSoLinger(true, DEFAULT_LINGER);
			is = new DataInputStream(socket.getInputStream());
			os = new DataOutputStream(socket.getOutputStream());
			os.writeUTF(message);
			os.flush();
			back = is.readUTF();
		}finally {
			closeSocket(socket, is, os);
		}
		return back;
	}
	
	public static byte[] request(String ip, int port, byte[] message) throws Exception{
		return request(ip, port, message, DEFAULT_TIMEOUT);
	}
	
	public static byte[] request(String ip, int port, byte[] message, int timeout) throws Exception{
		Socket socket = null;
		OutputStream os = null;
		InputStream is = null;
		try{
			socket = new Socket(ip, port);
			socket.setSoTimeout(timeout);
			socket.setTcpNoDelay(false);
			socket.setSoLinger(true, DEFAULT_LINGER);
			os = socket.getOutputStream();
			is = socket.getInputStream();
			os.write(message);
			os.flush();
			return StreamUtil.readBytes(is, BUFF_LENGTH);
		} finally {
			closeSocket(socket, is, os);
		}
	}
	
	public static void closeSocket(Socket socket, InputStream is, OutputStream os){
		try {
			if(is != null){
				is.close();
			} 
			if(os != null){
				os.close();
			} 
			if(socket != null){
				socket.close();
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
