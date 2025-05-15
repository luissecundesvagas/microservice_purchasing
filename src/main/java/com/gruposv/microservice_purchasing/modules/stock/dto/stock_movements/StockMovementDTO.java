package com.gruposv.microservice_purchasing.modules.stock.dto.stock_movements;

import java.time.LocalDateTime;

import com.gruposv.microservice_purchasing.modules.product.dto.products.ReturnProductDTO;
import com.gruposv.microservice_purchasing.modules.stock.dto.stock_locations.StockLocationDTO;

public class StockMovementDTO {

    private Long id;

    private MovementTypeDTO movementType;

    private ReturnProductDTO product;

    private StockLocationDTO stockLocation;

    private Integer quantity;

    private LocalDateTime movementDate;

    private Long referenceId;

    private String remarks;

    public StockMovementDTO() {
    }

    public StockMovementDTO(Long id, MovementTypeDTO movementType, ReturnProductDTO product, StockLocationDTO stockLocation, Integer quantity, LocalDateTime movementDate, Long referenceId, String remarks) {
        this.id = id;
        this.movementType = movementType;
        this.product = product;
        this.stockLocation = stockLocation;
        this.quantity = quantity;
        this.movementDate = movementDate;
        this.referenceId = referenceId;
        this.remarks = remarks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MovementTypeDTO getMovementType() {
        return movementType;
    }

    public void setMovementType(MovementTypeDTO movementType) {
        this.movementType = movementType;
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
}
