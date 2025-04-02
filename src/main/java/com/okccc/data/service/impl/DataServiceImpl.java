package com.okccc.data.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.okccc.data.bean.*;
import com.okccc.data.mapper.DataMapper;
import com.okccc.data.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: okccc
 * @Date: 2024/3/20 18:15:33
 * @Desc: 返回数据格式看前端需求
 */
@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DataMapper dataMapper;

    /**
     * 1.从mysql查询某一天分钟级别的下单数和退单数
     *
     * 模拟数据：
     * CREATE TABLE di (dt datetime, orders int(11), refunds int(11));
     * insert into di values('2023-03-10 12:43:08', 10, 3);
     * insert into di values('2023-03-10 12:43:09', 20, 5);
     * insert into di values('2023-03-10 12:43:10', 30, 9);
     * insert into di values('2023-03-10 15:26:11', 11, 3);
     * insert into di values('2023-03-10 15:26:12', 22, 5);
     * insert into di values('2023-03-10 15:26:13', 33, 8);
     *
     * 响应数据：
     * {"code":200,"message":"success","data":{"series":[{"name":"订单数","data":[20,76]},{"name":"退单数","data":[9,52]}],"categories":["12:43","15:26"]},"timestamp":1711444377079}
     */
    @Override
    public JSONObject queryOrderStats(String dt) {
        // 查询mysql,一行数据对应一个Bean对象
        List<OrderStats> orderStats = dataMapper.queryOrderStats(dt);
        System.out.println(orderStats);
        // [OrderStats(time=12:43, orderCount=20, refundCount=9), OrderStats(time=15:26, orderCount=76, refundCount=52)]

        // []格式使用List封装：维度(时间)、度量(订单数 & 退单数)
        List<String> categories = new ArrayList<>();
        List<Integer> orderCnt = new ArrayList<>();
        List<Integer> refundCnt = new ArrayList<>();

        // 遍历结果集
        for (OrderStats orderStat : orderStats) {
            categories.add(orderStat.getTime());
            orderCnt.add(orderStat.getOrderCount());
            refundCnt.add(orderStat.getRefundCount());
        }

        // {}格式使用Map/JSONObject封装,当字段数较多时会很臃肿,此时用Bean封装更方便
//        JSONObject jsonObject01 = new JSONObject();
//        jsonObject01.put("name", "订单数");
//        jsonObject01.put("data", orderCnt);
//        series.add(jsonObject01);
//        JSONObject jsonObject02 = new JSONObject();
//        jsonObject02.put("name", "退单数");
//        jsonObject02.put("data", refundCnt);
//        series.add(jsonObject02);

        SeriesData<Integer> seriesData01 = new SeriesData<>("订单数", orderCnt);
        SeriesData<Integer> seriesData02 = new SeriesData<>("退单数", refundCnt);

        // 封装data部分
        JSONObject data = new JSONObject();
        data.put("series", Arrays.asList(seriesData01, seriesData02));
        data.put("categories", categories);
        return data;
    }

}
