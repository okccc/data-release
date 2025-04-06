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

    /**
     * 2.从clickhouse查询各手机当日的销售额
     *
     * 模拟数据：
     * CREATE table ods.di(create_time DateTime, brand String, amount Decimal(10,2))
     * engine = ReplacingMergeTree(create_time) partition by toYYYYMMDD(create_time) order by (create_time);
     * insert into ods.di values('2023-03-10 12:43:08', 'iphone', 5999);
     * insert into ods.di values('2023-03-10 12:43:09', '华为', 4999);
     * insert into ods.di values('2023-03-10 12:43:10', '小米', 3999);
     * insert into ods.di values('2023-03-10 15:26:11', 'oppo', 2999);
     * insert into ods.di values('2023-03-10 15:26:12', 'vivo', 1999);
     * insert into ods.di values('2023-03-10 19:11:23', 'iphone', 6999);
     * insert into ods.di values('2023-03-10 19:39:16', '华为', 5999);
     * insert into ods.di values('2023-03-10 20:52:08', '小米', 4999);
     * insert into ods.di values('2023-03-10 21:03:13', 'oppo', 3999);
     *
     * 响应数据：
     * {"code":200,"message":"success","data":{"series":[{"name":"手机品牌","data":[8998,6998,12998,10998,4998]}],"categories":["小米","oppo","iphone","华为","vivo"]},"timestamp":1711452885144}
     */
    @Override
    public JSONObject queryAmountStats(String dt) {
        // 查询clickhouse
        List<AmountStats> amountStats = dataMapper.querySaleAmountStats(dt);
        System.out.println(amountStats);
        // [AmountStats(brand=小米, amount=8998.0), AmountStats(brand=iphone, amount=12998.0), AmountStats(brand=华为, amount=10998.0)]

        // []格式使用List封装：维度(手机品牌)、度量(销售额)
        List<String> categories = new ArrayList<>();
        List<Double> amount = new ArrayList<>();

        // 遍历结果集
        for (AmountStats amountStat : amountStats) {
            categories.add(amountStat.getBrand());
            amount.add(amountStat.getAmount());
        }

        // {}格式使用Bean封装
        SeriesData<Double> seriesData = new SeriesData<>("手机品牌", amount);

        // 封装data部分
        JSONObject data = new JSONObject();
        data.put("series", List.of(seriesData));
        data.put("categories", categories);
        return data;
    }

    /**
     * 3.从hive/presto查询某一天各国家的访问量
     *
     * 返回数据：
     * {"code":200,"message":"success","data":{"series":[{"name":"国家","data":[16334970,99221,25681,21788,18358]}],"categories":["中国","China mainland","日本","加拿大","美国"]},"timestamp":1711693622591}
     */
    public JSONObject queryCountryStats(Long dt) {
        // 查询presto
        List<CountryStats> countryStats = dataMapper.queryCountryStats(dt);
        System.out.println(countryStats);
        // [CountryStats(country=中国, count=99), CountryStats(country=日本, count=4), CountryStats(country=韩国, count=2)]

        // []格式使用List封装：维度(国家)、度量(访问量)
        List<String> categories = new ArrayList<>();
        List<Integer> count = new ArrayList<>();

        // 遍历结果集
        for (CountryStats countryStat : countryStats) {
            categories.add(countryStat.getCountry());
            count.add(countryStat.getCount());
        }

        // {}格式使用Bean封装
        SeriesData<Integer> seriesData = new SeriesData<>("国家", count);

        // 封装data部分
        JSONObject data = new JSONObject();
        data.put("series", List.of(seriesData));
        data.put("categories", categories);
        return data;
    }

    /**
     * 4.从hbase查询各省份的订单数和销售额
     *
     * 模拟数据：
     * create table if not exists ods.di(sale_time varchar primary key, brand varchar, province varchar, order_count integer, sale_amount float);
     * upsert into ods.di values('2024-03-29 12:03:01', 'iphone', '上海市', 12, 69999.00);
     * upsert into ods.di values('2024-03-29 15:03:01', '华为', '江苏省', 9, 59999.00);
     * upsert into ods.di values('2024-03-29 19:03:01', '小米', '山东省', 11, 49999.00);
     * upsert into ods.di values('2024-03-29 20:03:01', 'iphone', '上海市', 8, 39999.00);
     * upsert into ods.di values('2024-03-29 21:03:01', '华为', '江苏省', 6, 29999.00);
     * upsert into ods.di values('2024-03-29 22:03:01', '小米', '山东省', 6, 29999.00);
     *
     * 返回数据：
     * {"code":200,"message":"success","data":[{"province":"上海市","order_count":20,"sale_amount":109998},{"province":"山东省","order_count":17,"sale_amount":79998},{"province":"江苏省","order_count":15,"sale_amount":89998}],"timestamp":1711705696199}
     */
    @Override
    public List<JSONObject> queryProvinceStats(String dt) {
        // 查询hbase
        List<ProvinceStats> provinceStats = dataMapper.queryProvinceStats(dt);
        System.out.println(provinceStats);
        // [ProvinceStats(province=上海市, orderCount=20, saleAmount=109998.0), ProvinceStats(province=江苏省, orderCount=15, saleAmount=89998.0)]

        // []格式使用List封装：data
        List<JSONObject> data = new ArrayList<>();

        // 遍历
        for (ProvinceStats provinceStat : provinceStats) {
            // {}格式使用JSONObject封装
            JSONObject obj = new JSONObject();
            obj.put("province", provinceStat.getProvince());
            obj.put("order_count", provinceStat.getOrderCount());
            obj.put("sale_amount", provinceStat.getSaleAmount());
            data.add(obj);
        }

        // 响应数据
        return data;
    }

}
