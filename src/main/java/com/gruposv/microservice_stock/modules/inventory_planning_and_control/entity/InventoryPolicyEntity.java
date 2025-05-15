package com.gruposv.microservice_stock.modules.inventory_planning_and_control.entity;

import com.gruposv.microservice_stock.modules.product.entity.ProductEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_inventory_policies")
@Where(clause = "deleted_at IS NULL")
public class InventoryPolicyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_policy_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private ProductEntity product;

    @Column(name = "min_stock_level")
    private Integer minStockLevel;

    @Column(name = "max_stock_level")
    private Integer maxStockLevel;

    @Column(name = "reorder_quantity")
    private Integer reorderQuantity; // Quantidade sugerida para reabastecimento

    @Column(name = "lead_time_days")
    private Integer leadTimeDays; // Dias necessários para o reabastencimento

    @Column(name = "safety_stock")
    private Integer safetyStock; // Quantidade extra de estoque

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;

    public InventoryPolicyEntity() {
    }

    public InventoryPolicyEntity(Long id, ProductEntity product, Integer minStockLevel, Integer maxStockLevel, Integer reorderQuantity, Integer leadTimeDays, Integer safetyStock) {
        this.id = id;
        this.product = product;
        this.minStockLevel = minStockLevel;
        this.maxStockLevel = maxStockLevel;
        this.reorderQuantity = reorderQuantity;
        this.leadTimeDays = leadTimeDays;
        this.safetyStock = safetyStock;
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

    public Integer getMinStockLevel() {
        return minStockLevel;
    }

    public void setMinStockLevel(Integer minStockLevel) {
        this.minStockLevel = minStockLevel;
    }

    public Integer getMaxStockLevel() {
        return maxStockLevel;
    }

    public void setMaxStockLevel(Integer maxStockLevel) {
        this.maxStockLevel = maxStockLevel;
    }

    public Integer getReorderQuantity() {
        return reorderQuantity;
    }

    public void setReorderQuantity(Integer reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
    }

    public Integer getLeadTimeDays() {
        return leadTimeDays;
    }

    public void setLeadTimeDays(Integer leadTimeDays) {
        this.leadTimeDays = leadTimeDays;
    }

    public Integer getSafetyStock() {
        return safetyStock;
    }

    public void setSafetyStock(Integer safetyStock) {
        this.safetyStock = safetyStock;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
