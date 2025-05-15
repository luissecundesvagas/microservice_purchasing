package com.gruposv.microservice_stock.modules.shipping.exception;

public class ShipmentItemNotFoundException extends RuntimeException {
    public ShipmentItemNotFoundException(String message) {
        super(message);
    }
}
