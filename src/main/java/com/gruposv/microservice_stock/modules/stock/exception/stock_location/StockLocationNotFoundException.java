package com.gruposv.microservice_stock.modules.stock.exception.stock_location;

public class StockLocationNotFoundException extends RuntimeException {
  public StockLocationNotFoundException(String message) {
    super(message);
  }
}
