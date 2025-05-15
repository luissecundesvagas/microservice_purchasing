package com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.entity.PlanningSuggestionsEntity;

import java.util.Optional;

@Repository
public interface PlanningSuggestionRepository extends JpaRepository<PlanningSuggestionsEntity, Long> {
    Page<PlanningSuggestionsEntity> findAllByInventoryPlanningRun_Id(Long inventoryPlanningRunId, Pageable pageable);
    Optional<PlanningSuggestionsEntity> findByProduct_IdAndInventoryPlanningRun_Id(Long productId, Long inventoryPlanningRunId);
}
