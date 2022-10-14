package com.rzk.controller.app;

import com.rzk.config.isDelete.IsDelete;
import com.rzk.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DeleteByUserIdController {
    @Autowired
    private MessageService messageDetailService;
    @Autowired
    private MessageImagesService messageImagesService;
    @Autowired
    private AttendService attendService;
    @Autowired
    private UserService userService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private NewMessageService newMessageService;


    @Transactional
    @PostMapping("/deleteMessageById/{userId}/{messageId}")
    public IsDelete deleteByUserId(@PathVariable Integer userId, @PathVariable Integer messageId) {
        return new IsDelete().isDelete(userId, messageId, messageImagesService, userService, messageDetailService,attendService,collectService,newMessageService);
    }


}
