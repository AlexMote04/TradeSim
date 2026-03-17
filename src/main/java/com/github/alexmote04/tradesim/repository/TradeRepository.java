package com.github.alexmote04.tradesim.repository;

import com.github.alexmote04.tradesim.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TradeRepository extends JpaRepository<Trade, UUID> {
    List<Trade> findByBuyOrderIdOrSellOrderId(UUID buyOrderId, UUID sellOrderId);
}