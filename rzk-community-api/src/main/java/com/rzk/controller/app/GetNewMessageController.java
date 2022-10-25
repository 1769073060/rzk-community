package com.rzk.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rzk.pojo.NewMessage;
import com.rzk.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rzk.service.*;
import com.rzk.service.UserService;
import java.util.LinkedList;
import java.util.List;

@RestController
public class GetNewMessageController {


    @Autowired
    private UserService userService;
    @Autowired
    private NewMessageService newMessageService;

    @PostMapping("/getMessage/getAllNewMessage/{userId}/{pageNumber}")
    public List<NewMessage> getAllNewMessage(@PathVariable Integer userId, @PathVariable Integer pageNumber) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        User user = userService.getOne(queryWrapper);
        if (user == null) {
            return null;
        }
        PageHelper.startPage(pageNumber, 10);
        PageInfo<NewMessage> pageInfo = new PageInfo<NewMessage>(newMessageService.getAllNewMessage(userId));

        if (pageInfo.getPageNum() < pageNumber) {
            List list1 = new LinkedList();
            list1.add(200);
            return list1;
        }
        List<NewMessage> list = pageInfo.getList();
        return list;
    }

    @PostMapping("/getMessage/getLastNewMessage/{userId}")
    public NewMessage getLastNewMessage(@PathVariable Integer userId) {

        if (userId == -1) {
            return null;
        }

        return newMessageService.getLastNewMessage(userId);

    }


}
