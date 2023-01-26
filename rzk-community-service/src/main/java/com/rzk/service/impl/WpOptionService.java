package com.rzk.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.rzk.mapper.dao.WpOptionsMapper;
import com.rzk.pojo.wxserver.WpOptions;
import com.rzk.service.IWpOptionService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
@DS("slave")
public class WpOptionService extends ServiceImpl<WpOptionsMapper, WpOptions> implements IWpOptionService {
}
