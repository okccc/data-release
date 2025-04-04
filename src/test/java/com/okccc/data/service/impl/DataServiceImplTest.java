package com.okccc.data.service.impl;

import com.alibaba.fastjson2.JSON;
import com.okccc.data.service.DataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: okccc
 * @Date: 2024/3/19 18:37:20
 * @Desc: 数据查询接口测试类
 */
@SpringBootTest
class DataServiceImplTest {

    @Autowired
    private DataService dataService;

    @Test
    void queryOrderStatsByDate() {
        // 查mysql
        System.out.println(JSON.toJSONString(dataService.queryOrderStats("2023-03-10")));
    }

    @Test
    void queryAmountStats() {
        // 查clickhouse
        System.out.println(JSON.toJSONString(dataService.queryAmountStats("2023-03-10")));
    }

    @Test
    void queryCountryStats() {
        // 查hive/presto
        System.out.println(JSON.toJSONString(dataService.queryCountryStats(20240329L)));
    }

}