package com.cbbase.core.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 主要针对实体类,有重载方法时,最好不要用
 * @author changbo
 *
 */
public class ObjectUtil {
	
	@SuppressWarnings("unchecked")
	public static <T> T deepClone(T obj){
		try{
			return (T) bytes2Object(object2Bytes(obj));
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] object2Bytes(Object obj){
		if(obj == null){
			return null;
		}
		try{
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(obj);
			return byteOut.toByteArray();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object bytes2Object(byte[] bytes){
		if(bytes == null){
			return null;
		}
		try{
			ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
			ObjectInputStream in = new ObjectInputStream(byteIn);
			return in.readObject();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object newInstance(String className){
		return newInstance(className, null);
	}
	
	public static Object newInstance(String className, Object[] objects){
		if(StringUtil.isEmpty(className)){
			return null;
		}
		try {
			Class<?> clazz = Class.forName(className);
			return newInstance(clazz, objects);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T newInstance(Class<T> clazz){
		return newInstance(clazz, null);
	}

	public static <T> T newInstance(Class<T> clazz, Object[] objects){
		if(clazz == null){
			return null;
		}
		try {
			Constructor<T> cons = clazz.getConstructor();
			if(objects != null){
				return cons.newInstance(objects);
			}else{
				return cons.newInstance();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Method getMethod(Class<?> clazz, String methodName) {
		try {
	        for(; clazz != Object.class; clazz = clazz.getSuperclass()) {  
				Method[] methods = clazz.getDeclaredMethods();
				for (Method method : methods) {
					if(method.getName().equals(methodName)) {
						return method;
					}
				}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean setFieldValue(Object obj, String field, Object value){
		Class<?> clazz = obj.getClass();
		// method
		if(StringUtil.isEmpty(field)){
			return false;
		}
		String methodName = "set" + StringUtil.upperFirst(field);
		try {
			Method method = getMethod(clazz, methodName);
			method.setAccessible(true);
			method.invoke(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	public static Object getFieldValue(Object obj, String field){
		if(StringUtil.isEmpty(field)){
			return null;
		}
		String methodName = "get" + StringUtil.upperFirst(field);
		try {
			return invoke(obj, methodName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static Object invoke(Object obj, String methodName) {
		return invoke(obj, methodName, null);
	}
	
	public static Object invoke(Object obj, String methodName, Object[] params){
		try{
			Method method = getMethod(obj.getClass(), methodName);
			method.setAccessible(true);
			return method.invoke(obj, params);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean isClassField(Class<?> clazz, String field){
		// method
		if(StringUtil.isEmpty(field)){
			return false;
		}
		String methodName = "get" + StringUtil.upperFirst(field);
		try {
			Method method = clazz.getMethod(methodName);
			return method != null;
		} catch (Exception e) {
			e.printStackTrace();
		}
        return false;
	}
	
	public static List<String> getFields(Class<?> clazz){
		if(clazz == null){
			return null;
		}
		// method
        Field[] fields = clazz.getDeclaredFields();
        List<String> fieldNameList = new ArrayList<String>();
        for (Field temp : fields) {
        	fieldNameList.add(temp.getName());
        }
        return fieldNameList;
	}
	
	public static Class<?> getFieldClass(Class<?> clazz, String field){
		if(clazz == null){
			return null;
		}
		// method
        Field _field = null;
		try {
			_field = clazz.getDeclaredField(field);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
        return _field.getType();
	}
	
	/**
	 * 复制非空属性
	 * @param source
	 * @param target
	 */
	public static void copyNonNullField(Object source, Object target) {
		if(source == null || target == null) {
			return;
		}
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field temp : fields) {
        	Object value = getFieldValue(source, temp.getName());
        	if(value != null) {
        		setFieldValue(target, temp.getName(), value);
        	}
        }
	}
}
