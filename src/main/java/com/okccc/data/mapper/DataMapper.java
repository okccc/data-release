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
 *
 * hbase常见错误
 * Caused by: java.sql.SQLException: ERROR 726 (43M10): Inconsistent namespace mapping properties. Cannot initiate
 * connection as SYSTEM:CATALOG is found but client does not have phoenix.schema.isNamespaceMappingEnabled enabled
 * 原因：phoenix不能直接操作schema,要在hbase-site.xml开启namespace映射权限
 *
 * Caused by: java.lang.ClassNotFoundException: com.google.protobuf.LiteralByteString
 * 将jar包解压后看看是否包含当前报错类,如果包含那就是依赖冲突了,借助Maven Helper插件分析冲突jar包
 * mvn dependency:tree -Dverbose -Dincludes=com.google.protobuf
 * 分析依赖树发现mysql包的com.google.protobuf:protobuf-java:jar:3.11.4:compile导致hbase用不了,在mysql依赖添加exclusion去除
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
