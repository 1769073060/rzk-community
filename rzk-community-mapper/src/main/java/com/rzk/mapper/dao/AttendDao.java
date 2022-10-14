package com.rzk.mapper.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rzk.pojo.Attend;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2022-09-11
 */
@Mapper
public interface AttendDao extends BaseMapper<Attend> {
    List<Attend> getAllAttendMessageByUserId(Integer id);

}