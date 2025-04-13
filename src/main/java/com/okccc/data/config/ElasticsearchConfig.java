package com.okccc.data.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

/**
 * @Author: okccc
 * @Date: 2024/3/27 18:22:26
 * @Desc: spring-boot-autoconfigure不包含的配置类要手动添加,不然@Autowired注入对象时会报错No qualifying bean of type
 */
@Configuration
public class ElasticsearchConfig {

    @Bean
    public ElasticsearchRestTemplate elasticsearchRestTemplate() {
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        return new ElasticsearchRestTemplate(restHighLevelClient);
    }
}
