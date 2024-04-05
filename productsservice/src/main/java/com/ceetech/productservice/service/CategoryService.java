package com.ceetech.productservice.service;

import java.util.List;

import com.ceetech.productservice.model.CategoryRequestDto;
import com.ceetech.productservice.model.CategoryResponseDto;

public interface CategoryService {

    CategoryResponseDto findCategoryByName(String categoryName);

    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);

    CategoryResponseDto findCategoryById(Integer categoryId);

    List<CategoryResponseDto> findAll();
}
