package com.gruposv.microservice_stock.modules.stock.exception.stock_location;

public class StockLocationNameDuplicateException extends RuntimeException {
    public StockLocationNameDuplicateException(String message) {
        super(message);
    }
}
