package com.gdg.songin.orderHistory.domain;


import com.gdg.songin.product.domain.Product;
import com.gdg.songin.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;


    public static OrderHistory of(User user, Product product) {
        OrderHistory registration = new OrderHistory();
        registration.user = user;
        registration.product = product;
        return registration;
    }

    public OrderHistory(User user, Product product) {
        this.product = product;
        this.user = user;
    }

    public Long getUserId() {
        return user.getId();
    }

    public Long getProductId() {
        return product.getId();
    }

    @Builder
    public OrderHistory(Long id, User user, Product product) {
        this.id = id;
        this.user = user;
        this.product = product;
    }
}