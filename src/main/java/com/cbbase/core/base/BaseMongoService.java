package com.cbbase.core.base;

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

    public PageContainer selectPage(Class<?> entityClass, PageContainer param, Query query) {
        return selectPage(entityClass, param, query, null);
    }

    public PageContainer selectPage(Class<?> entityClass, PageContainer param, Query query, Sort sort) {
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
        List<?> list = mongoTemplate.find(query, entityClass);
        param.setRowCount((int)rowCount);
        param.setData(list);
        return param;
    }
}
