package com.gruposv.microservice_stock.modules.inventory_planning_and_control.entity;

import com.gruposv.microservice_stock.modules.product.entity.ProductEntity;
import com.gruposv.microservice_stock.modules.stock.entity.StockLocationEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_planning_suggestions")
@Where(clause = "deleted_at IS NULL")
public class PlanningSuggestionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "planning_suggestion_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "inventory_planning_runs_id")
    private InventoryPlanningRunsEntity inventoryPlanningRun;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private StockLocationEntity stockLocation;

    @Column(name = "suggested_quantity")
    private Integer suggestedQuantity;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;

    public PlanningSuggestionsEntity() {
    }

    public PlanningSuggestionsEntity(Long id, InventoryPlanningRunsEntity inventoryPlanningRun, ProductEntity product, StockLocationEntity stockLocation, Integer suggestedQuantity, String reason, LocalDateTime deletedAt) {
        this.id = id;
        this.inventoryPlanningRun = inventoryPlanningRun;
        this.product = product;
        this.stockLocation = stockLocation;
        this.suggestedQuantity = suggestedQuantity;
        this.reason = reason;
        this.deletedAt = deletedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InventoryPlanningRunsEntity getInventoryPlanningRun() {
        return inventoryPlanningRun;
    }

    public void setInventoryPlanningRun(InventoryPlanningRunsEntity inventoryPlanningRun) {
        this.inventoryPlanningRun = inventoryPlanningRun;
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

    public Integer getSuggestedQuantity() {
        return suggestedQuantity;
    }

    public void setSuggestedQuantity(Integer suggestedQuantity) {
        this.suggestedQuantity = suggestedQuantity;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
