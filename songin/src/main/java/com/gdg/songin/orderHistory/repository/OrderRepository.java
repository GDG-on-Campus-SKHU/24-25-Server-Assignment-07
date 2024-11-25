package com.gdg.songin.orderHistory.repository;

import com.gdg.songin.orderHistory.domain.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderHistory, Long> {

    // user 엔티티의 id를 참조하여 검색
    List<OrderHistory> findByUser_Id(Long userId);

    boolean existsByUser_IdAndProduct_Id(Long userId, Long productId);

    void deleteByUser_IdAndProduct_Id(Long userId, Long productId);
}
