package com.gruposv.microservice_stock.modules.shipping.dto.shipment_item;

import jakarta.validation.constraints.NotNull;

// Para criar ou editar um item de expedição
public class ShipmentItemDTO {

    private Long id;

    @NotNull(message = "Para criar um item de expedição, é necessário associar um produto.")
    private Long productId;

    @NotNull(message = "Para criar um item de expedição, é necessário informar uma quantidade.")
    private Integer quantity;

    private Integer pickedQuantity = 0;

    private String packagingDetails;

    private String lotNumber;

    public ShipmentItemDTO() {
    }

    public ShipmentItemDTO(Long id, Long productId, Integer quantity, Integer pickedQuantity, String packagingDetails, String lotNumber) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.pickedQuantity = pickedQuantity;
        this.packagingDetails = packagingDetails;
        this.lotNumber = lotNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }
}
