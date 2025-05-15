package com.gruposv.microservice_stock.modules.stock.exception.stock_balance;

public class DuplicateStockBalanceException extends RuntimeException {
    public DuplicateStockBalanceException(String message) {
        super(message);
    }
}
