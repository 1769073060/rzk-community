package com.rzk.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzk.config.VerifyUser;
import com.rzk.pojo.Attend;
import com.rzk.pojo.Comment;
import com.rzk.pojo.NewMessage;
import com.rzk.pojo.User;
import com.rzk.service.AttendService;
import com.rzk.service.CommentService;
import com.rzk.service.NewMessageService;
import com.rzk.service.UserService;
import com.rzk.utils.BadWordUtils;
import com.rzk.utils.status.MsgConsts;
import com.rzk.utils.status.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class AddCommentController {

    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private NewMessageService newMessageService;
    @Autowired
    private AttendService attendService;
    @Autowired
    private BadWordUtils badWordUtils;

    @Transactional
    @PostMapping("/addComment/{userId}/{messageId}/{messageUserId}")
    public ResponseResult addComment(@PathVariable Integer userId, @RequestBody String userComment, @PathVariable Integer messageId, @PathVariable Integer messageUserId) {
        VerifyUser verifyUser = new VerifyUser();

        //校验
        ResponseResult responseResult = verifyUser.VerifyUserIdAndUserAllow(userId,userService);
        if (responseResult.getCode() == 400) {
            return responseResult;
        }
        //检查是否需要替换敏感词
        String filterStr = badWordUtils.replaceSensitive(userComment);

        Comment comment = new Comment();
        comment.setMessageId(messageId);
        comment.setUserId(userId);
        comment.setCommentDetail(filterStr);

        commentService.save(comment);

        if(userId!=messageUserId) {
            NewMessage newMessage = new NewMessage();
            newMessage.setUserId(messageUserId);
            newMessage.setNewMessageType(1);
            newMessage.setNewMessageDetail(filterStr);
            newMessage.setMessageId(messageId);
            newMessageService.save(newMessage);
        }
        Attend attend = new Attend();

        attend.setMessageId(messageId);
        attend.setUserId(userId);
        QueryWrapper<Attend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("message_id",attend.getMessageId()).eq("user_id",attend.getUserId());
        if (attendService.count(queryWrapper) != 0) {
            return  new ResponseResult(MsgConsts.SUCCESS_CODE, null,null);

        }


        attendService.save(attend);

        return  new ResponseResult(MsgConsts.SUCCESS_CODE, null,null);



     }

}
