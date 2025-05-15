package com.gruposv.microservice_stock.modules.shipping.dto.shipment;

import com.gruposv.microservice_stock.modules.shipping.dto.shipment_item.ShipmentItemDTO;
import com.gruposv.microservice_stock.modules.shipping.enums.ShipmentStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

// Criar um novo registro de expedição ou editar
public class ShipmentDTO {

    @NotNull(message = "Para criar um registro de expedição, é necessário passar o ID do pedido de venda.")
    private Long SaleOrderId;

    @NotNull(message = "Para criar um registro de expedição, é necessário informar a data de expedição.")
    private LocalDateTime shipmentDate;

    @NotNull(message = "Para criar um registro de expedição, é necessário identificar a transportadora.")
    private String carrier;

    @NotNull(message = "Para criar um registro de expedição, é necessário passar o código de rastreamento.")
    private String trackingNumber;

    private ShipmentStatus shipmentStatus;

    @NotNull(message = "Para criar um novo registro de expedição, é necessário informar de qual estoque está saindo esses produtos.")
    private Long stockLocationId;

    @Valid
    @NotNull(message = "Para criar registros de expedições, é necessário passar uma lista de items.")
    @Size(min = 1, message = "A expedição deve conter pelo menos um item.")
    private List<ShipmentItemDTO> shipmentItems;

    public ShipmentDTO() {
    }

    public ShipmentDTO(Long saleOrderId, LocalDateTime shipmentDate, String carrier, String trackingNumber, ShipmentStatus shipmentStatus, Long stockLocationId, List<ShipmentItemDTO> shipmentItems) {
        SaleOrderId = saleOrderId;
        this.shipmentDate = shipmentDate;
        this.carrier = carrier;
        this.trackingNumber = trackingNumber;
        this.shipmentStatus = shipmentStatus;
        this.stockLocationId = stockLocationId;
        this.shipmentItems = shipmentItems;
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

    public ShipmentStatus getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(ShipmentStatus shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public Long getStockLocationId() {
        return stockLocationId;
    }

    public void setStockLocationId(Long stockLocationId) {
        this.stockLocationId = stockLocationId;
    }

    public List<ShipmentItemDTO> getShipmentItems() {
        return shipmentItems;
    }

    public void setShipmentItems(List<ShipmentItemDTO> shipmentItems) {
        this.shipmentItems = shipmentItems;
    }

}
