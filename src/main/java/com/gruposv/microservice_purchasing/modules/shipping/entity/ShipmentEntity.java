package com.gruposv.microservice_purchasing.modules.shipping.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Where;

import com.gruposv.microservice_purchasing.modules.shipping.enums.ShipmentStatus;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_shipments")
@Where(clause = "deleted_at IS NULL")
public class ShipmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipment_id")
    private Long id;

    @Column(name = "sale_order_id")
    private Long saleOrderId; // Referencia para o pedido de venda que está em outro MS

    @Column(name = "shipment_date")
    private LocalDateTime shipmentDate;

    @Column(name = "carrier")
    private String carrier;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "shipment_status")
    private ShipmentStatus shipmentStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ShipmentItemsEntity> shipmentItemsList;

    public ShipmentEntity() {
    }

    public ShipmentEntity(Long id, Long saleOrderId, LocalDateTime shipmentDate, String carrier, String trackingNumber, ShipmentStatus shipmentStatus, LocalDateTime createdAt, LocalDateTime updatedAt, List<ShipmentItemsEntity> shipmentItemsList) {
        this.id = id;
        this.saleOrderId = saleOrderId;
        this.shipmentDate = shipmentDate;
        this.carrier = carrier;
        this.trackingNumber = trackingNumber;
        this.shipmentStatus = shipmentStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.shipmentItemsList = shipmentItemsList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSaleOrderId() {
        return saleOrderId;
    }

    public void setSaleOrderId(Long saleOrderId) {
        this.saleOrderId = saleOrderId;
    }

    public LocalDateTime getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(LocalDateTime shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public ShipmentStatus getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(ShipmentStatus shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<ShipmentItemsEntity> getShipmentItemsList() {
        return shipmentItemsList;
    }

    public void setShipmentItemsList(List<ShipmentItemsEntity> shipmentItemsList) {
        this.shipmentItemsList = shipmentItemsList;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
