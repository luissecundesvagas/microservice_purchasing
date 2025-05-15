package com.gruposv.microservice_purchasing.modules.stock.dto.stock_movements;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RegisterStockTransferDTO {

    @NotNull(message = "Para movimentar o estoque, é necessário dizer qual produto está sendo movimentado.")
    private Long productId;

    @NotNull(message = "Para realizar a transferência de estoque, é necessário dizer para qual estoque o produto está saindo.")
    private Long locationOriginId;

    @NotNull(message = "Para realizar a transferência de estoque, é necessário dizer para qual estoque o produto está indo.")
    private Long locationDestinationId;

    @NotNull(message = "Para movimentar o estoque, é necessário dizer a quantidade de produto que está sendo movimentado.")
    @Positive(message = "A quantidade movimentada deve sempre ser um valor positivo.")
    private Integer quantity;

    private LocalDateTime movementDate;

    private Long referenceId;

    private String remarks;

    private String lotNumber;

    private LocalDate expirationDate;

    public RegisterStockTransferDTO() {
    }

    public RegisterStockTransferDTO(Long productId, Long locationOriginId, Long locationDestinationId, Integer quantity, LocalDateTime movementDate, Long referenceId, String remarks, String lotNumber, LocalDate expirationDate) {
        this.productId = productId;
        this.locationOriginId = locationOriginId;
        this.locationDestinationId = locationDestinationId;
        this.quantity = quantity;
        this.movementDate = movementDate;
        this.referenceId = referenceId;
        this.remarks = remarks;
        this.lotNumber = lotNumber;
        this.expirationDate = expirationDate;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getLocationOriginId() {
        return locationOriginId;
    }

    public void setLocationOriginId(Long locationOriginId) {
        this.locationOriginId = locationOriginId;
    }

    public Long getLocationDestinationId() {
        return locationDestinationId;
    }

    public void setLocationDestinationId(Long locationDestinationId) {
        this.locationDestinationId = locationDestinationId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(LocalDateTime movementDate) {
        this.movementDate = movementDate;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
}
