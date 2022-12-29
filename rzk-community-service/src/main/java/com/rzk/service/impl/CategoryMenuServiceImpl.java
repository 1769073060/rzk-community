package com.rzk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rzk.mapper.dao.CategoryMenuDao;
import com.rzk.pojo.CategoryMenu;
import com.rzk.service.CategoryMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class CategoryMenuServiceImpl extends ServiceImpl<CategoryMenuDao, CategoryMenu> implements CategoryMenuService {

    @Autowired
    private CategoryMenuDao categoryMenuDao;

    public QueryWrapper<CategoryMenu> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<CategoryMenu> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


    @Override
    public List<CategoryMenu> getCategoryMenuI() {

        QueryWrapper<CategoryMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_value","0");
        return categoryMenuDao.getCategoryMenuI(queryWrapper);
    }

    @Override
    public List<CategoryMenu> getCategoryMenuII(String value) {

        QueryWrapper<CategoryMenu> queryWrapper = new QueryWrapper<>();
        System.out.println(value);
        queryWrapper.eq("parent_value",value);
        return categoryMenuDao.getCategoryMenuII(queryWrapper);
    }
}