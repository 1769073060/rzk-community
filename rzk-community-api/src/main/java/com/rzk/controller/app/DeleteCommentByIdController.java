package com.rzk.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzk.pojo.Comment;
import com.rzk.pojo.CommentReply;
import com.rzk.pojo.User;
import com.rzk.service.CommentReplyService;
import com.rzk.service.CommentService;
import com.rzk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DeleteCommentByIdController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentReplyService commentReplyService;

    @Transactional
    @PostMapping("/deleteCommentByCommentId/{userId}/{messageId}")
    public Integer deleteCommentByCommentId(@PathVariable Integer userId, @PathVariable Integer messageId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        User user = userService.getOne(queryWrapper);
        if (user == null) {
            return 400;
        }
        Comment comment = new Comment();

        if (user.getUserIsAdmin() == 2) {
            comment.setCommentId(messageId);
        } else {
            comment.setCommentId(messageId);
            comment.setUserId(userId);
        }

        commentService.removeById(comment);

        CommentReply commentReply = new CommentReply();
        commentReply.setCommentId(messageId);
        commentReplyService.removeById(commentReply);


        return 200;
    }


    @Transactional
    @PostMapping("/deleteCommentReplyByCommentId/{userId}/{commentReplyId}")
    public Integer deleteCommentReplyByCommentId(@PathVariable Integer userId, @PathVariable Integer commentReplyId) {
        User user = userService.getById(userId);
        if (user == null) {
            return 400;
        }
        CommentReply commentReply = new CommentReply();

        if (user.getUserIsAdmin() == 2) {
            commentReply.setCommentReplyId(commentReplyId);
        } else {
            commentReply.setCommentReplyId(commentReplyId);
            commentReply.setReplayUserId(userId);
        }

        commentReplyService.removeById(commentReply);

        return 200;
    }

}


