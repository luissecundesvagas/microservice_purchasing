package com.gruposv.microservice_stock.modules.stock.entity;

import com.gruposv.microservice_stock.modules.product.entity.ProductEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_stock_lot_details")
@Where(clause = "deleted_at IS NULL")
public class StockLotDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_lot_details_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stock_location_id")
    private StockLocationEntity stockLocation;

    @Column(name = "lot_number", nullable = false)
    private String lotNumber;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;

    public StockLotDetailsEntity() {
    }

    public StockLotDetailsEntity(Long id, ProductEntity product, StockLocationEntity stockLocation, String lotNumber, LocalDate expirationDate, Integer quantity) {
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

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public StockLocationEntity getStockLocation() {
        return stockLocation;
    }

    public void setStockLocation(StockLocationEntity stockLocation) {
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

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
