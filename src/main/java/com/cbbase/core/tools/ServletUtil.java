package com.cbbase.core.tools;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author changbo
 *
 */
public class ServletUtil {
	
	public static final int DEFAULT_POST_MAX_LENGTH = 100000;
	
	public static String getLastPath(HttpServletRequest request){
		String uri = request.getRequestURI();
		int index = uri.lastIndexOf("/");
		return uri.substring(index, uri.length());
	}
	
	public static List<String> getParameterArray(HttpServletRequest request, String key){
		List<String> list = new ArrayList<String>();
		String[] array = request.getParameterValues(key);
		if(array == null) {
			return list;
		}
		Collections.addAll(list, array);
		return list;
    }
	
	public static boolean isNewSession(HttpServletRequest request) {
		if (request.getSession(false) == null) {
			if (request.getSession(true).isNew()) {
				return true;
			}
		}
		return false;
	}
	
    public static String readRequestData(HttpServletRequest request){
    	//默认最大读取10万字符
    	return readRequestData(request, DEFAULT_POST_MAX_LENGTH);
    }
    
    public static String readRequestData(HttpServletRequest request, int maxLength){
		try {
			return StreamUtil.readString(request.getReader(), maxLength);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }

    public static void returnString(HttpServletResponse response, String data){
    	PrintWriter out = null;
		try {
	    	response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html");
			out = response.getWriter();
	        out.print(data);
	        out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			StreamUtil.close(out);
        }
    }

    public static void returnFile(HttpServletResponse response, byte[] bytes, String fileName){
    	OutputStream out = null;
    	try {
    		if(bytes == null){
    			return;
    		}
    		response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName,"UTF-8"));
	    	response.setCharacterEncoding("utf-8");
	        response.setContentType("multipart/form-data");
	    	out = response.getOutputStream();
	        out.write(bytes);
	        out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			StreamUtil.close(out);
        }
    }
    
    public static void returnFile(HttpServletResponse response, File file){
    	OutputStream out = null;
    	try {
    		if(file == null || !file.exists()){
    			return;
    		}
    		response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(file.getName(),"UTF-8"));
	    	response.setCharacterEncoding("utf-8");
	        response.setContentType("multipart/form-data");
	    	out = response.getOutputStream();
	        FileUtil.writeFile2OutputStream(file, out);
	        out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			StreamUtil.close(out);
        }
    }
    
    public static void returnImg(HttpServletResponse response, RenderedImage image, String format){
    	OutputStream os = null;
    	try {
		    response.setHeader("Pragma", "no-cache");
		    response.setHeader("Cache-Control", "no-cache");
		    response.setDateHeader("Expires", 0L);
		    response.setContentType("image/"+format);
        	os = response.getOutputStream();
			ImageIO.write(image, format, os);
	    	os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			StreamUtil.close(os);
        }
    }
    
    public static void returnImg(HttpServletResponse response, File imageFile){
    	try {
    		if(!imageFile.exists()){
    			return;
    		}
			returnImg(response, ImageIO.read(imageFile), FileUtil.getSuffix(imageFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public static String getClientIp(HttpServletRequest request) {
    	String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    		ip = request.getHeader("Proxy-Client-IP");
    	}
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    		ip = request.getHeader("WL-Proxy-Client-IP");
    	}
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    		ip = request.getRemoteAddr();
    	}
    	if(StringUtil.hasValue(ip) && ip.indexOf(",") > 0){
    		ip = ip.substring(0, ip.indexOf(","));
    	}
		return ip;
	}
    
    
    public static Map<String, String> getRequestMap(HttpServletRequest request){
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {  
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; values!=null && i<values.length; i++) {
            	if(i == 0){
            		valueStr = values[i];
            	}else{
            		valueStr = valueStr + "," + values[i];
            	}
            }  
            params.put(name, valueStr);
        }
        return params;
    }
    
    public static void setAllowCORS(HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin","*");
		response.setHeader("Access-Control-Allow-Methods","POST");
		response.setHeader("Access-Control-Allow-Headers","Access-Control");
		response.setHeader("Allow","POST");
    }
    
    public static String getCookie(HttpServletRequest request, String key) {
    	Cookie[] cookies = request.getCookies();
		if(cookies == null){
			return null;
		}
		for (Cookie cookie : cookies) {
			if (key.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
    }
    
    public static void setCookie(HttpServletResponse response, String key, String value) {
    	Cookie cookie = new Cookie(key, value);
    	cookie.setPath("/");
    	response.addCookie(cookie); 
    }
    
    public static void setSSLCookie(HttpServletResponse response, String key, String value, String domain) {
    	StringBuffer sb = new StringBuffer();
		sb.append(key + "=" + value);
		sb.append(";path=/");
		sb.append(";HttpOnly");
		sb.append(";domain=" + domain);
		response.addHeader("Set-Cookie",sb.toString());
    }
}
