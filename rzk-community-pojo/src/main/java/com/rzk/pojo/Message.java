package com.rzk.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
 
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
     * 
     */
	private Integer messageShare;
    /**
     * 
     */
	private Integer messageComment;
    /**
     * 
     */
	private Integer messageWatch;
    /**
     * 
     */
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