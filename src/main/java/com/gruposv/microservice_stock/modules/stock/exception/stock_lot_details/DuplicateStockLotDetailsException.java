package com.gruposv.microservice_stock.modules.stock.exception.stock_lot_details;

public class DuplicateStockLotDetailsException extends RuntimeException {
    public DuplicateStockLotDetailsException(String message) {
        super(message);
    }
}
