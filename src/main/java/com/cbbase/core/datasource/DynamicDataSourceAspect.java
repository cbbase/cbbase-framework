package com.cbbase.core.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.cbbase.core.annotation.DataSourceName;


/**
 * 动态数据源切换处理器
 */
@Aspect
@Order(-1)  // 该切面应当先于 @Transactional 执行
@Component
public class DynamicDataSourceAspect {
    
    /**
     * 切换数据源
     * @param point
     * @param dataSourceKey
     */
    @Before("@annotation(dataSourceName))")
    public void switchDataSource(JoinPoint point, DataSourceName dataSourceName) {
        //切换数据源
    	DynamicDataSource.setDataSource(dataSourceName.value());
    }
    
    /**
     * 重置数据源
     * @param point
     * @param dataSourceKey
     */
    @After("@annotation(dataSourceName))")
    public void restoreDataSource(JoinPoint point, DataSourceName dataSourceName) {
    	//重置数据源
    	DynamicDataSource.clearDataSource();
    }
}