package com.gruposv.microservice_stock.modules.stock.repository;

import com.gruposv.microservice_stock.modules.stock.entity.StockMovementsEntity;
import com.gruposv.microservice_stock.modules.stock.enums.MovementType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovementsEntity, Long> {
    Page<StockMovementsEntity> findAllByProduct_IdOrderByMovementDateDesc(Long productId, Pageable pageable);
    Page<StockMovementsEntity> findAllByStockLocation_IdOrderByMovementDateDesc(Long locationId, Pageable pageable);
    Page<StockMovementsEntity> findAllByProduct_IdAndStockLocation_IdOrderByMovementDateDesc(Long productId, Long locationId, Pageable pageable);
    Page<StockMovementsEntity> findAllByMovementTypeAndMovementDateBetween(MovementType movementType, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
