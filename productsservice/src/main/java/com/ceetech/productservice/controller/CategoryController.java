package com.ceetech.productservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceetech.productservice.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("api/v1/categories")
@RestController
@Slf4j
public class CategoryController {
    
     private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
}
