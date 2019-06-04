package com.cbbase.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * 权限注释类,用于controller方法,标注权限, AuthInterceptor使用此注释判断权限
 * @author changbo
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authority {
	
	/**
	 * 检查登录
	 * @return
	 */
	boolean login() default false;
	
	/**
	 * 检查权限(检查多个权限时,用逗号分隔)
	 * @return
	 */
	String value() default "";
	
}
