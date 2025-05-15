package com.gruposv.microservice_purchasing.modules.stock.dto.stock_lot_details;

import java.time.LocalDate;

import com.gruposv.microservice_purchasing.modules.product.dto.products.ReturnProductDTO;
import com.gruposv.microservice_purchasing.modules.stock.dto.stock_locations.StockLocationDTO;

public class ReturnStockLotDetailsDTO {

    private Long id;

    private ReturnProductDTO product;

    private StockLocationDTO stockLocation;

    private String lotNumber;

    private LocalDate expirationDate;

    private Integer quantity;

    public ReturnStockLotDetailsDTO() {
    }

    public ReturnStockLotDetailsDTO(Long id, ReturnProductDTO product, StockLocationDTO stockLocation, String lotNumber, LocalDate expirationDate, Integer quantity) {
        this.id = id;
        this.product = product;
        this.stockLocation = stockLocation;
        this.lotNumber = lotNumber;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReturnProductDTO getProduct() {
        return product;
    }

    public void setProduct(ReturnProductDTO product) {
        this.product = product;
    }

    public StockLocationDTO getStockLocation() {
        return stockLocation;
    }

    public void setStockLocation(StockLocationDTO stockLocation) {
        this.stockLocation = stockLocation;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
