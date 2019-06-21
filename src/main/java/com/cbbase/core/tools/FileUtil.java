package com.cbbase.core.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;


/**
 * 对文件的一次性操作
 * </br>连续操作可以使用Files.newXXX();
 * @author changbo
 * 
 */
public class FileUtil {
	
	public static byte[] readAsBytes(String fileName) {
		try {
			return Files.readAllBytes(Paths.get(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String readAsString(String fileName) {
		try {
			return new String(readAsBytes(fileName), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String readAsBase64(String fileName) {
		try {
			byte[] bytes = readAsBytes(fileName);
			if(bytes != null) {
				return StringUtil.encodeBase64(bytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void clear(String fileName) {
		try {
			Files.delete(Paths.get(fileName));
			createFile(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void appendLine(String fileName) {
		appendLine(fileName, new byte[] {});
	}
	
	public static void appendLine(String fileName, byte[] bytes) {
		try {
	    	OutputStream os = Files.newOutputStream(Paths.get(fileName), StandardOpenOption.APPEND);
	    	if(bytes != null && bytes.length > 0) {
		    	os.write(bytes);
	    	}
	    	os.write("\n".getBytes());
	    	os.flush();
	    	os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void appendLine(String fileName, String str) {
		try {
	    	BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName), StandardOpenOption.APPEND);
	    	writer.write(str+"\n");
	    	writer.flush();
	    	writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<String> readAllLines(String fileName) {
		try {
			return Files.readAllLines(Paths.get(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void writeFile2OutputStream(File file, OutputStream out){
		try {
			StreamUtil.inToOut(new FileInputStream(file), out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void writeInputStream2File(InputStream in, File file){
        try {
			StreamUtil.inToOut(in, new FileOutputStream(file));
        } catch (Exception e) {
			e.printStackTrace();
        }
	}

	public static void copyFile(String fromFile, String toFile){
		Path fromPath = Paths.get(fromFile);
		Path toPath = Paths.get(toFile);
		try {
		    Files.copy(fromPath, toPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public static void copyFolder(String fromFile, String toFile){
		File fromFolder = new File(fromFile);
		File toFolder = new File(toFile);
		if(fromFolder.isFile()) {
			return;
		}
		createFolder(toFolder.getAbsolutePath());
		String[] files = fromFolder.list();
		if(files == null) {
			return;
		}
		String from = fromFolder.getAbsolutePath().replaceAll("\\\\", "/");
		String to = toFolder.getAbsolutePath().replaceAll("\\\\", "/");;
        for (int i = 0; i < files.length; i++) {
            if ((new File(from+"/"+files[i])).isDirectory()) {
            	copyFolder(from+"/"+files[i], to+"/"+files[i]);
            }
            if (new File(from+"/"+files[i]).isFile()) {
                copyFile(from+"/"+files[i], to+"/"+files[i]);
            }
        }
	}
	
	public static void createFile(String fileName){
        try {
        	createFolder(Paths.get(fileName).getParent().toString());
        	if(!Paths.get(fileName).toFile().exists()) {
            	Files.createFile(Paths.get(fileName));
        	}
        } catch (Exception e) {
			e.printStackTrace();
        }
	}
	
	public static void createFolder(String folderName){
        try {
        	if(!Paths.get(folderName).toFile().exists()) {
    			Files.createDirectories(Paths.get(folderName));
        	}
        } catch (Exception e) {
			e.printStackTrace();
        }
	}

	
	public static void createFile(String fileName, String text){
		createFile(fileName, text.getBytes());
	}
	
	public static void createFile(String fileName, byte[] bytes){
        try {
    		createFile(fileName);
        	OutputStream os = Files.newOutputStream(Paths.get(fileName), StandardOpenOption.TRUNCATE_EXISTING);
        	os.write(bytes);
        	os.flush();
        	os.close();
        } catch (Exception e) {
			e.printStackTrace();
        }
	}
	
	public static void rename(String fileName, String newFileName){
		File file = new File(fileName);
		createFile(fileName);
    	file.renameTo(new File(newFileName));
	}
	
	public static String getSuffix(File file){
	    return getSuffix(file.getName());
	}
	
	public static String getSuffix(String fileName){
	    return fileName.substring(fileName.lastIndexOf(".")+1);
	}
	
	public static String readTail(String fileName, int length) {
        try {
    		ByteBuffer buffer = ByteBuffer.allocate(length);
    		FileChannel channel = FileChannel.open(Paths.get(fileName), StandardOpenOption.READ);
    		channel.read(buffer, channel.size() - length);
    		return new String(buffer.array());
        } catch (Exception e) {
			e.printStackTrace();
        }
        return null;
	}
	
	public static void splitFile(String fileName, int size) {
		try {
			BufferedReader br = Files.newBufferedReader(Paths.get(fileName));
			BufferedWriter bw = null;
			int index = 0;
			String line = null;
			while((line = br.readLine()) != null) {
				int num = (index/size)+1;
				if(index%size == 0) {
					if(bw != null) {
						bw.flush();
						bw.close();
					}
					String newFileName = fileName+"."+StringUtil.leftPad(""+num, '0', 3);
					createFile(newFileName);
					bw = Files.newBufferedWriter(Paths.get(newFileName), StandardOpenOption.TRUNCATE_EXISTING);
				}
				bw.write(line+"\n");
			}
			if(index%size != 0) {
				if(bw != null) {
					bw.flush();
					bw.close();
				}
			}
			index++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void mergeFile(String fileName, List<String> sourceList) {
		
	}
}
