package com.rzk.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
 
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
@TableName("comment_reply")
public class CommentReply{
	private static final long serialVersionUID = 1L;

    /**
     * 
     */
	private Integer commentReplyId;
    /**
     * 
     */
	private Integer commentId;
    /**
     * 
     */
	private Integer commentUserId;
    /**
     * 
     */
	private Integer replayUserId;
    /**
     * 
     */
	private String replayUserName;
    /**
     * 
     */
	private Integer receiveUserId;
    /**
     * 
     */
	private String receiveUserName;
    /**
     * 
     */
	private String replyDetail;
    /**
     * 
     */
	private Date replyTime;

	@TableField(exist = false)
	private String userAvatar;
}