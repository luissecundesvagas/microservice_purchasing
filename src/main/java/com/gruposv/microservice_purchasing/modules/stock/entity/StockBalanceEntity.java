package com.gruposv.microservice_purchasing.modules.stock.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Where;

import com.gruposv.microservice_purchasing.modules.product.entity.ProductEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_stock_balance")
@Where(clause = "deleted_at IS NULL")
public class StockBalanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_balance_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private StockLocationEntity stockLocation;

    @Column(name = "quantity_on_hand", nullable = false)
    private Integer quantityOnHand; // Quantidade disponível em estoque

    @Column(name = "quantity_reserved", nullable = false)
    private Integer quantityReserved; // Quantidade reservada em estoque

    @Column(name = "minimum_stock_level", nullable = false)
    private Integer minimumStockLevel;

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;

    public StockBalanceEntity() {
    }

    public StockBalanceEntity(Long id, ProductEntity product, StockLocationEntity stockLocation, Integer quantityOnHand, Integer quantityReserved, Integer minimumStockLevel) {
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

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
