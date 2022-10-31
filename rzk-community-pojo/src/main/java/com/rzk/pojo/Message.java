package com.rzk.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2022-09-11
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("message")
public class Message{
	private static final long serialVersionUID = 1L;

    /**
     * 
     */
	@TableId(value = "message_id",type= IdType.AUTO)//需要在实体类的主键属性上添加此句
	private Integer messageId;
    /**
     * 
     */
	private Integer userIdAnonymity;
    /**
     * 
     */
	private Integer userId;
    /**
     * 
     */
	private Integer categoryId;
    /**
     * 
     */
	private String userPhone;
    /**
     * 
     */
	private String userMajor;
    /**
     * 
     */
	private String userLevel;
    /**
     * 
     */
	private String messageDetail;
    /**
     * 分享
     */
	private Integer messageShare;
    /**
     * 评论数
     */
	private Integer messageComment;
    /**
     * 消息查看数量
     */
	private Integer messageWatch;
    /**
     * 
     */
	@JsonFormat(pattern="yyyy年MM月dd日 HH时mm分ss秒",timezone = "UTC")
	private Date messageCreatTime;
	@TableField(exist = false)
	private List<MessageImages> messageImages;
	@TableField(exist = false)
	private List<Comment> comments;
	@TableField(exist = false)
	private User user;

	@TableField(exist = false)
	private List<String> resultImage;

}