package com.gruposv.microservice_purchasing.modules.shipping.exception;

public class ShipmentNotFoundException extends RuntimeException {
    public ShipmentNotFoundException(String message) {
        super(message);
    }
}
