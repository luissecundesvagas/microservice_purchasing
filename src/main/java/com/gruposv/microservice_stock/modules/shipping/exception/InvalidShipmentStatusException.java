package com.gruposv.microservice_stock.modules.shipping.exception;

public class InvalidShipmentStatusException extends RuntimeException {
    public InvalidShipmentStatusException(String message) {
        super(message);
    }
}
