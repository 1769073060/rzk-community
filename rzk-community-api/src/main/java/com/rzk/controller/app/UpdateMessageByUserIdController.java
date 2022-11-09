package com.rzk.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.rzk.config.VerifyUser;
import com.rzk.pojo.Message;
import com.rzk.pojo.User;
import com.rzk.service.*;
import com.rzk.service.UserService;
import com.rzk.utils.status.BaseResponse;
import com.rzk.utils.status.MsgConsts;
import com.rzk.utils.status.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class UpdateMessageByUserIdController {

    @Autowired
    private MessageService messageDetailService;
    @Autowired
    private UserService userService;

    @Transactional
    @PostMapping("/updateMessageById/{id}/{messageId}")
    public ResponseResult updateMessageById(@PathVariable Integer id, @PathVariable Integer messageId, @RequestBody String message){
        VerifyUser verifyUser = new VerifyUser();
        //校验
        ResponseResult responseResult = verifyUser.VerifyUserId(id,userService);
        if (responseResult.getCode() == 400) {
            return responseResult;
        }
        User user = userService.getOne(new QueryWrapper<User>().eq("user_id",id));

        Message messageData = new Message();

        if (user.getUserIsAdmin() == 2) {
            messageData.setMessageId(messageId);
        } else {
            messageData.setMessageId(messageId);
            messageData.setUserId(id);

            QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("message_id",messageData.getMessageId());
            Long count = messageDetailService.count(queryWrapper);

            if (count == 0) {
                return  new ResponseResult(MsgConsts.DATA_ERROR, null,null);
            }
        }
        messageData.setMessageDetail(message);
        //修改
        UpdateWrapper<Message> updateWrapperMessageDetail = new UpdateWrapper<>();
        updateWrapperMessageDetail.eq("message_id",messageData.getMessageId());
        messageDetailService.update(messageData,updateWrapperMessageDetail);
        return  new ResponseResult(MsgConsts.SUCCESS_CODE, null,null);

    }
}
