package com.gdg.songin.orderHistory.dto;


import com.gdg.songin.orderHistory.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class OrderListDto {
    private List<OrderRepository> OrderList;

}