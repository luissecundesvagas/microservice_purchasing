package com.gruposv.microservice_stock.modules.stock.repository;

import com.gruposv.microservice_stock.modules.stock.entity.StockLotDetailsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface StockLotDetailsRepository extends JpaRepository<StockLotDetailsEntity, Long> {
    Optional<StockLotDetailsEntity> findByProduct_IdAndLotNumberAndStockLocation_Id(Long productId, String lotNumber, Long locationId);
    Page<StockLotDetailsEntity> findAllByProduct_IdAndStockLocation_Id(Long productId, Long locationId, Pageable pageable);
    Page<StockLotDetailsEntity> findByProduct_IdAndStockLocation_IdAndExpirationDateBefore(Long productId, Long locationId, LocalDate expirationDate, Pageable pageable);
    Page<StockLotDetailsEntity> findByProduct_IdAndStockLocation_IdAndQuantityGreaterThan(Long productId, Long locationId, Integer quantity, Pageable pageable);

    @Query("SELECT COUNT(s) > 0 FROM StockLotDetailsEntity s WHERE s.product.id = :productId AND s.lotNumber = :lotNumber AND s.stockLocation.id = :locationId AND s.deletedAt IS NULL")
    boolean existsByProduct_IdAndLotNumberAndStockLocation_Id(Long productId, String lotNumber, Long locationId);
}
