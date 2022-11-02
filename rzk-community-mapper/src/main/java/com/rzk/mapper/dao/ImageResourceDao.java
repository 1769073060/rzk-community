package com.rzk.mapper.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rzk.pojo.ImageResource;
import org.apache.ibatis.annotations.Mapper;

/**
 * 图片资源表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2022-11-03
 */
@Mapper
public interface ImageResourceDao extends BaseMapper<ImageResource> {
	
}