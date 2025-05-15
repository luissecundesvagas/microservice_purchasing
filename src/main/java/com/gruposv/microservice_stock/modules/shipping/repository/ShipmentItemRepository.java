package com.gruposv.microservice_stock.modules.shipping.repository;

import com.gruposv.microservice_stock.modules.shipping.entity.ShipmentItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentItemRepository extends JpaRepository<ShipmentItemsEntity, Long> {
    List<ShipmentItemsEntity> findAllByShipment_Id(Long shipmentId);
    Optional<ShipmentItemsEntity> findByShipment_IdAndProduct_Id(Long shipmentId, Long productId);
}
