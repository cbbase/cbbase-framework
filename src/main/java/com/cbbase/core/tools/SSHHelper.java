package com.cbbase.core.tools;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Properties;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SSHHelper {
	
	private Session session;
	private ChannelExec channel;

    public static SSHHelper getSSHHelper(){
    	return new SSHHelper();
    }
    
    public SSHHelper connect(String ip, String username, String password){
    	return connect(ip, 22, username, password);
    }

    public SSHHelper connect(String ip, int port, String username, String password){
		try {
			JSch jsch = new JSch();
	        session = jsch.getSession(username, ip, port);  
	        session.setPassword(password);  
	        Properties config = new Properties();  
	        config.put("StrictHostKeyChecking", "no");  
	        session.setConfig(config);
	        session.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}  
    	return this;
    }
    
    public String execute(String cmd){
    	if(StringUtil.isEmpty(cmd)){
    		return null;
    	}
    	try {
            channel = (ChannelExec) session.openChannel("exec");  
    		channel.setCommand(cmd);
            channel.connect();
            return StreamUtil.readString(channel.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
	public String shell(String cmd){
    	if(StringUtil.isEmpty(cmd)){
    		return null;
    	}
    	try {
			ChannelShell channelShell = (ChannelShell)session.openChannel("shell");
			PipedInputStream pipeIn = new PipedInputStream();
			PipedOutputStream pipeOut = new PipedOutputStream(pipeIn);
			channelShell.setInputStream(pipeIn);
			channelShell.connect();
			pipeOut.write(cmd.getBytes());
			pipeOut.close();
			pipeIn.close();
			channelShell.disconnect();
	        return StreamUtil.readString(channelShell.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
	}

    public void disconnect(){
    	try {
            if(channel != null && channel.isConnected()){  
            	channel.disconnect();  
            }
            if (session != null && session.isConnected()) {  
                session.disconnect();
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}
