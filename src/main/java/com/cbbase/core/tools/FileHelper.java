package com.cbbase.core.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Files是jdk8以上才支持
 * @author changbo
 *
 */
public class FileHelper {
	
	public static int READ = 0;
	public static int APPEND = 1;
	public static int WRITE = 2;
	
	private BufferedReader reader;
	private BufferedWriter writer;
	
	private FileHelper(String fileName, int type) {
		try {
			if(!new File(fileName).exists()) {
				FileUtil.createFile(fileName);
			}
			if(type == READ) {
				reader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8);
			}else if (type == APPEND) {
				writer = Files.newBufferedWriter(Paths.get(fileName), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
			}else{
				writer = Files.newBufferedWriter(Paths.get(fileName), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static FileHelper getFileHelper(String fileName, int type) {
		return new FileHelper(fileName, type);
	}
	
	public void close() {
		StreamUtil.close(reader);
		StreamUtil.flushAndClose(writer);
	}
	
	public String readLine() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public void skip(int skip) {
		for(int i=0; i<skip; i++) {
			readLine();
		}
	}
	
	public void write(String str) {
		try {
			writer.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
