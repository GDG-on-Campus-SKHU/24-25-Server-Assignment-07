package com.gdg.songin.product.dto;


import lombok.Getter;

@Getter
public class ProductRequestDto {
    private int productId;
    String brand;
    String description;
    String price;
    String stockQuantity;       //수량
    String category;            //카테고리
}
