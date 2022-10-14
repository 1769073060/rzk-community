package com.rzk.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzk.pojo.Attend;
import com.rzk.pojo.Comment;
import com.rzk.pojo.NewMessage;
import com.rzk.pojo.User;
import com.rzk.service.AttendService;
import com.rzk.service.CommentService;
import com.rzk.service.NewMessageService;
import com.rzk.service.UserService;
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

    @Transactional
    @PostMapping("/addComment/{userId}/{messageId}/{messageUserId}")
    public ResponseResult addComment(@PathVariable Integer userId, @RequestBody String userComment, @PathVariable Integer messageId, @PathVariable Integer messageUserId) {

        User user = userService.getOne(new QueryWrapper<User>().eq("user_id",userId));
        if (user == null) {
            //用户为空
            return  new ResponseResult(MsgConsts.DATA_ERROR, null,null);

        }

        if(user.getUserAllow()!=1){
            return  new ResponseResult(MsgConsts.DISALE_PERMISSIONS, null,null);

        }

        Comment comment = new Comment();
        comment.setMessageId(messageId);
        comment.setUserId(userId);
        comment.setCommentDetail(userComment);
        commentService.save(comment);

        if(userId!=messageUserId) {
            NewMessage newMessage = new NewMessage();
            newMessage.setUserId(messageUserId);
            newMessage.setNewMessageType(1);
            newMessage.setNewMessageDetail(userComment);
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
