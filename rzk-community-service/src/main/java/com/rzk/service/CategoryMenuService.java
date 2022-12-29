package com.rzk.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.rzk.pojo.CategoryMenu;

import java.util.List;

/**
 * 
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2022-12-11
 */
public interface CategoryMenuService extends IService<CategoryMenu> {

    List<CategoryMenu> getCategoryMenuI();

    List<CategoryMenu> getCategoryMenuII(String value);

}