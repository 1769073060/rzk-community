package com.rzk.mapper.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rzk.pojo.wxserver.WpOptions;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 *
 * @author 刷新网站与公众号交互的验证码
 * @since 1.0.0 2022-07-28
 */
@Mapper
public interface WpOptionsMapper extends BaseMapper<WpOptions> {
	
}