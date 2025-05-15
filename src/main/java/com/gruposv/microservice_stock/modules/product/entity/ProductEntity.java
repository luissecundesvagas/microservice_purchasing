package com.gruposv.microservice_stock.modules.product.entity;

import com.gruposv.microservice_stock.modules.inventory_planning_and_control.entity.InventoryPolicyEntity;
import com.gruposv.microservice_stock.modules.inventory_planning_and_control.entity.PlanningSuggestionsEntity;
import com.gruposv.microservice_stock.modules.product.enums.ProductStatus;
import com.gruposv.microservice_stock.modules.product.enums.ProductType;
import com.gruposv.microservice_stock.modules.product.enums.UnitOfMeasure;
import com.gruposv.microservice_stock.modules.shipping.entity.ShipmentItemsEntity;
import com.gruposv.microservice_stock.modules.stock.entity.StockBalanceEntity;
import com.gruposv.microservice_stock.modules.stock.entity.StockLotDetailsEntity;
import com.gruposv.microservice_stock.modules.stock.entity.StockMovementsEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_products")
@Where(clause = "deleted_at IS NULL")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "sku_code", length = 20, nullable = false)
    private String skuCode;

    @Column(name = "product_name", length = 125, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "ncm_code")
    private String ncmCode;

    @Column(name = "product_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Column(name = "unit_of_measure", nullable = false)
    @Enumerated(EnumType.STRING)
    private UnitOfMeasure unitOfMeasure;

    @Column(name = "product_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "tb_product_attribute",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_id")
    )
    private List<ProductAttributeEntity> productAttributes;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "tb_product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<ProductCategoryEntity> productCategories;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<StockBalanceEntity> stockBalanceList;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<StockMovementsEntity> stockMovements;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<StockLotDetailsEntity> stockLotDetailsList;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private InventoryPolicyEntity inventoryPolicy;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PlanningSuggestionsEntity> planningSuggestionsList;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ShipmentItemsEntity> shipmentItemsList;

    public ProductEntity() {
    }

    public ProductEntity(Long id, String skuCode, String name, String description, String ncmCode, ProductType productType, UnitOfMeasure unitOfMeasure, ProductStatus productStatus, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
        this.skuCode = skuCode;
        this.name = name;
        this.description = description;
        this.ncmCode = ncmCode;
        this.productType = productType;
        this.unitOfMeasure = unitOfMeasure;
        this.productStatus = productStatus;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNcmCode() {
        return ncmCode;
    }

    public void setNcmCode(String ncmCode) {
        this.ncmCode = ncmCode;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public List<ProductAttributeEntity> getProductAttributes() {
        return productAttributes;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void setProductAttributes(List<ProductAttributeEntity> productAttributes) {
        this.productAttributes = productAttributes;
    }

    public List<ProductCategoryEntity> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<ProductCategoryEntity> productCategories) {
        this.productCategories = productCategories;
    }

    public List<StockBalanceEntity> getStockBalanceList() {
        return stockBalanceList;
    }

    public void setStockBalanceList(List<StockBalanceEntity> stockBalanceList) {
        this.stockBalanceList = stockBalanceList;
    }

    public List<StockMovementsEntity> getStockMovements() {
        return stockMovements;
    }

    public void setStockMovements(List<StockMovementsEntity> stockMovements) {
        this.stockMovements = stockMovements;
    }

    public List<StockLotDetailsEntity> getStockLotDetailsList() {
        return stockLotDetailsList;
    }

    public void setStockLotDetailsList(List<StockLotDetailsEntity> stockLotDetailsList) {
        this.stockLotDetailsList = stockLotDetailsList;
    }

    public InventoryPolicyEntity getInventoryPolicy() {
        return inventoryPolicy;
    }

    public void setInventoryPolicy(InventoryPolicyEntity inventoryPolicy) {
        this.inventoryPolicy = inventoryPolicy;
    }

    public List<PlanningSuggestionsEntity> getPlanningSuggestionsList() {
        return planningSuggestionsList;
    }

    public void setPlanningSuggestionsList(List<PlanningSuggestionsEntity> planningSuggestionsList) {
        this.planningSuggestionsList = planningSuggestionsList;
    }

    public List<ShipmentItemsEntity> getShipmentItemsList() {
        return shipmentItemsList;
    }

    public void setShipmentItemsList(List<ShipmentItemsEntity> shipmentItemsList) {
        this.shipmentItemsList = shipmentItemsList;
    }

}
