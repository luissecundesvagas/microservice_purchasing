package com.gruposv.microservice_stock.modules.shipping.exception;

public class ShipmentNotFoundException extends RuntimeException {
    public ShipmentNotFoundException(String message) {
        super(message);
    }
}
