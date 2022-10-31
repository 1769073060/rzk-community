package com.rzk.pojo;

import com.baomidou.mybatisplus.annotation.TableName;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2022-09-11
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("user")
public class User{
	private static final long serialVersionUID = 1L;

    /**
     * 
     */
	private Integer userId;
    /**
     * 
     */
	private String userOpenid;
    /**
     * 
     */
	private Integer userGender;
    /**
     * 
     */
	private String userAvatar;
    /**
     * 
     */
	private String userNickname;
    /**
     * 
     */
	private Integer userIsAdmin;
    /**
     * 
     */
	private Integer userAllow;
    /**
     * 
     */
	@JsonFormat(pattern="yyyy年MM月dd日 HH时mm分ss秒",timezone = "UTC")
	private Date userCreatTime;


	private Integer fansCounts;

	private Integer followCounts;

	private Integer userStatus;
}