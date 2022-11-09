package com.rzk.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
        UpdateWrapper<Comment> removeCommentWrapper = new UpdateWrapper<>();

        if (user.getUserIsAdmin() == 2) {
            comment.setCommentId(messageId);
            removeCommentWrapper.eq("comment_id",comment.getCommentId());
            commentService.remove(removeCommentWrapper);
        } else {
            comment.setCommentId(messageId);
            comment.setUserId(userId);
            removeCommentWrapper.eq("comment_id",comment.getCommentId()).eq("user_id",comment.getUserId());
            commentService.remove(removeCommentWrapper);
        }


        CommentReply commentReply = new CommentReply();
        commentReply.setCommentId(messageId);
        UpdateWrapper<CommentReply> removeCommentReplyWrapper = new UpdateWrapper<>();

        removeCommentReplyWrapper.eq("comment_id",messageId);
        commentReplyService.remove(removeCommentReplyWrapper);


        return 200;
    }


    @Transactional
    @PostMapping("/deleteCommentReplyByCommentId/{userId}/{commentReplyId}")
    public Integer deleteCommentReplyByCommentId(@PathVariable Integer userId, @PathVariable Integer commentReplyId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        User user = userService.getOne(queryWrapper);
        if (user == null) {
            return 400;
        }
        CommentReply commentReply = new CommentReply();

        if (user.getUserIsAdmin() == 2) {
            commentReply.setCommentReplyId(commentReplyId);
            UpdateWrapper<CommentReply> removeWrapper = new UpdateWrapper<>();
            removeWrapper.eq("comment_reply_id",commentReply.getCommentReplyId());
            commentReplyService.remove(removeWrapper);
        } else {
            commentReply.setCommentReplyId(commentReplyId);
            commentReply.setReplayUserId(userId);
            UpdateWrapper<CommentReply> removeWrapper = new UpdateWrapper<>();
            removeWrapper.eq("comment_reply_id",commentReply.getCommentReplyId()).eq("replay_user_id",commentReply.getReplayUserId());
            commentReplyService.remove(removeWrapper);
        }



        return 200;
    }

}


