package com.gruposv.microservice_stock.modules.shipping.exception;

public class InvalidPickedQuantityException extends RuntimeException {
    public InvalidPickedQuantityException(String message) {
        super(message);
    }
}
