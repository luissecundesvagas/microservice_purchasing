package com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.mapper;

import java.time.LocalDateTime;

import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.dto.inventory_planning_run.InventoryPlanningRunDTO;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.dto.inventory_planning_run.ReturnInventoryPlanningRunDTO;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.entity.InventoryPlanningRunsEntity;

public class InventoryPlanningRunMapper {
    public static InventoryPlanningRunsEntity toEntity(InventoryPlanningRunDTO dto) {
        InventoryPlanningRunsEntity entity = new InventoryPlanningRunsEntity();
        entity.setNotes(dto.getNotes());
        entity.setTriggerType(dto.getTriggerType());
        entity.setRunDate(LocalDateTime.now());
        return entity;
    }

    public static ReturnInventoryPlanningRunDTO toDTO(InventoryPlanningRunsEntity entity) {
        ReturnInventoryPlanningRunDTO dto = new ReturnInventoryPlanningRunDTO();
        dto.setId(entity.getId());
        dto.setRunDate(entity.getRunDate());
        dto.setNotes(entity.getNotes());
        dto.setTriggerType(entity.getTriggerType());
        if (entity.getPlanningSuggestionsList() != null) {
            dto.setPlanningSuggestionsList(entity.getPlanningSuggestionsList().stream()
                    .map(PlanningSuggestionMapper::toDTO)
                    .collect(java.util.stream.Collectors.toList()));
        }
        return dto;
    }
}
