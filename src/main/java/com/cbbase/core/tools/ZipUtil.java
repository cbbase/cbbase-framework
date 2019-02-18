package com.cbbase.core.tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 
 * @author changbo
 *
 */
public class ZipUtil {
	
	public static final int BUFFER_SIZE = 8192;
	
	public static boolean zip(String fileOrFolder, String outFile){
		return zip(fileOrFolder, outFile, null);
	}

	public static boolean zip(String fileOrFolder, String outFile, String[] excludeFiles){
		return zip(fileOrFolder, outFile, excludeFiles, System.getProperty("sun.jnu.encoding"));
	}

	public static boolean zip(String fileOrFolder, String outFile, String[] excludeFiles, String encodingSet) {
		File file = new File(fileOrFolder);
		File zipFile = new File(outFile);
		if (!file.exists()) {
			return false;
		}
		ZipOutputStream out = null;
		try {
			FileOutputStream fis = new FileOutputStream(zipFile);
			CheckedOutputStream cos = new CheckedOutputStream(fis, new CRC32());
			out = new ZipOutputStream(cos);
			out.setEncoding(encodingSet);// 设置文件名编码方式
			String basedir = "";
			compress(file, out, basedir, excludeFiles);
		} catch (Exception e) {
			return false;
		} finally {
			StreamUtil.flushAndClose(out);
		}
		return true;
	}
	
	private static void compress(File file, ZipOutputStream out, String basedir, String[] excludeFiles){
	    if (!file.exists()){
	    	return;
	    }
	    if(excludeFiles != null && excludeFiles.length > 0 ){
	    	for(String excludeFile : excludeFiles){
	    		if(StringUtil.hasValue(excludeFile)){
	    	    	File exFile = new File(excludeFile);
	    	    	if(exFile.getAbsolutePath().equals(file.getAbsolutePath())){
	    	    		return;
	    	    	}
	    		}
	    	}
	    }
	    if(file.isDirectory()){
	    	for(File temp : file.listFiles()){
	    		compress(temp, out, basedir+file.getName() + "/", excludeFiles);
	    	}
	    }
	    BufferedInputStream bis = null;
	    try{
		    bis = new BufferedInputStream(new FileInputStream(file));
		    ZipEntry entry = new ZipEntry(basedir + file.getName());
		    out.putNextEntry(entry);
		    int count;
		    byte data[] = new byte[BUFFER_SIZE];
		    while ((count = bis.read(data, 0, BUFFER_SIZE)) != -1){
		        out.write(data, 0, count);
		    }
		    bis.close();
	    }catch (Exception e){
	    	return;
	    }finally {
			StreamUtil.close(bis);
		}
	}
	
	public static boolean unzip(String zipFileName, String outFolder){
		return unzip(zipFileName, outFolder, System.getProperty("sun.jnu.encoding"));
	}
	
	public static boolean unzip(String zipFileName, String outFolder, String encodingSet){
	    System.setProperty("sun.zip.encoding", encodingSet); //防止文件名中有中文时出错
	    File outDirFile = new File(outFolder);  
	    if (!outDirFile.exists()){
	        outDirFile.mkdirs();  
	    }
	    FileOutputStream fos = null;
	    InputStream is = null;
		try {
			ZipFile zipFile = new ZipFile(zipFileName, encodingSet);
	        for (Enumeration<ZipEntry> entries = zipFile.getEntries(); entries.hasMoreElements();) {  
	            ZipEntry ze = (ZipEntry) entries.nextElement();  
	            File file = new File(outDirFile, ze.getName());  
	            if (ze.isDirectory()) {// 是目录，则创建之  
	                file.mkdirs();  
	            } else {  
	                File parent = file.getParentFile();  
	                if (parent != null && !parent.exists()) {  
	                    parent.mkdirs();  
	                }
	                file.createNewFile();  
	                fos = new FileOutputStream(file);  
	                is = zipFile.getInputStream(ze);  
	                StreamUtil.inToOut(is, fos);
	                fos.close();  
	                is.close();  
	            }  
	        }  
	        zipFile.close();  
		} catch (IOException e) {
			return false;
		} finally {
			StreamUtil.close(is);
			StreamUtil.close(fos);
		}
		return true;
	}
    

}
