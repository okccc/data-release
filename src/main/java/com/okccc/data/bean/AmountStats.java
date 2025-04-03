package com.okccc.data.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: okccc
 * @Date: 2024/3/26 19:02:18
 * @Desc:
 */
@Data
public class AmountStats {

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "销售额")
    private Double amount;
}
