package com.gruposv.microservice_stock.modules.stock.exception.stock_location;

public class StockLocationCodeDuplicateException extends RuntimeException {
    public StockLocationCodeDuplicateException(String message) {
        super(message);
    }
}
