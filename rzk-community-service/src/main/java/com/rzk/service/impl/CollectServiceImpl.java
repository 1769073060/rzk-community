package com.rzk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rzk.mapper.dao.CollectDao;
import com.rzk.pojo.Collect;
import com.rzk.service.CollectService;
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
public class CollectServiceImpl extends ServiceImpl<CollectDao, Collect> implements CollectService {

    @Autowired
    private CollectDao collectMapper;

    public List<Collect> getAllCollectionMessageByUserId(Integer userId) {
        return collectMapper.getAllCollectionMessageByUserId(userId);
    }

}