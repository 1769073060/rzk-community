package com.rzk.config.isUpdate;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzk.pojo.*;
import com.rzk.service.*;

public class IsUpdate {

    private Integer code;

    public Integer getCode() {

        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public IsUpdate isTrue(Integer id, Integer messageId, String message, MessageService messageDetailService, UserService UserService) {



        IsUpdate isUpdate = new IsUpdate();
        isUpdate.setCode(500);
        User user = UserService.getById(id);

        if (user == null) {
            isUpdate.setCode(400);
            return isUpdate;
        }
        Message messageData = new Message();

        if (user.getUserIsAdmin() == 2) {
            messageData.setMessageId(messageId);
        } else {
            messageData.setMessageId(messageId);
            messageData.setUserId(id);

            QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id",messageData.getMessageId());
            Long count = messageDetailService.count(queryWrapper);

            if (count == 0) {
                isUpdate.setCode(400);
                return isUpdate;
            }
        }
        messageData.setMessageDetail(message);
        messageDetailService.updateById(messageData);
        isUpdate.setCode(200);
        return isUpdate;
    }
}
