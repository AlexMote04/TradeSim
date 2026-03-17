package com.github.alexmote04.tradesim.dto;


import com.github.alexmote04.tradesim.model.OrderSide;
import java.math.BigDecimal;
import java.util.UUID;

public record OrderRequest(
        UUID accountId,
        String ticker,
        OrderSide side,
        BigDecimal price,
        Integer quantity
) {}