package com.cbbase.core.base;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbbase.core.container.PageContainer;
import com.cbbase.core.tools.IdWorker;
import com.cbbase.core.tools.ObjectUtil;

/**
 * 
 * @author changbo
 *
 */
public class BaseService<T extends BaseDao> {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected T baseDao;

	@Autowired
	protected IdWorker idWorker;
	
	public <E> List<E> selectList(Object param){
		return baseDao.selectList(param);
	}
    
    public <E> E selectById(Long id) {
    	return baseDao.selectById(id);
    }
    
    public int insert(Object param) {
    	checkPrimaryId(param);
    	return baseDao.insert(param);
    }
    
    public int batchInsert(List<?> paramList) {
    	for(Object param : paramList) {
        	checkPrimaryId(param);
    	}
    	return baseDao.batchInsert(paramList);
    }
    
    public int delete(Long id) {
    	return baseDao.delete(id);
    }
    
    public int batchDelete(List<Long> idList) {
    	return baseDao.batchDelete(idList);
    }
    
    public int update(Object param) {
    	return baseDao.update(param);
    }
    
    public int updateNonNullField(Object param) {
    	Long id = (Long) ObjectUtil.getFieldValue(param, "id");
    	if(id == null) {
    		return 0;
    	}
    	Object obj = selectById(id);
    	if(obj == null) {
    		return 0;
    	}
    	ObjectUtil.copyNonNullField(param, obj);
    	return baseDao.update(obj);
    }
	
    public PageContainer selectPage(PageContainer pageContainer) {
		Integer total = baseDao.selectPageTotal(pageContainer);
		pageContainer.setRowCount(total);
		if(pageContainer.getStartRow() > total) {
			return pageContainer;
		}
		List<Object> list = baseDao.selectPageList(pageContainer);
		pageContainer.setData(list);
		return pageContainer;
    }
    
    public <E> List<E> selectAll(Class<?> clazz) {
    	Object param = ObjectUtil.newInstance(clazz);
    	return this.selectList(param);
    }
	
    public <E> E select(Object param) {
    	List<E> list = this.selectList(param);
    	if(list == null || list.size() == 0) {
    		return null;
    	}
    	return list.get(0);
    }
    
    public <E> E selectByField(Class<?> clazz, String field, Object value) {
    	Object param = ObjectUtil.newInstance(clazz);
    	ObjectUtil.setFieldValue(param, field, value);
    	return this.select(param);
    }
    
    private void checkPrimaryId(Object param) {
    	if(param instanceof BaseEntity) {
    		BaseEntity entity = (BaseEntity) param;
    		if(entity.getId() == null) {
    			entity.setId(idWorker.nextId());
    		}
    	}
    }
    
}
