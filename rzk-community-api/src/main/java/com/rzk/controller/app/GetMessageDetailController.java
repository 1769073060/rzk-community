package com.rzk.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rzk.pojo.*;
import com.rzk.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@RestController
public class GetMessageDetailController {

    @Autowired
    private MessageService messageDetailService;
    @Autowired
    private MessageImagesService messageImagesService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentReplyService commentReplyService;
    @Autowired
    private UserService userService;

    @PostMapping("/getMessage/getAllMessageDetail/{pageNumber}")
    public List<Message> getAllMessageDetail(@PathVariable Integer pageNumber) {

        PageHelper.startPage(pageNumber, 8);

        PageInfo<Message> pageInfo = new PageInfo<Message>(messageDetailService.getAllMessage());

        log.info("pageInfo"+pageInfo);
        if (pageInfo.getPageNum() < pageNumber) {
            List list1 = new LinkedList();
            list1.add(200);
            return list1;
        }
        List<Message> allMessage = pageInfo.getList();
        for (int i = 0; i < allMessage.size(); i++) {
            log.info("allMessage============="+allMessage);
            QueryWrapper<User> queryWrapperUser = new QueryWrapper<>();
            queryWrapperUser.eq("user_id",allMessage.get(i).getUserId());

            allMessage.get(i).setUser(userService.getOne(queryWrapperUser));
            MessageImages messageImages = new MessageImages();
            messageImages.setMessageId(allMessage.get(i).getMessageId());
            QueryWrapper<MessageImages> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("message_id",messageImages.getMessageId());

            allMessage.get(i).setMessageImages(messageImagesService.list(queryWrapper));
        }
        return allMessage;
        //return getImage(allMessage, userService, messageImagesService);
    }

    @PostMapping("/getMessage/getAllMessageDetail/{categoryId}/{pageNumber}")
    public List<Message> getMessageByCategoryId(@PathVariable String categoryId, @PathVariable Integer pageNumber) {
        PageHelper.startPage(pageNumber, 8);

        PageInfo<Message> pageInfo = new PageInfo<Message>(messageDetailService.getMessageByCategoryId(categoryId));

        if (pageInfo.getPageNum() < pageNumber) {
            List list1 = new LinkedList();
            list1.add(200);
            return list1;
        }
        List<Message> allMessage = pageInfo.getList();

        for (int i = 0; i < allMessage.size(); i++) {
            QueryWrapper<User> queryWrapperUser = new QueryWrapper<>();
            queryWrapperUser.eq("user_id",allMessage.get(i).getUserId());
            log.info("allMessage.get(i).getUserId()=================="+allMessage.get(i).getUserId());

            User byId = userService.getOne(queryWrapperUser);
            log.info("byId=================="+byId);

            allMessage.get(i).setUser(byId);
            MessageImages messageImages = new MessageImages();
            messageImages.setMessageId(allMessage.get(i).getMessageId());
            QueryWrapper<MessageImages> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("message_id",messageImages.getMessageId());

            allMessage.get(i).setMessageImages(messageImagesService.list(queryWrapper));
        }
        return allMessage;

        //return getImage(allMessage, userService, messageImagesService);
    }

    @PostMapping("/getMessage/getAllMessageDetailClassification/{categoryId}/{pageNumber}")
    public List<Message> getAllMessageDetailClassification(@PathVariable String categoryId, @PathVariable Integer pageNumber) {
        PageHelper.startPage(pageNumber, 8);

        PageInfo<Message> pageInfo = new PageInfo<Message>(messageDetailService.getMessageByCategoryIdClassification(categoryId));

        if (pageInfo.getPageNum() < pageNumber) {
            List list1 = new LinkedList();
            list1.add(200);
            return list1;
        }
        List<Message> allMessage = pageInfo.getList();

        for (int i = 0; i < allMessage.size(); i++) {
            QueryWrapper<User> queryWrapperUser = new QueryWrapper<>();
            queryWrapperUser.eq("user_id",allMessage.get(i).getUserId());
            log.info("allMessage.get(i).getUserId()=================="+allMessage.get(i).getUserId());

            User byId = userService.getOne(queryWrapperUser);
            log.info("byId=================="+byId);

            allMessage.get(i).setUser(byId);
            MessageImages messageImages = new MessageImages();
            messageImages.setMessageId(allMessage.get(i).getMessageId());
            QueryWrapper<MessageImages> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("message_id",messageImages.getMessageId());

            allMessage.get(i).setMessageImages(messageImagesService.list(queryWrapper));
        }
        return allMessage;

        //return getImage(allMessage, userService, messageImagesService);
    }

    public List<Message> getImage(List<Message> allMessage, UserService userService, MessageImagesService messageImagesService) {
        for (int i = 0; i < allMessage.size(); i++) {
            QueryWrapper<User> queryWrapperUserId = new QueryWrapper<>();
            queryWrapperUserId.eq("user_id",allMessage.get(i).getUserId());
            allMessage.get(i).setUser(userService.getOne(queryWrapperUserId));
            MessageImages messageImages = new MessageImages();
            messageImages.setMessageId(allMessage.get(i).getMessageId());
            QueryWrapper<MessageImages> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("message_id",messageImages.getMessageId());

            allMessage.get(i).setMessageImages(messageImagesService.list(queryWrapper));
        }
        return allMessage;
    }

    @PostMapping("/getMessageDetailById/{id}")
    public Message getMessageDetailById(@PathVariable Integer id) {
        QueryWrapper<Message> queryWrapperMessage = new QueryWrapper<>();
        queryWrapperMessage.eq("message_id",id);
        Message message = messageDetailService.getOne(queryWrapperMessage);

        if (message == null) {
            return null;
        }

        Comment comment = new Comment();
        comment.setMessageId(id);
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("message_id",comment.getMessageId());

        List<Comment> comments = commentService.list(queryWrapper);
        message.setComments(comments);
        QueryWrapper<User> queryWrapperUser = new QueryWrapper<>();
        queryWrapperUser.eq("user_id",message.getUserId());
        User user = userService.getOne(queryWrapperUser);
        message.setUser(user);


        MessageImages messageImages = new MessageImages();
        messageImages.setMessageId(id);
        QueryWrapper<MessageImages> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("message_id",comment.getMessageId());

        message.setMessageImages(messageImagesService.list(queryWrapper1));

        Integer messageWatch = message.getMessageWatch();

        Message message1 = new Message();
        message1.setMessageId(id);

        message1.setMessageWatch(messageWatch + 1);
        message1.setMessageComment(comments.size());

        UpdateWrapper<Message> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("message_id",message1.getMessageId());

        messageDetailService.update(message1,updateWrapper);

        if (comments.size() == 0) {
            return message;
        }

        Integer commentReplyListCounts = 0;
        for (int i = 0; i < comments.size(); i++) {
            CommentReply commentReply = new CommentReply();
            commentReply.setCommentId(comments.get(i).getCommentId());
            QueryWrapper<CommentReply> queryWrapper2= new QueryWrapper<>();
            log.info("循环子评论{}"+comments.get(i).getCommentId());

            queryWrapper2.eq("comment_id",comments.get(i).getCommentId());
            List<CommentReply> commentReplyLists = commentReplyService.list(queryWrapper2);
            List<CommentReply> commentReplyList = new ArrayList<>();

            if (commentReplyLists.size()>0){
                commentReplyListCounts+=commentReplyLists.size();
                //循环获取用户头像
                for (CommentReply reply : commentReplyLists) {
                    QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                    userQueryWrapper.eq("user_id",reply.getReplayUserId());
                    User serviceOne = userService.getOne(userQueryWrapper);
                    reply.setUserAvatar(serviceOne.getUserAvatar());
                    commentReplyList.add(reply);
                }
            }
            comments.get(i).setCommentReplies(commentReplyList);
            QueryWrapper<User> queryWrapperUserList= new QueryWrapper<>();
            queryWrapperUserList.eq("user_id",comments.get(i).getUserId());

            comments.get(i).setUser(userService.getOne(queryWrapperUserList));
        }
        log.info("条数{}"+comments.size());
        log.info("条数{}"+commentReplyListCounts);
        message.setMessageComment(comments.size()+commentReplyListCounts);
        message.setComments(comments);
        return message;
    }

    @PostMapping("/getMessage/getMessageDetailByUserId/{userId}/{pageNumber}")
    public List<Message> getMessageDetailByUserId(@PathVariable Integer userId, @PathVariable Integer pageNumber) {
        QueryWrapper<User> queryWrapperUserList= new QueryWrapper<>();
        queryWrapperUserList.eq("user_id",userId);

        User user = userService.getOne(queryWrapperUserList);

        if (user.getUserIsAdmin() == 2) {
            PageHelper.startPage(pageNumber, 5);
            PageInfo<Message> pageInfo = new PageInfo<Message>(messageDetailService.getAllMessage());
            List<Message> list = pageInfo.getList();
            getImage(list, userService, messageImagesService);
            if (pageInfo.getPageNum() < pageNumber) {
                List list1 = new LinkedList();
                list1.add(200);
                return list1;
            }
            return list;
        } else {
            PageHelper.startPage(pageNumber, 3);
            PageInfo<Message> pageInfo = new PageInfo<Message>(messageDetailService.getMessageDetailByUserId(userId));
            List<Message> list = pageInfo.getList();
            getImage(list, userService,messageImagesService);
            if (pageInfo.getPageNum() < pageNumber) {
                List list1 = new LinkedList();
                list1.add(200);
                return list1;
            }
            return list;

        }


    }

}
