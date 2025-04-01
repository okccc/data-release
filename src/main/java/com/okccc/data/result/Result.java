package com.okccc.data.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: okccc
 * @Date: 2024/3/24 20:10:47
 * @Desc: 给前端返回统一的数据格式,泛型包含String/Integer/UserInfo/Double/Boolean/POJO等各种类型
 *
 * {
 *     code: 200,
 *     message: "操作成功",
 *     data: {
 *         categories: ["12:43", "15:26"],
 *         series: [
 *             {name:"订单数", data:[60, 66]},
 *             {name:"退单数", data:[17, 16]}
 *         ]
 *     },
 *     timestamp: 1738823519155
 * }
 */
@Schema(description = "统一响应结果实体类")
@Data
public class Result<T> {

    @Schema(description = "业务状态码")
    private Integer code;

    @Schema(description = "响应消息")
    private String message;

    @Schema(description = "业务数据")
    private T data;

    @Schema(description = "时间戳")
    private long timestamp;

    public Result() {
        this.timestamp = System.currentTimeMillis();
    }

    // 构造Result对象
    public static <T> Result<T> build(T data) {
        Result<T> result = new Result<>();
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    // 通过枚举类构造Result对象
    public static <T> Result<T> build(T data, ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(data);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    public static <T> Result<T> ok(T data) {
        // 查询成功
        return build(data, ResultCodeEnum.SUCCESS);
    }

    public static <T> Result<T> ok() {
        // 更新成功
        return ok(null);
    }

    public static <T> Result<T> fail() {
        // 操作失败,返回友好信息
        return build(null, ResultCodeEnum.FAIL);
    }

    public static <T> Result<T> fail(ResultCodeEnum resultCodeEnum) {
        // 操作失败,返回具体原因
        return build(null, resultCodeEnum);
    }

}
