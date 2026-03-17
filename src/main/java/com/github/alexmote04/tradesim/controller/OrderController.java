package com.github.alexmote04.tradesim.controller;

import com.github.alexmote04.tradesim.dto.OrderRequest;
import com.github.alexmote04.tradesim.dto.OrderResponse;
import com.github.alexmote04.tradesim.model.Order;
import com.github.alexmote04.tradesim.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest request) {
        Order newOrder = orderService.placeOrder(
                request.accountId(),
                request.ticker(),
                request.side(),
                request.price(),
                request.quantity()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(OrderResponse.fromEntity(newOrder));
    }
}