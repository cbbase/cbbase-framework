package com.cbbase.core.base;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import com.cbbase.core.container.PageContainer;


public class BaseMongoService {

    @Autowired
    protected MongoTemplate mongoTemplate;

    public < T > T findById(Long id, Class < T > entityClass) {
        return mongoTemplate.findById(id, entityClass);
    }

    public < T > List < T > find(Query query, Class < T > entityClass) {
        return mongoTemplate.find(query, entityClass);
    }

    public void insert(Object entity) {
        mongoTemplate.insert(entity);
    }

    public void insertAll(Collection < ? > objectsToSave) {
        mongoTemplate.insertAll(objectsToSave);
        ;
    }

    public void update(Object entity) {
        mongoTemplate.save(entity);
    }

    public void delete(Object entity) {
        mongoTemplate.remove(entity);
    }

    public void deleteAll(Query query, Class < ? > entityClass) {
        mongoTemplate.findAllAndRemove(query, entityClass);
    }

    public PageContainer selectPage(Class < ? > entityClass, Query query, PageContainer param) {
        return selectPage(entityClass, query, param, null);
    }

    public PageContainer selectPage(Class < ? > entityClass, Query query, PageContainer param, Sort sort) {
        if (query == null) {
            query = new Query();
        }
        // 查询count
        long rowCount = mongoTemplate.count(query, entityClass);

        if (sort != null) {
            query.with(sort);
        }
        // mongo分页从0开始
        PageRequest pageRequest = PageRequest.of(param.getCurrentPage() - 1, param.getPageSize());
        query.with(pageRequest);
        List < ? > list = mongoTemplate.find(query, entityClass);
        param.setRowCount((int)rowCount);
        param.setData(list);
        return param;
    }
}
