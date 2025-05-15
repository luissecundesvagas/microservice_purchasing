package com.gruposv.microservice_purchasing.modules.product.exception;

public class DuplicateSkuCodeException extends RuntimeException {
  public DuplicateSkuCodeException(String message) {
    super(message);
  }
}
