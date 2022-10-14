package com.rzk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.rzk.mapper.dao.NewMessageDao;
import com.rzk.pojo.NewMessage;
import com.rzk.service.NewMessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2022-09-11
 */
@Service
public class NewMessageServiceImpl extends ServiceImpl<NewMessageDao, NewMessage> implements NewMessageService {

    @Autowired
    private NewMessageDao newMessageMapper;

    public List<NewMessage> getAllNewMessage(Integer userId) {
        return newMessageMapper.getAllNewMessage(userId);
    }

    public NewMessage getLastNewMessage(Integer id) {
        return newMessageMapper.getLastNewMessage(id);
    }


}