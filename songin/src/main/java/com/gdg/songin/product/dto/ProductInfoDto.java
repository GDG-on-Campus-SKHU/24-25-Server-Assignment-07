package com.gdg.songin.product.dto;

import com.gdg.songin.product.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductInfoDto {
    Long id;
    String brand;
    String productName;         //제품이름
    String description;         //제품설명
    String price;               //가격
    String stockQuantity;       //수량
    String category;            //카테고리

    public static ProductInfoDto from(Product product) {
        return ProductInfoDto.builder()
                .id(product.getId())
                .brand(product.getBrand())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .category(product.getCategory())
                .build();
    }
}
