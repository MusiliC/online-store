package com.ceetech.productservice.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreateRequest {
  private String name;
  private BigDecimal price;
  private String productCode;
  private String categoryName;
  private String image;
}
