package com.rzk.mapper.dao;

 import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rzk.pojo.Message;
import org.apache.ibatis.annotations.Mapper;
 import org.apache.ibatis.annotations.Param;

 import java.util.List;

/**
 * 
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2022-09-11
 */
@Mapper
public interface MessageDao extends BaseMapper<Message> {

 Message getLostMessage();

 Integer insertMessageDetail(Message eessage);

 List<Message> getAllMessage();

 List<Message> getMessageByCategoryId(String id);

 List<Message> getMessageByCategoryIdClassification(String id);

 List<Message> getMessageByCategoryAndKeyword(@Param("id") String id, @Param("keyword") String keyword);

 List<Message> getMessageDetailByUserId (Integer userId);

 List<Message> getMessageByKeyword(@Param("keyword") String keyword);

 void deleteCommentAndReply(@Param("id") Integer id);
}