package com.rzk.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.rzk.pojo.Attend;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2022-09-11
 */
public interface AttendService extends IService<Attend> {

    public List<Attend> getAllAttendMessageByUserId(Integer id);
}