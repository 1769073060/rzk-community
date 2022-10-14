package com.rzk.mapper.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rzk.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2022-09-11
 */
@Mapper
public interface UserDao extends BaseMapper<User> {
    Integer insertUserMessage(User user);

}