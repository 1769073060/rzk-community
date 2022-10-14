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
@TableName("comment")
public class Comment{
	private static final long serialVersionUID = 1L;

    /**
     * 
     */
	private Integer commentId;
    /**
     * 
     */
	private Integer userId;
    /**
     * 
     */
	private Integer messageId;
    /**
     * 
     */
	private String commentDetail;
    /**
     * 
     */
	private Date commentCreatTime;
	@TableField(exist = false)
	private User user;
	@TableField(exist = false)
	private List<CommentReply> commentReplies;

}