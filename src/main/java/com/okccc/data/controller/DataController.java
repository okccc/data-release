package com.okccc.data.controller;

import com.alibaba.fastjson2.JSONObject;
import com.okccc.data.result.Result;
import com.okccc.data.service.DataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: okccc
 * @Date: 2024/3/20 16:48:07
 * @Desc: 数据接口开发步骤
 *
 * 1.分析请求url,确认要响应的数据格式
 * 2.判断涉及到哪些数据库,编写相应sql或NoSqlApi去查询数据
 * 3.MVC开发流程,DataMapper - DataService - DataServiceImpl - DataServiceImplTest - DataController
 */
@Tag(name = "数据查询接口")
@RestController
public class DataController {

    @Autowired
    private DataService dataService;

    // http://localhost:8081/queryOrderStats/2023-03-10
    @Operation(summary = "查询mysql案例")
    @GetMapping(value = "/queryOrderStats/{dt}")
    public Result<JSONObject> queryOrderStats(@PathVariable("dt") String dt) {
        JSONObject data = dataService.queryOrderStats(dt);
        return Result.ok(data);
    }

    // http://localhost:8081/queryAmountStats/2023-03-10
    @Operation(summary = "查询clickhouse案例")
    @GetMapping(value = "/queryAmountStats/{dt}")
    public Result<JSONObject> queryAmountStats(@PathVariable("dt") String dt) {
        JSONObject data = dataService.queryAmountStats(dt);
        return Result.ok(data);
    }

    // http://localhost:8081/queryCountryStats/20240329
    @Operation(summary = "查询hive/presto案例")
    @GetMapping(value = "/queryCountryStats/{dt}")
    public Result<JSONObject> queryCountryStats(@PathVariable("dt") Long dt) {
        JSONObject data = dataService.queryCountryStats(dt);
        return Result.ok(data);
    }

    // http://localhost:8081/queryProvinceStats/2024-03-29
    @Operation(summary = "查询hbase案例")
    @GetMapping(value = "/queryProvinceStats/{dt}")
    public Result<List<JSONObject>> queryProvinceStats(@PathVariable("dt") String dt) {
        List<JSONObject> data = dataService.queryProvinceStats(dt);
        return Result.ok(data);
    }

    // http://localhost:8081/queryVisitStatsTop3
    @Operation(summary = "查询redis案例")
    @GetMapping(value = "/queryVisitStatsTop3")
    public Result<JSONObject> queryVisitStatsTop3() {
        JSONObject data = dataService.queryVisitStatsTop3();
        return Result.ok(data);
    }

}
