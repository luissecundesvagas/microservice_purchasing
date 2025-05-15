package com.gruposv.microservice_purchasing.modules.shipping.exception;

public class InvalidShipmentStatusException extends RuntimeException {
    public InvalidShipmentStatusException(String message) {
        super(message);
    }
}
