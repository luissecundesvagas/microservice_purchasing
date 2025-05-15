package com.gruposv.microservice_stock.modules.stock.repository;

import com.gruposv.microservice_stock.modules.stock.entity.StockLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockLocationRepository extends JpaRepository<StockLocationEntity, Long> {
    Optional<StockLocationEntity> findByLocationCode(String locationCode);
    Optional<StockLocationEntity> findByLocationNameContainingIgnoreCase(String locationName);

    @Query("SELECT COUNT(s) > 0 FROM StockLocationEntity s WHERE s.locationCode = :locationCode AND s.deletedAt IS NULL")
    boolean existsByLocationCode(String locationCode);

    @Query("SELECT COUNT(s) > 0 FROM StockLocationEntity s WHERE s.locationName = :locationName AND s.deletedAt IS NULL")
    boolean existsByLocationName(String locationName);
}
