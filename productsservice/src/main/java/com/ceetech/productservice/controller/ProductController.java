package com.ceetech.productservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceetech.productservice.entity.Product;
import com.ceetech.productservice.model.GenericResponse;
import com.ceetech.productservice.model.ProductCreateRequest;
import com.ceetech.productservice.model.ProductCreateResponse;
import com.ceetech.productservice.service.ProductService;

import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("api/products")
@RestController
@Slf4j
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public GenericResponse<List<ProductCreateResponse>> listProducts() {
        List<ProductCreateResponse> pr = productService.findAll();
        GenericResponse<List<ProductCreateResponse>> resp = GenericResponse.<List<ProductCreateResponse>>builder()
                .success(true)
                .msg("Data fetched Successfully")
                .data(pr)
                .build();

        return resp;
    }

    @PostMapping
    public GenericResponse<ProductCreateResponse> createProduct(
            @RequestBody ProductCreateRequest productCreateRequest) {

        ProductCreateResponse pr = productService.createProduct(productCreateRequest);
        GenericResponse<ProductCreateResponse> resp = GenericResponse.<ProductCreateResponse>builder()
                .success(true)
                .msg("Successfully created")
                .data(pr)
                .build();

        return resp;
    }

    @GetMapping("{productId}")
    public GenericResponse<ProductCreateResponse> findById(@PathVariable("productId") Integer productId) {
        ProductCreateResponse pr = productService.findById(productId);
        GenericResponse<ProductCreateResponse> resp = GenericResponse.<ProductCreateResponse>builder()
                .success(true)
                .msg("Data fetched Successfully")
                .data(pr)
                .build();
        log.info("We returned : {}", pr);
        return resp;
    }

}
