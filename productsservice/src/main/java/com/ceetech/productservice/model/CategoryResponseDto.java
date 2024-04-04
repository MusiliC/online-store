package com.ceetech.productservice.model;

import java.util.List;

import com.ceetech.productservice.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponseDto {
    private String name;
    private List<Product> products;

}
