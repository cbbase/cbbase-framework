package com.cbbase.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cbbase.core.handler.ExceptionHandler;
import com.cbbase.core.interceptor.AuthInterceptor;

/**
 * web配置类，与原web.xml功能相同
 */
@Configuration
public class CustomWebMvcConfigurer implements WebMvcConfigurer {
	/**
	 * 	设置默认访问页
	 */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.jsp");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    /**
     * 	全局异常处理
     * @return
     */
    @Bean
    public ExceptionHandler globalExceptionResolver(){
        return new ExceptionHandler();
    }
    
    /**
     *	 权限拦截器
     * @return
     */
    @Bean
    public AuthInterceptor authInterceptor(){
        return new AuthInterceptor();
    }

    /**
     *	 权限拦截器注册
     * @return
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(authInterceptor()).addPathPatterns("/**").order(1);
    }
    
}
