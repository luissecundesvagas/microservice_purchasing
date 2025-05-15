package com.gruposv.microservice_purchasing.modules.shipping.exception;

public class InvalidPickedQuantityException extends RuntimeException {
    public InvalidPickedQuantityException(String message) {
        super(message);
    }
}
