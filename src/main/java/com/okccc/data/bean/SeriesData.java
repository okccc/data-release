package com.okccc.data.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @Author: okccc
 * @Date: 2024/3/20 18:46:00
 * @Desc: {name:"订单数", data:[60, 66]}
 */
@Data
@AllArgsConstructor
public class SeriesData<T> {

    @Schema(description = "统计数据名称")
    private String name;

    @Schema(description = "统计数据集合")
    private List<T> data;
}
