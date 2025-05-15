package com.gruposv.microservice_stock.modules.shipping.dto.shipment;

import com.gruposv.microservice_stock.modules.shipping.dto.shipment_item.ReturnShipmentItemDTO;

import java.time.LocalDateTime;
import java.util.List;

// Retornar DTO de Shipement na resposta da API
public class ReturnShipmentDTO {

    private Long id;

    private Long SaleOrderId;

    private LocalDateTime shipmentDate;

    private String carrier;

    private String trackingNumber;

    private ShipmentStatusDTO shipmentStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<ReturnShipmentItemDTO> shipmentItems;

    public ReturnShipmentDTO() {
    }

    public ReturnShipmentDTO(Long id, Long saleOrderId, LocalDateTime shipmentDate, String carrier, String trackingNumber, ShipmentStatusDTO shipmentStatus, LocalDateTime createdAt, LocalDateTime updatedAt, List<ReturnShipmentItemDTO> shipmentItems) {
        this.id = id;
        SaleOrderId = saleOrderId;
        this.shipmentDate = shipmentDate;
        this.carrier = carrier;
        this.trackingNumber = trackingNumber;
        this.shipmentStatus = shipmentStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.shipmentItems = shipmentItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSaleOrderId() {
        return SaleOrderId;
    }

    public void setSaleOrderId(Long saleOrderId) {
        SaleOrderId = saleOrderId;
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

    public ShipmentStatusDTO getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(ShipmentStatusDTO shipmentStatus) {
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

    public List<ReturnShipmentItemDTO> getShipmentItems() {
        return shipmentItems;
    }

    public void setShipmentItems(List<ReturnShipmentItemDTO> shipmentItems) {
        this.shipmentItems = shipmentItems;
    }
}
