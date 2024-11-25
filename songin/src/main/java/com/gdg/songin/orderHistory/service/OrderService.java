package com.gdg.songin.orderHistory.service;


import com.gdg.songin.orderHistory.domain.OrderHistory;
import com.gdg.songin.orderHistory.dto.OrderResponseDto;
import com.gdg.songin.orderHistory.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gdg.songin.product.domain.Product;
import com.gdg.songin.product.repository.ProductRepository;
import com.gdg.songin.user.domain.User;
import com.gdg.songin.user.repository.UserRepository;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    // 사용자 ID로 주문 내역 조회
    public List<OrderResponseDto> getOrdersByUserId(Long userId) {
        List<OrderHistory> orders = orderRepository.findByUser_Id(userId);

        if (orders.isEmpty()) {
            throw new IllegalArgumentException("해당 유저의 주문이 존재하지 않습니다.");
        }

        return orders.stream()
                .map(order -> {
                    User user = userRepository.findById(order.getUserId())
                            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + order.getUserId()));
                    Product product = productRepository.findById(order.getProductId())
                            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + order.getProductId()));

                    return OrderResponseDto.from(order, user, product);
                })
                .collect(Collectors.toList());
    }

    // 주문 저장
    public OrderResponseDto saveOrder(Long userId, Long productId) {
        // 사용자 및 상품 데이터 가져오기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 사용자를 찾을 수 없습니다: " + userId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 상품을 찾을 수 없습니다: " + productId));

        // 새로운 주문 객체 생성
        OrderHistory order = OrderHistory.builder()
                .user(user)           // User 객체 설정
                .product(product)     // Product 객체 설정
                .build();

        // 저장 후 반환
        OrderHistory savedOrder = orderRepository.save(order);

        return OrderResponseDto.from(savedOrder, user, product); // 저장된 주문을 DTO로 변환
    }

    // 주문 취소
    @Transactional
    public void cancelOrder(Long userId, Long productId) {
        if (!orderRepository.existsByUser_IdAndProduct_Id(userId, productId)) {
            throw new IllegalArgumentException("해당 주문이 존재하지 않습니다.");
        }
        orderRepository.deleteByUser_IdAndProduct_Id(userId, productId);

        orderRepository.deleteByUser_IdAndProduct_Id(userId, productId);
    }
}
