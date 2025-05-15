package com.gruposv.microservice_purchasing.modules.product.exception;

public class AttributeNotFoundException extends RuntimeException {
    public AttributeNotFoundException(String message) {
        super(message);
    }
}
