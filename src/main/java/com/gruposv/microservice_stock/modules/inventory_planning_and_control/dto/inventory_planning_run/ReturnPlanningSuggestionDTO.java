package com.gruposv.microservice_stock.modules.inventory_planning_and_control.dto.inventory_planning_run;

import com.gruposv.microservice_stock.modules.product.dto.products.ReturnProductDTO;
import com.gruposv.microservice_stock.modules.stock.dto.stock_locations.StockLocationDTO;

public class ReturnPlanningSuggestionDTO {

    private Long id;
    private ReturnInventoryPlanningRunDTO inventoryPlanningRun;
    private ReturnProductDTO product;
    private StockLocationDTO stockLocation;
    private Integer suggestedQuantity;
    private String reason;

    public ReturnPlanningSuggestionDTO() {
    }

    public ReturnPlanningSuggestionDTO(Long id, ReturnInventoryPlanningRunDTO inventoryPlanningRun, ReturnProductDTO product, StockLocationDTO stockLocation, Integer suggestedQuantity, String reason) {
        this.id = id;
        this.inventoryPlanningRun = inventoryPlanningRun;
        this.product = product;
        this.stockLocation = stockLocation;
        this.suggestedQuantity = suggestedQuantity;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReturnInventoryPlanningRunDTO getInventoryPlanningRun() {
        return inventoryPlanningRun;
    }

    public void setInventoryPlanningRun(ReturnInventoryPlanningRunDTO inventoryPlanningRun) {
        this.inventoryPlanningRun = inventoryPlanningRun;
    }

    public ReturnProductDTO getProduct() {
        return product;
    }

    public void setProduct(ReturnProductDTO product) {
        this.product = product;
    }

    public StockLocationDTO getStockLocation() {
        return stockLocation;
    }

    public void setStockLocation(StockLocationDTO stockLocation) {
        this.stockLocation = stockLocation;
    }

    public Integer getSuggestedQuantity() {
        return suggestedQuantity;
    }

    public void setSuggestedQuantity(Integer suggestedQuantity) {
        this.suggestedQuantity = suggestedQuantity;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
