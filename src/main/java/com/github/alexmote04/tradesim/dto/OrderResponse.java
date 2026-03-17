package com.github.alexmote04.tradesim.dto;

import com.github.alexmote04.tradesim.model.Order;
import com.github.alexmote04.tradesim.model.OrderSide;
import com.github.alexmote04.tradesim.model.OrderStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        String ticker,
        OrderSide side,
        OrderStatus status,
        BigDecimal price,
        Integer quantity
) {
    public static OrderResponse fromEntity(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTicker(),
                order.getSide(),
                order.getStatus(),
                order.getPrice(),
                order.getQuantity()
        );
    }
}