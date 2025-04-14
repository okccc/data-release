package com.okccc.data.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @Author: okccc
 * @Date: 2024/3/27 15:27:24
 * @Desc:
 *
 * [TOO_MANY_REQUESTS/12/disk usage exceeded flood-stage watermark, index has read-only-allow-delete block];
 * 原因：一次插入数据过多或短时间内请求次数过多,导致es节点内存超出限制,es会主动给索引上锁使其变成只读状态,需要关闭索引的只读状态
 * 解决：PUT http://localhost:9200/_all/_settings {"index.blocks.read_only_allow_delete": null}
 */
@Data
@Document(indexName = "di")  // 将Bean和ES的Index进行映射
public class Customer {

    @Id  // 主键
    @Field(name = "id", type = FieldType.Long)
    private Long id;

    @Field(name = "name", type = FieldType.Keyword)
    private String name;

    @Field(name = "sex", type = FieldType.Keyword)
    private String sex;

    @Field(name = "profession", type = FieldType.Keyword)
    private String profession;

    @Field(name = "source", type = FieldType.Keyword)
    private String source;
}
