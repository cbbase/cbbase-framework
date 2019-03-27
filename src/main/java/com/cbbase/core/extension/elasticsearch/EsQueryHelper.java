package com.cbbase.core.extension.elasticsearch;

import java.util.ArrayList;
import java.util.List;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;

/**
 * 
 * @author changbo
 *
 */
public class EsQueryHelper {
	
	private ElasticsearchTemplate elasticsearchTemplate;
	private BoolQueryBuilder boolQueryBuilder;
	private String[] indices;
	private String type;
	private int from = 0;
	private int size = 10;
	private List<Order> orders = new ArrayList<>();
	
	
	private EsQueryHelper(ElasticsearchTemplate elasticsearchTemplate) {
		this.elasticsearchTemplate = elasticsearchTemplate;
	}
	
	public static EsQueryHelper getEsGroupHelper(ElasticsearchTemplate elasticsearchTemplate) {
		return new EsQueryHelper(elasticsearchTemplate);
	}
	
	private BoolQueryBuilder getQueryBuilder() {
		if(boolQueryBuilder == null) {
			boolQueryBuilder = QueryBuilders.boolQuery();
		}
		return boolQueryBuilder;
	}
	
	public EsQueryHelper setQueryBuilder(BoolQueryBuilder boolQueryBuilder) {
		this.boolQueryBuilder = boolQueryBuilder;
		return this;
	}

	public EsQueryHelper index(String... indices) {
		this.indices = indices;
		return this;
	}

	public EsQueryHelper type(String type) {
		this.type = type;
		return this;
	}

	public EsQueryHelper from(int size) {
		this.size = size;
		return this;
	}
	
	public EsQueryHelper size(int size) {
		this.size = size;
		return this;
	}
	
	public EsQueryHelper equal(String field, Object value) {
		getQueryBuilder().must(QueryBuilders.termQuery(field, value));
		return this;
	}
	
	public EsQueryHelper like(String field, Object value) {
		getQueryBuilder().must(QueryBuilders.matchPhraseQuery(field, value));
		return this;
	}
	
	public EsQueryHelper notEqual(String field, Object value) {
		getQueryBuilder().mustNot(QueryBuilders.termQuery(field, value));
		return this;
	}
	
	public EsQueryHelper notLike(String field, Object value) {
		getQueryBuilder().mustNot(QueryBuilders.matchPhraseQuery(field, value));
		return this;
	}
	
	public EsQueryHelper orEqual(String field, Object value) {
		getQueryBuilder().should(QueryBuilders.termQuery(field, value));
		return this;
	}
	
	public EsQueryHelper orLike(String field, Object value) {
		getQueryBuilder().should(QueryBuilders.commonTermsQuery(field, value));
		return this;
	}
	
	public EsQueryHelper range(String field, Object from, Object to) {
		getQueryBuilder().must(QueryBuilders.rangeQuery(field).gte(from).lte(to));
		return this;
	}
	
	public EsQueryHelper in(String field, List<?> values) {
		getQueryBuilder().must(QueryBuilders.termsQuery(field, values));
		return this;
	}
	
	public EsQueryHelper notIn(String field, List<?> values) {
		getQueryBuilder().mustNot(QueryBuilders.termsQuery(field, values));
		return this;
	}
	
    public EsQueryHelper isNull(String field) {
        getQueryBuilder().mustNot(QueryBuilders.existsQuery(field));
        return this;
    }
    
    public EsQueryHelper isNotNull(String field) {
        getQueryBuilder().must(QueryBuilders.existsQuery(field));
        return this;
    }
	
	public EsQueryHelper orderBy(String field, boolean asc) {
		if(asc) {
			orders.add(new Order(Direction.ASC, field));
		}else {
			orders.add(new Order(Direction.DESC, field));
		}
		return this;
	}
	
	public <T> List<T> query(Class<T> clazz) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(boolQueryBuilder);
        nativeSearchQuery.addIndices(indices);
        nativeSearchQuery.addTypes(type);
        nativeSearchQuery.setPageable(PageRequest.of(from, size, Sort.by(orders)));
        return elasticsearchTemplate.queryForList(nativeSearchQuery, clazz);
	}

}
