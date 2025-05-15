package com.gruposv.microservice_stock.modules.inventory_planning_and_control.repository;

import com.gruposv.microservice_stock.modules.inventory_planning_and_control.entity.InventoryPolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryPolicyRepository extends JpaRepository<InventoryPolicyEntity, Long> {
    Optional<InventoryPolicyEntity> findByProduct_Id(Long productId);

    @Query("SELECT COUNT(s) > 0 FROM InventoryPolicyEntity s WHERE s.product.id = :productId AND s.deletedAt IS NULL")
    boolean existsByProductId(Long productId);
}
