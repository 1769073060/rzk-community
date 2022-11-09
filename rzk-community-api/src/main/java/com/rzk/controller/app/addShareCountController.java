package com.rzk.controller.app;

import com.rzk.pojo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rzk.service.MessageService;

@RestController
public class addShareCountController {


    @Autowired
    private MessageService messageDetailService;

    @PostMapping("/share/addShareCount/{messageId}")
    public Integer addShareCount(@PathVariable Integer messageId) {

        Message message = messageDetailService.getById(messageId);

        Message messageData = new Message();

        messageData.setMessageId(messageId);
        messageData.setMessageShare(message.getMessageShare() + 1);
        messageDetailService.updateById(messageData);


        return message.getMessageShare();
    }

}
