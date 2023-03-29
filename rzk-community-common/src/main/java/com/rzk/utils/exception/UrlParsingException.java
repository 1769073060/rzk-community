package com.rzk.utils.exception;

/**
 * @author: 小熊
 * @date: 2021/6/9 13:47
 * @description:phone 17521111022
 * 视频地址解析时异常
 */
public class UrlParsingException extends RuntimeException {

    public UrlParsingException(String message) {
        super(message);
    }

    public UrlParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public Integer getStatus() {
        return 1000;
    }
}
