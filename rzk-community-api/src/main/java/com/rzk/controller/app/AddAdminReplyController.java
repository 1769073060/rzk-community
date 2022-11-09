package com.rzk.controller.app;

import com.rzk.pojo.NewMessage;
import com.rzk.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.rzk.service.*;

@RestController
public class AddAdminReplyController {

    @Autowired
    private UserService userService;
    @Autowired
    private NewMessageService newMessageService;

    @Transactional
    @PostMapping("/addNewMessageByAdmin/{adminId}/{messageId}/{messageUserId}")
    public Integer addNewMessageByAdmin(@PathVariable Integer adminId, @PathVariable Integer messageId, @PathVariable Integer messageUserId, @RequestBody String newMessage) {
        User admin = userService.getById(adminId);
        if (admin.getUserIsAdmin() != 2) {
            return 400;
        }

        NewMessage newMessageData = new NewMessage();
        newMessageData.setNewMessageDetail(newMessage);
        newMessageData.setMessageId(messageId);
        newMessageData.setUserId(messageUserId);
        newMessageData.setNewMessageType(3);
        newMessageService.save(newMessageData);

        return 200;
    }


}
