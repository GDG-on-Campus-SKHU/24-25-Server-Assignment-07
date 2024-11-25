package com.gdg.songin.orderHistory.dto;


import com.gdg.songin.orderHistory.domain.OrderHistory;
import com.gdg.songin.product.domain.Product;
import com.gdg.songin.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class OrderResponseDto {
    private Long orderId;
    private Long UserId;
    private String userName;
    private Long productId;
    private String productName;
    private String price;

    public static OrderResponseDto from(OrderHistory orderHistory, User user, Product product) {
        return OrderResponseDto.builder()
                .UserId(user.getId())
                .userName(user.getName())
                .productId(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .build();
    }

}
