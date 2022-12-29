package com.rzk.controller.app;

import com.rzk.pojo.Category;
import com.rzk.pojo.CategoryMenu;
import com.rzk.service.CategoryMenuService;
import com.rzk.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class GetCategoryMessageController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryMenuService categoryMenuService;

    @PostMapping("/getMessage/getAllCategoryMessage")
    public List<Category> getAllCategoryMessage() {
        return categoryService.list();
    }

    @PostMapping("/getMessage/getCategoryMenuI")
    public List<CategoryMenu> getCategoryMenuI() {
        return categoryMenuService.getCategoryMenuI();
    }

    @PostMapping("/getMessage/getCategoryMenuII/{value}")
    public List<CategoryMenu> getCategoryMenuII(@PathVariable String value) {

        return categoryMenuService.getCategoryMenuII(value);
    }
}
