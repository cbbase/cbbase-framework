package com.cbbase.core.connector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;

import com.alibaba.fastjson.JSON;
import com.cbbase.core.tools.ObjectUtil;

/**
 * 
 * @author changbo
 *
 */
public class EsGroupHelper {
	
	private ElasticsearchTemplate elasticsearchTemplate;
	private BoolQueryBuilder boolQueryBuilder;
	private List<TermsAggregationBuilder> termsList = new ArrayList<>();
	private List<AggregationBuilder> aggList = new ArrayList<>();
	private String[] indices;
	private String type;
	private int from = 0;
	private int size = 10;
	private List<Order> orders = new ArrayList<>();
	
	
	private EsGroupHelper(ElasticsearchTemplate elasticsearchTemplate) {
		this.elasticsearchTemplate = elasticsearchTemplate;
	}
	
	public static EsGroupHelper getEsGroupHelper(ElasticsearchTemplate elasticsearchTemplate) {
		return new EsGroupHelper(elasticsearchTemplate);
	}
	
	private BoolQueryBuilder getQueryBuilder() {
		if(boolQueryBuilder == null) {
			boolQueryBuilder = QueryBuilders.boolQuery();
		}
		return boolQueryBuilder;
	}
	
	public EsGroupHelper setQueryBuilder(BoolQueryBuilder boolQueryBuilder) {
		this.boolQueryBuilder = boolQueryBuilder;
		return this;
	}

	public EsGroupHelper index(String... indices) {
		this.indices = indices;
		return this;
	}

	public EsGroupHelper type(String type) {
		this.type = type;
		return this;
	}

	public EsGroupHelper from(int size) {
		this.size = size;
		return this;
	}
	
	public EsGroupHelper size(int size) {
		this.size = size;
		return this;
	}
	
	public EsGroupHelper andEqual(String field, Object value) {
		getQueryBuilder().must(QueryBuilders.termQuery(field, value));
		return this;
	}
	
	public EsGroupHelper andLike(String field, Object value) {
		getQueryBuilder().must(QueryBuilders.matchPhraseQuery(field, value));
		return this;
	}
	
	public EsGroupHelper andNotEqual(String field, Object value) {
		getQueryBuilder().mustNot(QueryBuilders.termQuery(field, value));
		return this;
	}
	
	public EsGroupHelper andNotLike(String field, Object value) {
		getQueryBuilder().mustNot(QueryBuilders.matchPhraseQuery(field, value));
		return this;
	}
	
	public EsGroupHelper orEqual(String field, Object value) {
		getQueryBuilder().should(QueryBuilders.termQuery(field, value));
		return this;
	}
	
	public EsGroupHelper orLike(String field, Object value) {
		getQueryBuilder().should(QueryBuilders.commonTermsQuery(field, value));
		return this;
	}
	
	public EsGroupHelper range(String field, Object from, Object to) {
		getQueryBuilder().must(QueryBuilders.rangeQuery(field).gte(from).lte(to));
		return this;
	}
	
	public EsGroupHelper in(String field, List<?> values) {
		getQueryBuilder().must(QueryBuilders.termsQuery(field, values));
		return this;
	}
	
	public EsGroupHelper notIn(String field, List<?> values) {
		getQueryBuilder().mustNot(QueryBuilders.termsQuery(field, values));
		return this;
	}
	
    public EsGroupHelper isNull(String field) {
        getQueryBuilder().mustNot(QueryBuilders.existsQuery(field));
        return this;
    }
    
    public EsGroupHelper isNotNull(String field) {
        getQueryBuilder().must(QueryBuilders.existsQuery(field));
        return this;
    }

	
	public EsGroupHelper groupBy(String alias, String field) {
		termsList.add(AggregationBuilders.terms(alias).field(field));
		return this;
	}
	
	public EsGroupHelper sum(String alias, String field) {
		aggList.add(AggregationBuilders.sum(alias).field(field));
		return this;
	}
	
	public EsGroupHelper avg(String alias, String field) {
		aggList.add(AggregationBuilders.avg(alias).field(field));
		return this;
	}
	
	public EsGroupHelper count(String alias, String field) {
		aggList.add(AggregationBuilders.count(alias).field(field));
		return this;
	}
	
	public EsGroupHelper max(String alias, String field) {
		aggList.add(AggregationBuilders.max(alias).field(field));
		return this;
	}
	
	public EsGroupHelper min(String alias, String field) {
		aggList.add(AggregationBuilders.min(alias).field(field));
		return this;
	}
	
	public EsGroupHelper orderBy(String field, boolean asc) {
		if(asc) {
			orders.add(new Order(Direction.ASC, field));
		}else {
			orders.add(new Order(Direction.DESC, field));
		}
		return this;
	}

	public <T> List<T> query(Class<T> clazz) {
		List<Map<String, Object>> list = query();
		String json = JSON.toJSONString(list);
		return JSON.parseArray(json, clazz);
	}
	
	public List<Map<String, Object>> query() {
		if(termsList.size() == 0) {
			return null;
		}
		TermsAggregationBuilder last = null;
		for(TermsAggregationBuilder terms : termsList) {
			if(last == null) {
				last = terms;
			}else {
				last.subAggregation(terms);
				last = terms;
			}
		}
		for(AggregationBuilder agg : aggList) {
			last.subAggregation(agg);
		}
		
        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        searchBuilder.query(boolQueryBuilder);
        searchBuilder.from(from).size(size);//指定返回数量，默认10条，ES默认最大不能超过10000条，如果有需要超过则需要更新ES配置
        searchBuilder.aggregation(termsList.get(0));
        
        SearchRequest request = new SearchRequest(indices);
        request.types(type);
        request.source(searchBuilder);
        
        SearchResponse response = elasticsearchTemplate.getClient().search(request).actionGet();
		Terms terms = response.getAggregations().get(termsList.get(0).getName());
        return termsToList(terms);
	}
	
	public List<Map<String, Object>> termsToList(Terms terms) {
		List<Map<String, Object>> list = new ArrayList<>();
        for(Terms.Bucket entry : terms.getBuckets()){
        	List<Aggregation> subAggList = entry.getAggregations().asList();
        	Map<String, Object> map = new HashMap<>();
    		map.put(terms.getName(), entry.getKey());
        	for(Aggregation subAgg : subAggList) {
        		if(subAgg instanceof Terms) {
        			Terms sub = (Terms) subAgg;
        			List<Map<String, Object>> subList = termsToList(sub);
        			if(subList.size() == 1) {
        				map.putAll(subList.get(0));
        			}else if(subList.size() > 1){
        				map.put(sub.getName(), subList);
        			}
        		}else {
            		Object val = ObjectUtil.getFieldValue(entry.getAggregations().get(subAgg.getName()), "value");
    	        	map.put(subAgg.getName(), val);
        		}
        	}
        	list.add(map);
        }
    	return list;
	}

}
