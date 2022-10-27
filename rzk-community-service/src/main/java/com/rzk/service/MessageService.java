package com.rzk.service;


import com.rzk.pojo.Message;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2022-09-11
 */
public interface MessageService extends IService<Message> {

    public Message getLostMessage();

    public Integer insertMessageDetail(Message message) ;

    Integer addMessage(Message message);

    public List<Message> getAllMessage();
    /**
     * 查询分类所有
     *
     * @param id
     * @return
     */
    public List<Message> getMessageByCategoryId(Integer id) ;

    /**
     * 在分类里查询信息
     */

    public List<Message> getMessageByCategoryAndKeyword(Integer id, String keyword);

    /**
     * 全局查询
     */
    public List<Message> getMessageByKeyword(String keyword);


    /**
     * 通过用户id查询
     */
    public List<Message> getMessageDetailByUserId(Integer userId);

    /**
     * 删除对应信息下的所有评论以及回复
     */
    public void deleteCommentAndReply(Integer messageId);



}