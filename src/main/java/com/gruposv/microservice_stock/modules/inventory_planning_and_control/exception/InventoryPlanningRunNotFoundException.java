package com.gruposv.microservice_stock.modules.inventory_planning_and_control.exception;

public class InventoryPlanningRunNotFoundException extends RuntimeException {
    public InventoryPlanningRunNotFoundException(String message) {
        super(message);
    }
}
