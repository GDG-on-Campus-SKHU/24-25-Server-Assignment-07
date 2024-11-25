package com.gdg.songin.product.domain;


import com.gdg.songin.orderHistory.domain.OrderHistory;
import com.gdg.songin.product.dto.ProductRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;

    private String productName;         //제품이름

    private String description;         //제품설명

    private String price;               //가격

    private String stockQuantity;       //수량

    private String category;            //카테고리

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private OrderHistory orderHistory;

    @Builder
    public Product(String brand, String productName, String description, String price, String stockQuantity, String category) {
        this.brand = brand;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }

    public void update(ProductRequestDto productRequestDto) {
        this.brand = brand;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }
}
