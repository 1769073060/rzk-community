package com.rzk.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rzk.mapper.dao.WxUserDao;
import com.rzk.pojo.wxserver.WxUser;
import com.rzk.service.IWxUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 微信公众用户表
 *
 * @author rzk 1769073060@qq.com
 * @since 1.0.0 2023-01-20
 */
@DS("slave")
@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserDao, WxUser> implements IWxUserService {

    public QueryWrapper<WxUser> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<WxUser> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}