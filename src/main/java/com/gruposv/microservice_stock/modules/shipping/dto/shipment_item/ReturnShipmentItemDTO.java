package com.gruposv.microservice_stock.modules.shipping.dto.shipment_item;

import com.gruposv.microservice_stock.modules.product.dto.products.ReturnProductDTO;
import com.gruposv.microservice_stock.modules.shipping.dto.shipment.ReturnShipmentDTO;

// Retornar um item de expedição para resposta de API
public class ReturnShipmentItemDTO {

    private Long id;

    private Long shipmentId;

    private ReturnProductDTO product;

    private Integer quantity;

    private Integer pickedQuantity;

    private String packagingDetails;

    public ReturnShipmentItemDTO() {
    }

    public ReturnShipmentItemDTO(Long id, Long shipmentId, ReturnProductDTO product, Integer quantity, Integer pickedQuantity, String packagingDetails) {
        this.id = id;
        this.shipmentId = shipmentId;
        this.product = product;
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

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public ReturnProductDTO getProduct() {
        return product;
    }

    public void setProduct(ReturnProductDTO product) {
        this.product = product;
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
}
