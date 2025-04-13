package com.okccc.data.dao;

import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.springframework.data.elasticsearch.core.SearchHits;

/**
 * @Author: okccc
 * @Date: 2024/3/26 19:40:43
 * @Desc: ES是Nosql数据库,无法使用Mybatis,所以使用传统的Dao实现
 */
public interface ESDao {

    <T> SearchHits<T> queryAggFromES(AbstractAggregationBuilder aggBuilder, Class<T> bean);
}
