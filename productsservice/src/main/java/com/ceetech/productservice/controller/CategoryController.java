package com.ceetech.productservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ceetech.productservice.model.CategoryRequestDto;
import com.ceetech.productservice.model.CategoryResponseDto;
import com.ceetech.productservice.model.GenericResponse;
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

    @GetMapping
    public GenericResponse<List<CategoryResponseDto>> list(@RequestParam(required = false) String param) {
        List<CategoryResponseDto> pr = categoryService.findAll();
        GenericResponse<List<CategoryResponseDto>> resp = GenericResponse.<List<CategoryResponseDto>>builder()
                .success(true)
                .msg("Data fetched Successfully")
                .data(pr)
                .build();
        log.info("We returned : {}", pr);
        return resp;
    }

    @GetMapping("{categoryId}")
    public GenericResponse<CategoryResponseDto> findById(@PathVariable Integer categoryId) {
        CategoryResponseDto pr = categoryService.findCategoryById(categoryId);
        GenericResponse<CategoryResponseDto> resp = GenericResponse.<CategoryResponseDto>builder()
                .success(true)
                .msg("Data fetched Successfully")
                .data(pr)
                .build();

        log.info("We returned : {}", pr);
        return resp;
    }

    @GetMapping("category/{categoryName}")
    public GenericResponse<CategoryResponseDto> findByName(@PathVariable String categoryName) {
        CategoryResponseDto pr = categoryService.findCategoryByName(categoryName);
        GenericResponse<CategoryResponseDto> resp = GenericResponse.<CategoryResponseDto>builder()
                .success(true)
                .msg("Data fetched Successfully")
                .data(pr)
                .build();

        log.info("We returned : {}", pr);
        return resp;
    }

    @PostMapping
    public GenericResponse<CategoryResponseDto> createCategory(@RequestBody CategoryRequestDto categoryCreateRequest) {
        log.info("We received : {}", categoryCreateRequest);
        CategoryResponseDto pr = categoryService.createCategory(categoryCreateRequest);

        GenericResponse<CategoryResponseDto> resp = GenericResponse.<CategoryResponseDto>builder()
                .success(true)
                .msg("Data saved Successfully")
                .data(pr)
                .build();
        log.info("We returned : {}", pr);
        return resp;
    }
}
