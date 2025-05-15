package com.gruposv.microservice_purchasing.modules.stock.exception.stock_balance;

public class DuplicateStockBalanceException extends RuntimeException {
    public DuplicateStockBalanceException(String message) {
        super(message);
    }
}
