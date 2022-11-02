package com.rzk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rzk.mapper.dao.ImageResourceDao;
import com.rzk.pojo.ImageResource;
import com.rzk.service.ImageResourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 图片资源表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2022-11-03
 */
@Service
public class ImageResourceServiceImpl extends ServiceImpl<ImageResourceDao, ImageResource> implements ImageResourceService {

    public QueryWrapper<ImageResource> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<ImageResource> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}