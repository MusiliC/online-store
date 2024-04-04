package com.ceetech.productservice.service;

import java.util.List;

import com.ceetech.productservice.model.ProductCreateRequest;
import com.ceetech.productservice.model.ProductCreateResponse;

public interface ProductService {

    ProductCreateResponse createProduct(ProductCreateRequest productCreateRequest);

    List<ProductCreateResponse> createMultipleProducts(List<ProductCreateRequest> productCreateRequests);
    
    List<ProductCreateResponse> findAll();

    ProductCreateResponse findById(Integer productId);

    ProductCreateResponse findByProductCode(String productCode);

    Boolean checkProducts(List<String> productCodes);
    
}
