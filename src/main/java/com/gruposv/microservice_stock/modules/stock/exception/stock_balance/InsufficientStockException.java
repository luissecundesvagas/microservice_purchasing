package com.gruposv.microservice_stock.modules.stock.exception.stock_balance;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message) {
        super(message);
    }
}
