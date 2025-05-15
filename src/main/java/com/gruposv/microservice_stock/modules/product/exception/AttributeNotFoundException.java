package com.gruposv.microservice_stock.modules.product.exception;

public class AttributeNotFoundException extends RuntimeException {
    public AttributeNotFoundException(String message) {
        super(message);
    }
}
