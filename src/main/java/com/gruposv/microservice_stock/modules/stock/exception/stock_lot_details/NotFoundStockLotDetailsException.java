package com.gruposv.microservice_stock.modules.stock.exception.stock_lot_details;

public class NotFoundStockLotDetailsException extends RuntimeException {
    public NotFoundStockLotDetailsException(String message) {
        super(message);
    }
}
