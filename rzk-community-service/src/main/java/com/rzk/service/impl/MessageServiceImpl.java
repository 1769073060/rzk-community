package com.rzk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.rzk.mapper.dao.MessageDao;
import com.rzk.pojo.Message;
import com.rzk.service.MessageService;
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
public class MessageServiceImpl extends ServiceImpl<MessageDao, Message> implements MessageService {
    @Autowired
    private MessageDao messageMapper;

    public Message getLostMessage() {
        return messageMapper.getLostMessage();
    }

    public Integer insertMessageDetail(Message message) {
        return messageMapper.insertMessageDetail(message);
    }

    @Override
    public Integer addMessage(Message message) {
        int insert = messageMapper.insert(message);
        if (insert>0){
            return message.getMessageId();
        }return 0;
    }

    public List<Message> getAllMessage() {
        return messageMapper.getAllMessage();
    }

    /**
     * 查询分类所有
     *
     * @param id
     * @return
     */
    public List<Message> getMessageByCategoryId(String id) {
        return messageMapper.getMessageByCategoryId(id);
    }

    /**
     * 在分类里查询信息
     */

    public List<Message> getMessageByCategoryAndKeyword(String id, String keyword) {
        return messageMapper.getMessageByCategoryAndKeyword(id, keyword);
    }

    /**
     * 全局查询
     */
    public List<Message> getMessageByKeyword(String keyword){
        return messageMapper.getMessageByKeyword(keyword);
    }

    /**
     * 通过用户id查询
     */
    public List<Message> getMessageDetailByUserId(Integer userId) {
        return messageMapper.getMessageDetailByUserId(userId);
    }

    /**
     * 删除对应信息下的所有评论以及回复
     */
    public void deleteCommentAndReply(Integer messageId) {

        messageMapper.deleteCommentAndReply(messageId);
    }



}