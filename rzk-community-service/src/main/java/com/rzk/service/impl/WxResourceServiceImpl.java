package com.rzk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rzk.mapper.dao.WxResourceMapper;
import com.rzk.pojo.wxserver.WxResource;
import com.rzk.service.IWxResourceService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Rzk
 * @since 2022-02-02
 */
@Service
public class WxResourceServiceImpl extends ServiceImpl<WxResourceMapper, WxResource> implements IWxResourceService  {

}
