package com.cbbase.core.base;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cbbase.core.container.PageContainer;

/**
 * 
 * @author changbo
 * 
 */
public interface BaseDao {
	
	public Integer selectPageTotal(PageContainer pageContainer);
	
	public <T> List<T> selectPageList(PageContainer pageContainer);
	
	public <T> List<T> selectList(Object param);
	
	public <T> T selectById(@Param("id") String id);
	
	public int insert(Object param);
	
	public int batchInsert(List<?> paramList);
	
	public int delete(@Param("id") String id);
	
	public int batchDelete(List<String> idList);
	
	public int update(Object entity);
	
}
