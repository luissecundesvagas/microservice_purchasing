package com.gruposv.microservice_stock.modules.shipping.dto.shipment;

import com.gruposv.microservice_stock.modules.shipping.enums.ShipmentStatus;

public class ShipmentStatusDTO {

    private String name;
    private String value;

    public ShipmentStatusDTO() {
    }

    public ShipmentStatusDTO(ShipmentStatus shipmentStatus) {
        this.name = shipmentStatus.name();
        this.value = shipmentStatus.getValue();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
