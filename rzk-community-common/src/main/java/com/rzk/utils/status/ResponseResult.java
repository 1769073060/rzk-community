package com.rzk.utils.status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @PackageName : com.rzk.utils.status
 * @FileName : ResponseResult
 * @Description : 返回数据
 * @Author : rzk
 * @CreateTime : 2022年 09月 15日 下午10:52
 * @Version : 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult {
    /**
     * 返回代码
     */
    private int code;
    /**
     * 返回消息
     */
    private String msg;
    /**
     * 返回数据
     */
    private Object data;

    /**
     * 成功返回结果
     *
     * @param msg
     * @return
     */
    public static ResponseResult success(String msg) {
        return new ResponseResult(200, msg, null);
    }




    /**
     * 成功返回结果
     *
     * @param msg
     * @param data
     * @return
     */
    public static ResponseResult success(int code, String msg, Object data) {
        return new ResponseResult(code, msg, data);
    }


    /**
     * 失败返回结果
     *
     * @param msg
     * @return
     */
    public static ResponseResult error(String msg) {
        return new ResponseResult(500, msg, null);
    }

    /**
     * 失败返回结果
     *
     * @param msg
     * @return
     */
    public static ResponseResult error(String msg, Object data) {
        return new ResponseResult(500, msg, data);
    }

}
