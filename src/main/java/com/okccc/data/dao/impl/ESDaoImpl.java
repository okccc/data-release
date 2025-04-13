package com.okccc.data.dao.impl;

import com.okccc.data.dao.ESDao;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

/**
 * @Author: okccc
 * @Date: 2024/3/26 19:46:49
 * @Desc: es查询接口实现类
 */
@Repository  // @Repository适用于所有Dao,@Mapper仅限于JDBC中使用Mybatis的Dao
public class ESDaoImpl implements ESDao {

    @Autowired
    private ElasticsearchRestTemplate esRestTemplate;

    @Override
    public <T> SearchHits<T> queryAggFromES(AbstractAggregationBuilder aggBuilder, Class<T> bean) {
        // 根据聚合条件构造查询对象
        NativeSearchQuery query = new NativeSearchQueryBuilder().addAggregation(aggBuilder).build();

        // 执行查询,将命中结果封装成Bean
        return esRestTemplate.search(query, bean);
    }
}
