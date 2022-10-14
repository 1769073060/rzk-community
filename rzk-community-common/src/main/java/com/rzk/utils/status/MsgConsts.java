package com.rzk.utils.status;

public class MsgConsts {

    /**
     * 200：表示成功
     * 301 禁用权限
     * 400 数据出现问题
     * 401  未登录
     * 403 不允许发布,拉黑
     * 500：表示错误，错误信息在msg字段中
     * 501：bean验证错误，不管多少个错误都以map形式返回
     * 502：拦截器拦截到用户token出错
     * 555：异常抛出信息
     * 1000 非法入侵
     */

    public static final int SUCCESS_CODE = 200;
    public static final int NEW_USER = 300;
    public static final int DISALE_PERMISSIONS= 301;
    public static final int DATA_ERROR = 400;
    public static final int FAIL_CODE = 500;
    public static final int Error_Map_CODE = 501;
    public static final int Error_Token_CODE = 502;
    public static final int Error_Exception_CODE = 555;

    public static final boolean SUCCESS_RESULT_TRUE = true;
    public static final boolean SUCCESS_RESULT_FALSE = false;

    /**
     * 类型
     * 1个人认证
     * 2商家认证
     */
    public static final int PERSONAL_AUTHENTICATION = 1;
    public static final int MERCHANT_CERTIFICATION = 2;
    /**
     * 设置状态值
     * 0
     * 1
     * 2
     * ...
     * 7
     */
    public static final int ZERO_STATUS = 0;
    public static final int FIRST_STATUS = 1;
    public static final int SECOND_STATUS = 2;
    public static final int THIRD_STATUS = 3;
    public static final int FOURTH_STATUS = 4;
    public static final int FIFTH_STATUS = 5;
    public static final int SIXTH_STATUS = 6;
    public static final int SEVENTH_STATUS = 7;
    /*类型 商家id，用户id*/
    public static final Long BusinessId_Status = 0L;
    public static final Long UserId_Status = 0L;
    /**
     * 经纬度
     * RADIUS 半径千米
     * Double = 2 倍数
     * DEGREES = 180 度数
     */
    public static final int RADIUS = 6371;
    public static final int DOUBLE = 2;
    public static final int DEGREES = 180;

    /**
     * UNPAID 未支付
     * RAID   已支付
     */
    public static final int UNPAID = 0;
    public static final int RAID = 1;
    /**
     * NO_CLICK_RAID 未点击支付
     * CLICK_RAID    已点击支付
     */
    public static final int NO_CLICK_RAID = 0;
    public static final int CLICK_RAID = 1;

    public static final String SUCCESS_MSG = "操作成功";
    public static final String FAIL_MSG = "操作失败";
    public static final String SYSTEM_ERROR_MSG = "系统错误";
    public static final String PARAM_ERROR_MSG = "参数错误";
    public static final String MISS_PARAM_MSG = "缺少参数";
    public static final String AMOUNT_ERROR_MSG = "金额有误";
    public static final String PAY_ERROR_MSG = "支付有误";
    public static final String ALREADY_EXISTS= "不能重复添加";
    public static final String SMS_VALIDATE_FAILE = "验证码不正确";
    public static final String USER_IS_EXIST = "用户已存在";
    public static final String USER_IS_NOT_EXIST = "用户不存在";
    public static final String ACCOUNT_OR_PASSWORD_ERROR = "账号或密码错误";
    public static final String PASSWORD_ERROR = "密码错误";
    public static final String Enter_User_Name = "请输入用户名";
    public static final String SEND_SUCCESS = "发送成功";

    public static final String DEL_FAIL_MSG = "删除失败";
    public static final String ADD_FAIL_MSG = "添加失败";
    public static final String UPDATE_FAIL_MSG = "修改失败";
    public static final String DEL_SUCCESS_MSG = "删除成功";
    public static final String ADD_SUCCESS_MSG = "添加成功";
    public static final String UPDATE_SUCCESS_MSG = "修改成功";
    public static final String IMPORT_SUCCESS_MSG = "导入成功";
    public static final String IMPORT_FAIL_MSG = "导入失败";


    //消息投递中
    public static final Integer DELIVERING = 0;
    //消息投递成功
    public static final Integer SUCCESS = 1;
    //消息投递失败
    public static final Integer FAILURE = 2;
    //最大重试次数
    public static final Integer MAX_TRY_COUNT = 3;
    //消息超时时间
    public static final Integer MSG_TIMEOUT = 1;
    //队列
    public static final String MAXL_QUEUE_NAME = "mail.queue";
    //交换机
    public static final String MAIL_EXCHANGE_NAME = "mail.exchange";
    //路由键
    public static final String MAIL_ROUTING_KEY_NAME = "mail.routing.key";


}
