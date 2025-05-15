package com.gruposv.microservice_purchasing.modules.product.exception;

public class DuplicateNameProductException extends RuntimeException {
    public DuplicateNameProductException(String message) {
        super(message);
    }
}
