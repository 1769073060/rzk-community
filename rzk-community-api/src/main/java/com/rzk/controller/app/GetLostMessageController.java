package com.rzk.controller.app;

import com.rzk.pojo.Message;
import com.rzk.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class GetLostMessageController {
    @Autowired
    private MessageService messageDetailService;

    @PostMapping("/getMessage/getLostMessage")
    public Message getLostMessage(){

        return messageDetailService.getLostMessage();
    }
}
