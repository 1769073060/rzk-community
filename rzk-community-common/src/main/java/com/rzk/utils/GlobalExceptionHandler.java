package com.rzk.utils;

import com.rzk.utils.status.BaseResponse;
import com.rzk.utils.status.CodeEnum;
import com.rzk.utils.status.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义的业务异常
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = com.rzk.utils.MyException.class)
    @ResponseBody
    public BaseResponse bizExceptionHandler(HttpServletRequest req, com.rzk.utils.MyException e) {
        logger.error("发生业务异常！原因是：{}", e.getErrorMsg());
        return ResponseData.error(e.getErrorCode(), e.getErrorMsg());
    }

    /**
     * 处理空指针的异常
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public ResponseData exceptionHandler(HttpServletRequest req, NullPointerException e) {
        logger.error("发生空指针异常！原因是:", e);
        return ResponseData.error(CodeEnum.BODY_NOT_MATCH);
    }


    /**
     * 处理其他异常
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseData exceptionHandler(HttpServletRequest req, Exception e) {
        logger.error("未知异常！原因是:", e);
        log.info("e.getMessage()"+e.getMessage());
        if ("无token,请重新登录".equals(e.getMessage())){
            logger.info("进入"+e.getMessage());
            return ResponseData.error(CodeEnum.NOT_LOGIN.getCode(), CodeEnum.NOT_LOGIN.getMsg());

        }
        if ("401".equals(e.getMessage())){
            logger.info("进入"+e.getMessage());
            return ResponseData.error(CodeEnum.NOT_LOGIN.getCode(), CodeEnum.NOT_LOGIN.getMsg());

        }
        if ("1001".equals(e.getMessage())){
            logger.info("进入"+e.getMessage());
            return ResponseData.error(CodeEnum.TOKENEPIRED.getCode(), CodeEnum.TOKENEPIRED.getMsg());

        }
        logger.info("进入"+e.getMessage());

        return ResponseData.error(CodeEnum.INTERNAL_SERVER_ERROR.getCode(), CodeEnum.INTERNAL_SERVER_ERROR.getMsg());
    }
}