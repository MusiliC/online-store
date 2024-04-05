package com.ceetech.productservice.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ceetech.productservice.entity.Category;
import com.ceetech.productservice.exceptions.CategoryNotFoundException;
import com.ceetech.productservice.exceptions.ProductNotFoundException;
import com.ceetech.productservice.model.CategoryRequestDto;
import com.ceetech.productservice.model.CategoryResponseDto;
import com.ceetech.productservice.repository.CategoryRepository;
import com.ceetech.productservice.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponseDto findCategoryByName(String categoryName) {
        var categoryEntity = categoryRepository.findByName(categoryName);

        if (categoryEntity.isPresent()) {
            return mapToCategoryResponseDto(categoryEntity.get());
        }
        throw new ProductNotFoundException("Category with Name not found");
    }

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {

        try {
            var savedCategory = categoryRepository.save(mapToCategoryEntity(categoryRequestDto));
            return mapToCategoryResponseDto(savedCategory);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Duplicate Category Name");
        }
    }

    @Override
    public CategoryResponseDto findCategoryById(Integer categoryId) {
        var categoryEntity = categoryRepository.findById(categoryId);

        if (categoryEntity.isPresent()) {
            return mapToCategoryResponseDto(categoryEntity.get());
        }

        throw new CategoryNotFoundException("Category with id not found");
    }

    @Override
    public List<CategoryResponseDto> findAll() {
        return categoryRepository.findAll().stream().map(this::mapToCategoryResponseDto).toList();
    }

    private CategoryResponseDto mapToCategoryResponseDto(Category savedCategory) {
        CategoryResponseDto target = new CategoryResponseDto();
        BeanUtils.copyProperties(savedCategory, target);
        return target;
    }

    private Category mapToCategoryEntity(CategoryRequestDto categoryRequestDto) {
        Category target = new Category();
        BeanUtils.copyProperties(categoryRequestDto, target);
        return target;
    }

}
