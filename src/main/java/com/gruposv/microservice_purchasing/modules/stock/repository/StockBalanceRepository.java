package com.gruposv.microservice_purchasing.modules.stock.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gruposv.microservice_purchasing.modules.stock.entity.StockBalanceEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockBalanceRepository extends JpaRepository<StockBalanceEntity, Long> {
    Optional<StockBalanceEntity> findByProduct_IdAndStockLocation_Id(Long productId, Long locationId);
    List<StockBalanceEntity> findAllByProduct_Id(Long productId);
    Page<StockBalanceEntity> findAllByProduct_Id(Long productId, Pageable pageable);
    Optional<StockBalanceEntity> findByProduct_IdAndStockLocation_IdAndQuantityOnHandGreaterThanEqual(Long productId, Long locationId, Integer quantity);
    Optional<StockBalanceEntity> findByProduct_IdAndStockLocation_IdAndQuantityOnHandGreaterThan(Long productId, Long locationId, Integer quantity);
    Optional<StockBalanceEntity> findByProduct_IdAndStockLocation_IdAndQuantityOnHandLessThanEqual(Long productId, Long locationId, Integer quantity);

    @Query("SELECT COUNT(s) > 0 FROM StockBalanceEntity s WHERE s.product.id = :productId AND s.stockLocation.id = :locationId AND s.deletedAt IS NULL")
    boolean existsByProduct_IdAndStockLocation_Id(Long productId, Long locationId);
}
