package com.gruposv.microservice_purchasing.modules.stock.exception.stock_balance;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message) {
        super(message);
    }
}
