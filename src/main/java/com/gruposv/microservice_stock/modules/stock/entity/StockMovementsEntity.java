package com.gruposv.microservice_stock.modules.stock.entity;

import com.gruposv.microservice_stock.modules.product.entity.ProductEntity;
import com.gruposv.microservice_stock.modules.stock.enums.MovementType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_stock_movements")
public class StockMovementsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_movement_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type", nullable = false)
    private MovementType movementType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(name = "lot_number")
    private String lotNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stock_location_id")
    private StockLocationEntity stockLocation;

    @Column(name = "quantity", nullable = false)
    private Integer quantity; // Quantidade positiva para entrada e negativa para saída

    @Column(name = "movement_date")
    private LocalDateTime movementDate;

    @Column(name = "reference_id")
    private Long referenceId;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    public StockMovementsEntity() {
    }

    public StockMovementsEntity(Long id, MovementType movementType, ProductEntity product, String lotNumber, StockLocationEntity stockLocation, Integer quantity, LocalDateTime movementDate, Long referenceId, String remarks) {
        this.id = id;
        this.movementType = movementType;
        this.product = product;
        this.lotNumber = lotNumber;
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

    public MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public StockLocationEntity getStockLocation() {
        return stockLocation;
    }

    public void setStockLocation(StockLocationEntity stockLocation) {
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
