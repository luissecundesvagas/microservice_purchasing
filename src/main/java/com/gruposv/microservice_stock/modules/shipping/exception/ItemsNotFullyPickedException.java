package com.gruposv.microservice_stock.modules.shipping.exception;

public class ItemsNotFullyPickedException extends RuntimeException {
    public ItemsNotFullyPickedException(String message) {
        super(message);
    }
}
