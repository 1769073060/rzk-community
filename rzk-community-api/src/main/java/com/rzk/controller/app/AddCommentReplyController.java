package com.rzk.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzk.config.VerifyUser;
import com.rzk.pojo.*;
import com.rzk.service.AttendService;
import com.rzk.service.CommentReplyService;
import com.rzk.service.NewMessageService;
import com.rzk.service.UserService;
import com.rzk.utils.status.MsgConsts;
import com.rzk.utils.status.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AddCommentReplyController {

    private Logger logger = LoggerFactory.getLogger(AddCommentReplyController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private CommentReplyService commentReplyService;
    @Autowired
    private NewMessageService newMessageService;
    @Autowired
    private AttendService attendService;
    private VerifyUser verifyUser;

    @Transactional
    @PostMapping("/addCommentReply/{messageId}")
    public ResponseResult addCommentReply(@PathVariable Integer messageId, @RequestBody CommentReply commentReply) {
        //校验
        ResponseResult responseResult = verifyUser.VerifyUserIdAndUserAllow(commentReply.getReplayUserId());
        if (responseResult != null) {
            return responseResult;
        }
        commentReplyService.save(commentReply);

        if (commentReply.getReceiveUserId() == commentReply.getReplayUserId()) {
            logger.info("addCommentReply{}添加成功");
            return new ResponseResult(MsgConsts.SUCCESS_CODE, null, null);
        }


        if (commentReply.getReplayUserId() != commentReply.getReceiveUserId()) {
            NewMessage newMessage = new NewMessage();
            newMessage.setUserId(commentReply.getReceiveUserId());
            newMessage.setNewMessageType(2);
            newMessage.setMessageId(messageId);
            newMessage.setNewMessageDetail(commentReply.getReplyDetail());
            newMessageService.save(newMessage);
        }
        Attend attend = new Attend();
        attend.setMessageId(messageId);
        attend.setUserId(commentReply.getReplayUserId());

        QueryWrapper<Attend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("message_id", attend.getMessageId()).eq("user_id", attend.getUserId());
        if (attendService.count(queryWrapper) != 0) {
            return new ResponseResult(MsgConsts.SUCCESS_CODE, null, null);
        }

        attendService.save(attend);

        return new ResponseResult(MsgConsts.SUCCESS_CODE, null, null);

    }


}
