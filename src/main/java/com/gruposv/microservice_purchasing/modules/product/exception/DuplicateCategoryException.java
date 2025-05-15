package com.gruposv.microservice_purchasing.modules.product.exception;

public class DuplicateCategoryException extends RuntimeException {
    public DuplicateCategoryException(String message) {
        super(message);
    }
}
