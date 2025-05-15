package com.gruposv.microservice_stock.modules.stock.dto.stock_balance;

import com.gruposv.microservice_stock.modules.product.dto.products.ReturnProductDTO;
import com.gruposv.microservice_stock.modules.stock.dto.stock_locations.StockLocationDTO;

public class ReturnStockBalanceDTO {

    private Long id;
    private ReturnProductDTO product;
    private StockLocationDTO stockLocation;
    private Integer quantityOnHand;
    private Integer quantityReserved;
    private Integer minimumStockLevel;

    public ReturnStockBalanceDTO() {
    }

    public ReturnStockBalanceDTO(Long id, ReturnProductDTO product, StockLocationDTO stockLocation, Integer quantityOnHand, Integer quantityReserved, Integer minimumStockLevel) {
        this.id = id;
        this.product = product;
        this.stockLocation = stockLocation;
        this.quantityOnHand = quantityOnHand;
        this.quantityReserved = quantityReserved;
        this.minimumStockLevel = minimumStockLevel;
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

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public Integer getQuantityReserved() {
        return quantityReserved;
    }

    public void setQuantityReserved(Integer quantityReserved) {
        this.quantityReserved = quantityReserved;
    }

    public Integer getMinimumStockLevel() {
        return minimumStockLevel;
    }

    public void setMinimumStockLevel(Integer minimumStockLevel) {
        this.minimumStockLevel = minimumStockLevel;
    }

}
