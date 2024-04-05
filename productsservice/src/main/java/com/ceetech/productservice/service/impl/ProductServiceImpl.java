package com.ceetech.productservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ceetech.productservice.entity.Category;
import com.ceetech.productservice.entity.Product;
import com.ceetech.productservice.exceptions.ProductNotFoundException;
import com.ceetech.productservice.model.ProductCreateRequest;
import com.ceetech.productservice.model.ProductCreateResponse;
import com.ceetech.productservice.repository.CategoryRepository;
import com.ceetech.productservice.repository.ProductRepository;
import com.ceetech.productservice.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // TODO CREATE PRODUCT
    @Override
    public ProductCreateResponse createProduct(ProductCreateRequest productCreateRequest) {
        try {

            Product newProduct = mapToProductEntity(productCreateRequest);
            // var savedProduct =
            // productRepository.save(mapToProductEntity(productCreateRequest));
            // return mapToProductCreateResponse(savedProduct);

            Category category = categoryRepository.findByName(productCreateRequest.getCategoryName())
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setName(productCreateRequest.getCategoryName());
                        return categoryRepository.save(newCategory);
                    });
            newProduct.setCategory(category);

            return mapToProductCreateResponse(productRepository.save(newProduct));
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(
                    "Duplicate product code");
        }
    }

    // TODO CREATE MULTIPLE PRODUCTS
    @Override
    public List<ProductCreateResponse> createMultipleProducts(List<ProductCreateRequest> productCreateRequests) {

        List<ProductCreateResponse> productResponses = new ArrayList<>();

        for (ProductCreateRequest request : productCreateRequests) {
            Product newProduct = mapToProductEntity(request);

            Category category = categoryRepository.findByName(request.getCategoryName())
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setName(request.getCategoryName());
                        return categoryRepository.save(newCategory);
                    });
            newProduct.setCategory(category);
            ProductCreateResponse response = mapToProductCreateResponse(productRepository.save(newProduct));
            productResponses.add(response);

        }
        return productResponses;
    }

    // TODO GET ALL PRODUCTS
    @Override
    public List<ProductCreateResponse> findAll() {
        return productRepository.findAll().stream().map(this::mapToProductCreateResponse).toList();
    }

    // TODO GET BY PRODUCT ID
    @Override
    public ProductCreateResponse findById(Integer productId) {
        var productEntity = productRepository.findById(productId);

        if (productEntity.isPresent()) {
            return mapToProductCreateResponse(productEntity.get());
        }
        throw new ProductNotFoundException("Product with id not found");
    }

    // TODO GET BY PRODUCT CODE
    @Override
    public ProductCreateResponse findByProductCode(String productCode) {
        var productEntity = productRepository.findProductByProductCode(productCode);

        if (productEntity != null) {
            return mapToProductCreateResponse(productEntity);
        }
        throw new ProductNotFoundException("Product with id not found");
    }

    // TODO CHECK PRODUCTS
    @Override
    public Boolean checkProducts(List<String> productCodes) {

        List<String> unavailableProducts = new ArrayList<>();

        for (String productCode : productCodes) {
            Product product = productRepository.findProductByProductCode(productCode);
            if (product == null) {
                unavailableProducts.add(productCode);
            }
        }

        if (unavailableProducts.isEmpty()) {
            return true;
        } else {
            throw new ProductNotFoundException("Product does not exist: " + unavailableProducts);
        }
    }

    private Product mapToProductEntity(ProductCreateRequest source) {
        Product target = new Product();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    private ProductCreateResponse mapToProductCreateResponse(Product source) {
        ProductCreateResponse target = new ProductCreateResponse();
        BeanUtils.copyProperties(source, target);
        return target;
    }

}
