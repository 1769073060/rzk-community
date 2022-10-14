package com.rzk.controller.app;

import com.rzk.config.isUpdate.IsUpdate;
import com.rzk.service.*;
import com.rzk.service.UserService;
import com.rzk.utils.status.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UpdateMessageByUserIdController {

    @Autowired
    private MessageService messageDetailService;
    @Autowired
    private UserService userService;
    @Transactional
    @PostMapping("/updateMessageById/{id}/{messageId}")
    public IsUpdate updateMessageById(@PathVariable Integer id, @PathVariable Integer messageId, @RequestBody String message){


        return new IsUpdate().isTrue(id,messageId,message,messageDetailService,userService);
    }
}
