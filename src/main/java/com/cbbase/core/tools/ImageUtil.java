package com.cbbase.core.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 
 * @author changbo
 *
 */
public class ImageUtil {
	
	public static final int DEFAULT_WIDTH = 160;
	public static final int DEFAULT_HEIGHT = 40;
	
	
	public static String img2Base64(File file){
        String imgBase64 = null;
        FileInputStream fis = null;
        try {
            byte[] content = new byte[(int) file.length()];
            fis = new FileInputStream(file);
            fis.read(content);
            fis.close();
            imgBase64 = StringUtil.encodeBase64(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	StreamUtil.close(fis);
        }
        return imgBase64;
	}
	
	public static BufferedImage randomImg(String code) {
		return randomImg(code, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public static BufferedImage randomImg(String code, int width, int height) {
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
		Graphics2D g = image.createGraphics();
		g.setColor(Color.WHITE);  
		g.fillRect(0, 0, width, height);
		
		for (int i = 0; i < 50; i++) {
			int xs = RandomUtil.random(0, width);
			int ys = RandomUtil.random(0, height);
			int xe = xs+RandomUtil.random(0, width/4);
			int ye = ys+RandomUtil.random(0, height/4);
			int red = RandomUtil.random(0, 255);
			int green = RandomUtil.random(0, 255);
			int blue = RandomUtil.random(0, 255);
			g.setColor(new Color(red, green, blue));
			g.drawLine(xs, ys, xe, ye);
		}
		
		int fontSize = height-4;
        Font font = new Font("Algerian", Font.ITALIC, fontSize);
        g.setFont(font);

		int codeWidth = width / code.length();
		int codeHeight = height - 4;
		for(int i=0; i<code.length(); i++) {
			int red = RandomUtil.random(0, 255);
			int green = RandomUtil.random(0, 255);
			int blue = RandomUtil.random(0, 255);
			g.setColor(new Color(red, green, blue));
			g.drawString(""+code.charAt(i), i*codeWidth+10, codeHeight-2);  
		}
		return image;
	}

}
