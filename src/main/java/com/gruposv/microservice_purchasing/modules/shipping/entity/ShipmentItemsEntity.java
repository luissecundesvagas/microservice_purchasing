package com.gruposv.microservice_purchasing.modules.shipping.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Where;

import com.gruposv.microservice_purchasing.modules.product.entity.ProductEntity;
import com.gruposv.microservice_purchasing.modules.stock.entity.StockLocationEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_shipment_items")
@Where(clause = "deleted_at IS NULL")
public class ShipmentItemsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipment_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shipment_id")
    private ShipmentEntity shipment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stock_location_id")
    private StockLocationEntity stockLocation;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "picked_quantity")
    private Integer pickedQuantity;

    @Column(name = "packaging_details")
    private String packagingDetails;

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;

    public ShipmentItemsEntity() {
    }

    public ShipmentItemsEntity(Long id, ShipmentEntity shipment, ProductEntity product, StockLocationEntity stockLocation, Integer quantity, Integer pickedQuantity, String packagingDetails) {
        this.id = id;
        this.shipment = shipment;
        this.product = product;
        this.stockLocation = stockLocation;
        this.quantity = quantity;
        this.pickedQuantity = pickedQuantity;
        this.packagingDetails = packagingDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShipmentEntity getShipment() {
        return shipment;
    }

    public void setShipment(ShipmentEntity shipment) {
        this.shipment = shipment;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPickedQuantity() {
        return pickedQuantity;
    }

    public void setPickedQuantity(Integer pickedQuantity) {
        this.pickedQuantity = pickedQuantity;
    }

    public String getPackagingDetails() {
        return packagingDetails;
    }

    public void setPackagingDetails(String packagingDetails) {
        this.packagingDetails = packagingDetails;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
