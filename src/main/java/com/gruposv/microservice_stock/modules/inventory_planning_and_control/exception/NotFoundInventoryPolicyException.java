package com.gruposv.microservice_stock.modules.inventory_planning_and_control.exception;

public class NotFoundInventoryPolicyException extends RuntimeException {
    public NotFoundInventoryPolicyException(String message) {
        super(message);
    }
}
