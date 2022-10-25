package com.rzk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.rzk.mapper.dao.AttendDao;
import com.rzk.pojo.Attend;
import com.rzk.service.AttendService;
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
public class AttendServiceImpl extends ServiceImpl<AttendDao, Attend> implements AttendService {

    @Autowired
    private AttendDao attendMapper;

    public List<Attend> getAllAttendMessageByUserId(Integer id) {
        return attendMapper.getAllAttendMessageByUserId(id);
    }


}