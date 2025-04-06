package com.okccc.data.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.okccc.data.bean.CountryStats;
import com.okccc.data.bean.AmountStats;
import com.okccc.data.bean.OrderStats;
import com.okccc.data.bean.ProvinceStats;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: okccc
 * @Date: 2024/3/20 18:16:07
 * @Desc: Mapper接口的查询结果一行数据对应一个Bean对象
 */
@Mapper
public interface DataMapper {

    // 从mysql查询某一天分钟级别的下单数和退单数
    @DS("mysql")
    @Select("SELECT" +
            "    DATE_FORMAT(dt,'%H:%i') time," +
            "    SUM(orders) order_count," +
            "    SUM(refunds) refund_count " +
            "FROM eshop.di " +
            "WHERE DATE(dt) = #{dt} " +
            "GROUP BY DATE_FORMAT(dt,'%H:%i') " +
            "ORDER BY time")
    List<OrderStats> queryOrderStats(String dt);

    // 从ck查询某一天各手机当日销售额
    @DS("ck")
    @Select("SELECT" +
            "    brand," +
            "    SUM(amount) amount " +
            "FROM ods.di " +
            "WHERE toDate(create_time) = #{dt} " +
            "GROUP BY brand")
    List<AmountStats> querySaleAmountStats(String dt);

    // 从hive/presto查询某一天各国家的访问量
//    @DS("hive")
    @DS("presto")
    @Select("SELECT" +
            "    country," +
            "    COUNT(1) count " +
            "FROM ods.ods_access_log_realtime " +
            "WHERE dt = #{dt} " +
            "GROUP BY country " +
            "ORDER BY count DESC " +
            "LIMIT 5")
    List<CountryStats> queryCountryStats(Long dt);

    // 从hbase查询某一天各省份的订单数和销售额
    @DS("hbase")
    @Select("SELECT " +
            "    PROVINCE," +
            "    SUM(ORDER_COUNT) order_count," +
            "    SUM(SALE_AMOUNT) sale_amount " +
            "FROM ODS.DI " +
            "WHERE substr(SALE_TIME,1,10) = #{dt} " +
            "GROUP BY PROVINCE")
    List<ProvinceStats> queryProvinceStats(String dt);
}
