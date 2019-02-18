package com.cbbase.core.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;


/**
 * 二维码生成以及解析
 * @author changbo
 * 
 */
public class QRCode {
	
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;
	
	private int width = 200;
	private int height = 200;
	private String format = "png";
	private BitMatrix bitMatrix = null;
	public QRCode() {
		
	}
	
	public QRCode(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public QRCode(int width, int height, String format) {
		this.width = width;
		this.height = height;
		this.format = format;
	}

	public QRCode createImg(String content){
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();  
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
        try {
			bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
		} catch (WriterException e) {
			e.printStackTrace();
		}
        return this;
	}

	private BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	public boolean writeToFile(String fileName){
		BufferedImage image = toBufferedImage(bitMatrix);
		try {
			ImageIO.write(image, format, new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean writeToStream(BitMatrix matrix, String format, OutputStream stream){
		BufferedImage image = toBufferedImage(matrix);
		try {
			ImageIO.write(image, format, stream);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 解析二维码
	 * @param file
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String decodeQRCode(File file){
		try {
	        BufferedImage image = ImageIO.read(file);//读取文件，识别成一个图片  
	        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));  
	        HashMap hints = new HashMap();  
	        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); 
	        MultiFormatReader formatReader = new MultiFormatReader();   
	        Result result = formatReader.decode(binaryBitmap, hints);
	        return result.toString();  
		} catch (Exception e) {
			e.printStackTrace();
		}  
		return null;
	}
	

}
