package com.rzk.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rzk.config.VerifyUser;
import com.rzk.pojo.Collect;
import com.rzk.pojo.Message;
import com.rzk.pojo.User;
import com.rzk.service.CollectService;
import com.rzk.service.MessageImagesService;
import com.rzk.service.MessageService;
import com.rzk.service.UserService;
import com.rzk.utils.status.MsgConsts;
import com.rzk.utils.status.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
public class AddCollectionController {
    @Autowired
    private CollectService collectService;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageImagesService messageImagesService;
    @Autowired
    private MessageService messageDetailService;


    @PostMapping("/getMessage/getAllCollectionMessageByUserId/{userId}/{pageNumber}")

    public List<Message> getAllCollectionMessageByUserId(@PathVariable Integer userId, @PathVariable Integer pageNumber) {
        PageHelper.startPage(pageNumber, 5);
        PageInfo<Collect> pageInfo = new PageInfo<Collect>(collectService.getAllCollectionMessageByUserId(userId));

        if (pageInfo.getPageNum() < pageNumber) {
            List list1 = new LinkedList();
            list1.add(200);
            return list1;
        }
        List<Collect> list = pageInfo.getList();


        List<Message> list1 = new ArrayList<Message>();
        for (int i = 0; i < list.size(); i++) {
            Integer messageId = list.get(i).getMessageId();
            Message message = messageDetailService.getById(messageId);
            list1.add(message);
        }
        return new GetMessageDetailController().getImage(list1, userService, messageImagesService);
    }


    @Transactional
    @PostMapping("/addCollection/{userId}/{messageId}")
    public ResponseResult addCollection(@PathVariable Integer userId, @PathVariable Integer messageId) {

        VerifyUser verifyUser = new VerifyUser();
        //校验
        ResponseResult responseResult = verifyUser.VerifyUserId(userId,userService);
        if (responseResult.getCode() == 400) {
            return responseResult;
        }

        Collect collect = new Collect();
        collect.setUserId(userId);
        collect.setMessageId(messageId);
        collectService.save(collect);


        return new ResponseResult(MsgConsts.SUCCESS_CODE,null,null);
    }

    @PostMapping("/addCollection/checkIsCollection/{userId}/{messageId}")
    public Integer checkIsCollection(@PathVariable Integer userId, @PathVariable Integer messageId) {
        Collect collect = new Collect();
        collect.setUserId(userId);
        collect.setMessageId(messageId);

        long count = collectService.count(new QueryWrapper<Collect>().eq("user_id",collect.getUserId()).eq("message_id",collect.getMessageId()));
        return Integer.valueOf((int) count);
    }

    @Transactional
    @PostMapping("/deleteCollection/{userId}/{messageId}")
    public Integer deleteCollection(@PathVariable Integer userId, @PathVariable Integer messageId) {


        Collect collect = new Collect();
        collect.setUserId(userId);
        collect.setMessageId(messageId);
        collectService.removeById(collect);
        return 200;
    }
}
