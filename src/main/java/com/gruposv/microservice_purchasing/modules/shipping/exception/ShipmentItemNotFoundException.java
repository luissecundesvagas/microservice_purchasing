package com.gruposv.microservice_purchasing.modules.shipping.exception;

public class ShipmentItemNotFoundException extends RuntimeException {
    public ShipmentItemNotFoundException(String message) {
        super(message);
    }
}
