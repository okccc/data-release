package com.okccc.data.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @Author: okccc
 * @Date: 2024/3/24 20:04:23
 * @Desc:
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200, "操作成功"),
    FAIL(9999, "操作失败");

    @Schema(description = "业务状态码")
    private final Integer code;

    @Schema(description = "描述信息")
    private final String message;

    ResultCodeEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }

}
