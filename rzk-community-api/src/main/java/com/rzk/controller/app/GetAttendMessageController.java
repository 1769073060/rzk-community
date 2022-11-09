package com.rzk.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rzk.pojo.Attend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rzk.service.*;

import com.rzk.pojo.Message;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
public class GetAttendMessageController {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageDetailService;
    @Autowired
    private MessageImagesService messageImagesService;
    @Autowired
    private AttendService attendService;


    @PostMapping("/getMessage/getAttendMessageByUserId/{userId}/{pageNumber}")

    public List<Message> getAttendMessageByUserId(@PathVariable Integer userId, @PathVariable Integer pageNumber) {
        PageHelper.startPage(pageNumber, 5);
        PageInfo<Attend> pageInfo = new PageInfo<Attend>(attendService.getAllAttendMessageByUserId(userId));

        if (pageInfo.getPageNum() < pageNumber) {
            List list1 = new LinkedList();
            list1.add(200);
            return list1;
        }
        List<Attend> list = pageInfo.getList();


        List<Message> list1 = new ArrayList<Message>();
        for (int i = 0; i < list.size(); i++) {
            Integer messageId = list.get(i).getMessageId();
            QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("message_id",messageId);
            Message message = messageDetailService.getOne(queryWrapper);
            list1.add(message);
        }
        return new GetMessageDetailController().getImage(list1, userService, messageImagesService);
    }
}
