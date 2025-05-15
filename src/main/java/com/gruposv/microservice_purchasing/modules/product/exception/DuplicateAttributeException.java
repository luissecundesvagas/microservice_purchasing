package com.gruposv.microservice_purchasing.modules.product.exception;

public class DuplicateAttributeException extends RuntimeException {
    public DuplicateAttributeException(String message) {
        super(message);
    }
}
