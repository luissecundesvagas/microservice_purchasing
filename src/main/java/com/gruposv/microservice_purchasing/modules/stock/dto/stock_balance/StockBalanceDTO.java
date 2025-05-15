package com.gruposv.microservice_purchasing.modules.stock.dto.stock_balance;

import jakarta.validation.constraints.NotNull;

public class StockBalanceDTO {

    private Long id;

    @NotNull(message = "Para criar um saldo de estoque, é necessário passar o ID do produto!")
    private Long productId;

    @NotNull(message = "Para criar um saldo de estoque, é necessário passar o ID do local de estoque!")
    private Long stockLocationId;

    @NotNull(message = "Para criar um saldo de estoque, é necessário informar a quantidade disponível do produto.")
    private Integer quantityOnHand;

    @NotNull(message = "Para criar um saldo de estoque, é necessário informar a quantidade reservada do produto.")
    private Integer quantityReserved;

    @NotNull(message = "Para criar um saldo de estoque, é necessário informar o estoque mínimo do produto.")
    private Integer minimumStockLevel;

    public StockBalanceDTO() {
    }

    public StockBalanceDTO(Long id, Integer quantityOnHand, Integer quantityReserved, Integer minimumStockLevel) {
        this.id = id;
        this.quantityOnHand = quantityOnHand;
        this.quantityReserved = quantityReserved;
        this.minimumStockLevel = minimumStockLevel;
    }

    public StockBalanceDTO(Long id, Long productId, Long stockLocationId, Integer quantityOnHand, Integer quantityReserved, Integer minimumStockLevel) {
        this.id = id;
        this.productId = productId;
        this.stockLocationId = stockLocationId;
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
