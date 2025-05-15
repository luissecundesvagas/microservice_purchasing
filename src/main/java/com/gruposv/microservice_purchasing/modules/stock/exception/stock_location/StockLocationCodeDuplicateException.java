package com.gruposv.microservice_purchasing.modules.stock.exception.stock_location;

public class StockLocationCodeDuplicateException extends RuntimeException {
    public StockLocationCodeDuplicateException(String message) {
        super(message);
    }
}
