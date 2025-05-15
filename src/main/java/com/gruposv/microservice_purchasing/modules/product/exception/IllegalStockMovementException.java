package com.gruposv.microservice_purchasing.modules.product.exception;

public class IllegalStockMovementException extends RuntimeException {
    public IllegalStockMovementException(String message) {
        super(message);
    }
}
