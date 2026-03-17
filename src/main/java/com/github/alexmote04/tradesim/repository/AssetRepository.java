package com.github.alexmote04.tradesim.repository;

import com.github.alexmote04.tradesim.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssetRepository extends JpaRepository<Asset, UUID> {
    List<Asset> findByAccountId(UUID accountId);
    Optional<Asset> findByAccountIdAndTicker(UUID accountId, String ticker);
}