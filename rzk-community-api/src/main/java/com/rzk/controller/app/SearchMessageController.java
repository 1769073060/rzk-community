package com.rzk.controller.app;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rzk.pojo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rzk.service.*;
import java.util.LinkedList;
import java.util.List;

@RestController
public class SearchMessageController {

    @Autowired
    private MessageService messageDetailService;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageImagesService messageImagesService;

    @PostMapping("/search/{categoryId}/{keyword}")
    public List<Message> getMessageByCategoryAndKeyword(@PathVariable Integer categoryId, @PathVariable String keyword) {
        List<Message> allMessage = messageDetailService.getMessageByCategoryAndKeyword(categoryId, keyword);
        return new GetMessageDetailController().getImage(allMessage, userService, messageImagesService);

    }

    @PostMapping("/searchByKeyword/{keyword}/{pageNumber}")
    public List<Message> getMessageByCategoryAndKeyword(@PathVariable String keyword, @PathVariable Integer pageNumber) {

        PageHelper.startPage(pageNumber, 3);

        PageInfo<Message> pageInfo = new PageInfo<Message>(messageDetailService.getMessageByKeyword(keyword));

        if (pageInfo.getPageNum() < pageNumber) {
            List list1 = new LinkedList();
            list1.add(200);
            return list1;
        }

        List<Message> allMessage = pageInfo.getList();

        return new GetMessageDetailController().getImage(allMessage, userService, messageImagesService);

    }

}
