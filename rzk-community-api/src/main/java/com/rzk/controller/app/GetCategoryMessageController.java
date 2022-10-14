package com.rzk.controller.app;

import com.rzk.pojo.Category;
import com.rzk.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class GetCategoryMessageController {

    @Autowired
    private CategoryService categoryService;


    @PostMapping("/getMessage/getAllCategoryMessage")
    public List<Category> getAllCategoryMessage() {
        return categoryService.list();
    }
}
