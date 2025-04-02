package com.okccc.data.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: okccc
 * @Date: 2024/3/20 18:19:29
 * @Desc:
 */
@Data
public class OrderStats {

    @Schema(description = "时间(分钟)")
    private String time;

    @Schema(description = "订单数")
    private Integer orderCount;

    @Schema(description = "退单数")
    private Integer refundCount;
}
