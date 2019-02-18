package com.cbbase.core.tools;

import java.io.File;    
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;    
import org.apache.commons.net.ftp.FTPReply;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;    

/**
 * 
 * @author changbo
 *
 */
public class FtpHelper {

	public static final int FTP = 0;
	public static final int SFTP = 1;
	
	private int ftpModel = 0;
	private Session session;
	private ChannelSftp sftp;
    private FTPClient ftp;
    
    private FtpHelper(int ftpModel){
    	this.ftpModel = ftpModel;
    }

    public static FtpHelper getFtpHelper(){
    	return getFtpHelper(FTP);
    }
    public static FtpHelper getFtpHelper(int ftpModel){
    	return new FtpHelper(ftpModel);
    }

    public FtpHelper connect(String ip, String username, String password){
    	if(ftpModel == FTP){
        	return connect(ip, 21, username, password);
    	}else if(ftpModel == SFTP){
        	return connect(ip, 22, username, password);
    	}
    	return null;
    }
    
    public FtpHelper connect(String ip, int port, String username, String password){
		try {
	    	if(ftpModel == FTP){
	        	ftp = new FTPClient();
    			ftp.connect(ip, port);
    			ftp.login(username, password);
    			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
    			if(!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
    				ftp.disconnect();
    			}
	    	}else if(ftpModel == SFTP){
	    		JSch jsch = new JSch();
    	        session = jsch.getSession(username, ip, port);  
    	        session.setPassword(password);  
    	        Properties config = new Properties();  
    	        config.put("StrictHostKeyChecking", "no");  
    	        session.setConfig(config);
    	        session.connect();
    	        sftp = (ChannelSftp) session.openChannel("sftp");
    	        sftp.connect();
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}  
    	return this;
    }
    
    public FtpHelper changeDir(String dir){
    	try {
    		if(ftpModel == FTP){
    			ftp.changeWorkingDirectory(dir);
        	}else if(ftpModel == SFTP){
        		sftp.cd(dir);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}     
    	return this;
    }
    
    public FtpHelper changeToParentDir(){
		try {
    		if(ftpModel == FTP){
    			ftp.changeToParentDirectory();
        	}else if(ftpModel == SFTP){
        		sftp.cd("..");
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
    }
    
    public FtpHelper makeDir(String dir){
		try {
			if(ftpModel == FTP){
				ftp.makeDirectory(dir);
	    	}else if(ftpModel == SFTP){
	    		sftp.mkdir(dir);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
    }
    
    public FtpHelper upload(File file){
    	if(file == null){
    		return this;
    	}
    	if(!file.exists() || file.isDirectory()){
    		return this;
    	}

		InputStream is = null;
		try {
			is = new FileInputStream(file);
    		if(ftpModel == FTP){
	    		ftp.storeFile(file.getName(), is);
        	}else if(ftpModel == SFTP){
        		sftp.put(is, file.getName());
        	}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			StreamUtil.close(is);
		}
    	return this;
    }
    
    public FtpHelper download(String fileName , String localPath){
		OutputStream os = null;
		try {
			os = new FileOutputStream(new File(localPath + "/" + fileName));
			if(ftpModel == FTP){
    			ftp.retrieveFile(fileName, os);
			}else if(ftpModel == SFTP){
		        sftp.get(fileName, os);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
    }
    
    public void disconnect(){
    	try {
            if(sftp != null && sftp.isConnected()){  
            	sftp.disconnect();  
            }
            if (session != null && session.isConnected()) {  
                session.disconnect();
            }
            if(ftp != null && ftp.isConnected()){
    			ftp.logout();
    			ftp.disconnect();
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
} 
