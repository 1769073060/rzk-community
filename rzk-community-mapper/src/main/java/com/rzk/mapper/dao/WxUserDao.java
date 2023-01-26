package com.rzk.mapper.dao;


import com.rzk.pojo.wxserver.WxUser;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
/**
 * 微信公众用户表
 *
 * @author rzk 1769073060@qq.com
 * @since 1.0.0 2023-01-20
 */
@Mapper
public interface WxUserDao extends BaseMapper<WxUser> {
	
}