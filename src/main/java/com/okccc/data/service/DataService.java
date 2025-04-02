package com.okccc.data.service;

import com.alibaba.fastjson2.JSONObject;

/**
 * @Author: okccc
 * @Date: 2024/3/20 16:54:46
 * @Desc:
 */
public interface DataService {

    // 1.从mysql查询某一天分钟级别的下单数和退单数
    JSONObject queryOrderStats(String dt);
}
