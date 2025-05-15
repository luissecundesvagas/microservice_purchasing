package com.gruposv.microservice_purchasing.modules.shipping.mapper;

import com.gruposv.microservice_purchasing.modules.shipping.dto.shipment.ShipmentStatusDTO;
import com.gruposv.microservice_purchasing.modules.shipping.enums.ShipmentStatus;

public class ShipmentStatusMapper {

    public static ShipmentStatusDTO toDTO(ShipmentStatus shipmentStatus){
        return new ShipmentStatusDTO(shipmentStatus);
    }

}
