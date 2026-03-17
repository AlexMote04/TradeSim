package com.github.alexmote04.tradesim.service;

import com.github.alexmote04.tradesim.model.*;
import com.github.alexmote04.tradesim.repository.AccountRepository;
import com.github.alexmote04.tradesim.repository.AssetRepository;
import com.github.alexmote04.tradesim.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;
    private final AssetRepository assetRepository;

    public OrderService(OrderRepository orderRepository, AccountRepository accountRepository, AssetRepository assetRepository) {
        this.orderRepository = orderRepository;
        this.accountRepository = accountRepository;
        this.assetRepository = assetRepository;
    }

    @Transactional
    public Order placeOrder(UUID accountId, String ticker, OrderSide side, BigDecimal price, Integer quantity) {
        // 1. Fetch the account
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        // 2. Calculate the total cost or value of the order
        BigDecimal totalValue = price.multiply(BigDecimal.valueOf(quantity));

        // 3. Apply Trading Rules based on the side (BUY or SELL)
        if (side == OrderSide.BUY) {
            if (account.getBalance().compareTo(totalValue) < 0) {
                throw new IllegalArgumentException("Insufficient fiat balance to place BUY order.");
            }
            // Deduct the funds immediately (Escrow)
            account.setBalance(account.getBalance().subtract(totalValue));
            accountRepository.save(account);

        } else if (side == OrderSide.SELL) {
            Asset asset = assetRepository.findByAccountIdAndTicker(accountId, ticker)
                    .orElseThrow(() -> new IllegalArgumentException("You do not own any " + ticker + " to sell."));

            if (asset.getQuantity() < quantity) {
                throw new IllegalArgumentException("Insufficient asset quantity to place SELL order.");
            }
            // Deduct the assets immediately (Escrow)
            asset.setQuantity(asset.getQuantity() - quantity);
            assetRepository.save(asset);
        }

        // 4. Create and save the Open Order
        Order order = new Order();
        order.setAccount(account);
        order.setTicker(ticker.toUpperCase());
        order.setSide(side);
        order.setStatus(OrderStatus.OPEN);
        order.setPrice(price);
        order.setQuantity(quantity);

        return orderRepository.save(order);
    }
}