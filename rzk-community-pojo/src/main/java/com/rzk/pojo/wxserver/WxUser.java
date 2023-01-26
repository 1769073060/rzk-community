package com.rzk.pojo.wxserver;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 微信公众用户表
 *
 * @author rzk 1769073060@qq.com
 * @since 1.0.0 2023-01-20
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("wx_user")
public class WxUser {
	private static final long serialVersionUID = 1L;
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
    /**
     * openId标识
     */
	private String toUserName;
    /**
     * 用户微信号
     */
	private String fromUserName;
    /**
     * 关注时间
     */
	private Date followDate;
    /**
     * 关注次数
     */
	private String followCount;
    /**
     * 取关时间
     */
	private Date unFollowDate;
    /**
     * 取关次数
     */
	private String unFollowCount;
    /**
     * 是否启动黑名单验证
     */
	private String blackFlag;
}