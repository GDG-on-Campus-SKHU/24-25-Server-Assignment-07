package com.gdg.songin.orderHistory.controller;


import com.gdg.songin.orderHistory.dto.OrderResponseDto;
import com.gdg.songin.orderHistory.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderHistoryController {

    private final OrderService orderService;

    @PostMapping("/{userId}/{productId}")
    public ResponseEntity<OrderResponseDto> createOrder(@PathVariable Long userId, @PathVariable Long productId) {
        OrderResponseDto orderResponseDto = orderService.saveOrder(userId, productId);
        return ResponseEntity.ok(orderResponseDto);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDto>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderResponseDto> orderList = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orderList);
    }

    @DeleteMapping("/cancel/{userId}/{productId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long userId, @PathVariable Long productId) {
        orderService.cancelOrder(userId, productId);
        return ResponseEntity.noContent().build();
        // 204
    }
}

