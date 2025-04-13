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
