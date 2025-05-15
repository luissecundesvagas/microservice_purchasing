package com.gruposv.microservice_stock.modules.stock.dto.stock_lot_details;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class StockLotDetailsDTO {

    @NotNull(message = "Para a criação de um novo registro de lote é necessário definir um produto específico.")
    private Long productId;

    @NotNull(message = "Para a criação de um novo registro de lote é necessário definir um local de estoque específico.")
    private Long stockLocationId;

    @NotEmpty(message = "Para a criação de um novo registro de lote é necessário definir o número do lote.")
    private String lotNumber;

    @NotNull(message = "Para a criação de um novo registro de lote é necessário definir a data de expiração desse mesmo lote.")
    @Future(message = "A data de expiração deve ser futura...")
    private LocalDate expirationDate;

    @NotNull(message = "Para a criação de um novo registro de lote é necessário definir a quantidade por lote.")
    private Integer quantity;

    public StockLotDetailsDTO() {
    }

    public StockLotDetailsDTO(Long productId, Long stockLocationId, String lotNumber, LocalDate expirationDate, Integer quantity) {
        this.productId = productId;
        this.stockLocationId = stockLocationId;
        this.lotNumber = lotNumber;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getStockLocationId() {
        return stockLocationId;
    }

    public void setStockLocationId(Long stockLocationId) {
        this.stockLocationId = stockLocationId;
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
