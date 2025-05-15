package com.gruposv.microservice_purchasing.modules.shipping.exception;

public class ItemsNotFullyPickedException extends RuntimeException {
    public ItemsNotFullyPickedException(String message) {
        super(message);
    }
}
