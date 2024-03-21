package com.shopme.admin.category.controller;

import com.shopme.admin.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryRestController {
    @Autowired
    private CategoryService service;

    @PostMapping("/categories/check_unique")
    public String checkUnique(Integer id, String name, String alias){
        return service.checkUnique(id, name, alias);
    }
}
