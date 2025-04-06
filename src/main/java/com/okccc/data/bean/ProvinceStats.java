package com.okccc.data.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: okccc
 * @Date: 2024/3/29 16:54:27
 * @Desc:
 */
@Data
public class ProvinceStats {

    @Schema(description = "省份")
    private String province;

    @Schema(description = "订单数")
    private Integer orderCount;

    @Schema(description = "销售额")
    private Double saleAmount;
}
