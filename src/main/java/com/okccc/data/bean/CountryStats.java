package com.okccc.data.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: okccc
 * @Date: 2024/3/29 11:24:44
 * @Desc:
 */
@Data
public class CountryStats {

    @Schema(description = "国家")
    private String country;

    @Schema(description = "访问量")
    private Integer count;
}
