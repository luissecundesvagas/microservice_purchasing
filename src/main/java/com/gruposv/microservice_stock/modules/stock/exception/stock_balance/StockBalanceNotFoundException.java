package com.gruposv.microservice_stock.modules.stock.exception.stock_balance;

public class StockBalanceNotFoundException extends RuntimeException {
    public StockBalanceNotFoundException(String message) {
        super(message);
    }
}
