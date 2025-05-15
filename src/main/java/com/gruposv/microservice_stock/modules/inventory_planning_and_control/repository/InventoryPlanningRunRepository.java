package com.gruposv.microservice_stock.modules.inventory_planning_and_control.repository;

import com.gruposv.microservice_stock.modules.inventory_planning_and_control.entity.InventoryPlanningRunsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface InventoryPlanningRunRepository extends JpaRepository<InventoryPlanningRunsEntity, Long> {
    Optional<InventoryPlanningRunsEntity> findTopByOrderByRunDateDesc();
    Page<InventoryPlanningRunsEntity> findAllByRunDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
