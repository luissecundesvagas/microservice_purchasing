package com.gruposv.microservice_stock.modules.product.exception;

public class IllegalStockMovementException extends RuntimeException {
    public IllegalStockMovementException(String message) {
        super(message);
    }
}
