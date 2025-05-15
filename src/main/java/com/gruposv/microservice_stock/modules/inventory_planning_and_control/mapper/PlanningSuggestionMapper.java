package com.gruposv.microservice_stock.modules.inventory_planning_and_control.mapper;

import com.gruposv.microservice_stock.modules.inventory_planning_and_control.dto.inventory_planning_run.PlanningSuggestionDTO;
import com.gruposv.microservice_stock.modules.inventory_planning_and_control.dto.inventory_planning_run.ReturnInventoryPlanningRunDTO;
import com.gruposv.microservice_stock.modules.inventory_planning_and_control.dto.inventory_planning_run.ReturnPlanningSuggestionDTO;
import com.gruposv.microservice_stock.modules.inventory_planning_and_control.entity.InventoryPlanningRunsEntity;
import com.gruposv.microservice_stock.modules.inventory_planning_and_control.entity.PlanningSuggestionsEntity;
import com.gruposv.microservice_stock.modules.product.entity.ProductEntity;
import com.gruposv.microservice_stock.modules.product.mapper.products.ProductMapper;
import com.gruposv.microservice_stock.modules.stock.mapper.stock_locations.StockLocationsMapper;

public class PlanningSuggestionMapper {
    public static PlanningSuggestionsEntity toEntity(PlanningSuggestionDTO dto, InventoryPlanningRunsEntity run, ProductEntity product) {
        PlanningSuggestionsEntity entity = new PlanningSuggestionsEntity();
        entity.setInventoryPlanningRun(run);
        entity.setProduct(product);
        entity.setSuggestedQuantity(dto.getSuggestedQuantity());
        entity.setReason(dto.getReason());
        return entity;
    }

    public static ReturnPlanningSuggestionDTO toDTO(PlanningSuggestionsEntity entity) {
        ReturnPlanningSuggestionDTO dto = new ReturnPlanningSuggestionDTO();
        dto.setId(entity.getId());
        if (entity.getInventoryPlanningRun() != null) {
            ReturnInventoryPlanningRunDTO runDTO = new ReturnInventoryPlanningRunDTO();
            runDTO.setId(entity.getInventoryPlanningRun().getId());
            runDTO.setRunDate(entity.getInventoryPlanningRun().getRunDate());
            runDTO.setNotes(entity.getInventoryPlanningRun().getNotes());
            runDTO.setTriggerType(entity.getInventoryPlanningRun().getTriggerType());
            dto.setInventoryPlanningRun(runDTO);
        }
        if (entity.getProduct() != null) {
            dto.setProduct(ProductMapper.returnDTO(entity.getProduct()));
        }
        dto.setSuggestedQuantity(entity.getSuggestedQuantity());
        dto.setStockLocation(StockLocationsMapper.toDTO(entity.getStockLocation()));
        dto.setReason(entity.getReason());
        return dto;
    }
}