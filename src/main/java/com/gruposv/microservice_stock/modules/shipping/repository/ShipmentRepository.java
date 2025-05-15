package com.gruposv.microservice_stock.modules.shipping.repository;

import com.gruposv.microservice_stock.modules.shipping.entity.ShipmentEntity;
import com.gruposv.microservice_stock.modules.shipping.enums.ShipmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<ShipmentEntity, Long> {
    Optional<ShipmentEntity> findBySaleOrderId(Long saleOrderId);
    Optional<ShipmentEntity> findByTrackingNumber(String trackingNumber);
    Page<ShipmentEntity> findAllByShipmentStatus(ShipmentStatus shipmentStatus, Pageable pageable);
    Page<ShipmentEntity> findAllByShipmentDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
