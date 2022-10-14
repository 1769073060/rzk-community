package com.rzk.mapper.dao;

 import com.baomidou.mybatisplus.core.mapper.BaseMapper;
 import com.rzk.pojo.Message;
 import com.rzk.pojo.NewMessage;
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
public interface NewMessageDao extends BaseMapper<NewMessage> {
 List<NewMessage> getAllNewMessage(Integer id);


 NewMessage getLastNewMessage(Integer id);

}