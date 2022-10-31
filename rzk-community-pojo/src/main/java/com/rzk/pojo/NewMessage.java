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
@TableName("new_message")
public class NewMessage{
	private static final long serialVersionUID = 1L;

    /**
     * 
     */
	@JsonFormat(pattern="yyyy年MM月dd日 HH时mm分ss秒",timezone = "UTC")
	private Date newMessageTime;
    /**
     * 
     */
	private Integer newMessageId;
    /**
     * 
     */
	private Integer userId;
    /**
     * 
     */
	private Integer newMessageType;
    /**
     * 
     */
	private Integer messageId;
    /**
     * 
     */
	private String newMessageDetail;
}