package com.ceetech.productservice.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ceetech.productservice.entity.Product;
import com.ceetech.productservice.exceptions.ProductNotFoundException;
import com.ceetech.productservice.model.ProductCreateRequest;
import com.ceetech.productservice.model.ProductCreateResponse;
import com.ceetech.productservice.repository.ProductRepository;
import com.ceetech.productservice.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductCreateResponse createProduct(ProductCreateRequest productCreateRequest) {
        var savedProd = productRepository.save(mapToProductEntity(productCreateRequest));
        return mapToProductCreateResponse(savedProd);
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

    @Override
    public List<ProductCreateResponse> findAll() {
        return productRepository.findAll().stream().map(this::mapToProductCreateResponse).toList();
    }

    @Override
    public ProductCreateResponse findById(Integer productId) {
       var pr = productRepository.findById(productId);

       if (pr.isPresent()) {
        return mapToProductCreateResponse(pr.get());
       }
    throw new ProductNotFoundException("Product with id not found");

    }

}
