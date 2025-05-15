package com.gruposv.microservice_purchasing.modules.shipping.mapper;

import com.gruposv.microservice_purchasing.modules.product.mapper.products.ProductMapper;
import com.gruposv.microservice_purchasing.modules.shipping.dto.shipment_item.ReturnShipmentItemDTO;
import com.gruposv.microservice_purchasing.modules.shipping.dto.shipment_item.ShipmentItemDTO;
import com.gruposv.microservice_purchasing.modules.shipping.entity.ShipmentItemsEntity;

public class ShipmentItemsMapper {

    public static ShipmentItemsEntity toCreateEntity(ShipmentItemDTO shipmentItemDTO){
        ShipmentItemsEntity shipmentItems = new ShipmentItemsEntity();

        shipmentItems.setQuantity(shipmentItemDTO.getQuantity());
        shipmentItems.setPickedQuantity(shipmentItemDTO.getPickedQuantity());
        shipmentItems.setPackagingDetails(shipmentItemDTO.getPackagingDetails());

        return shipmentItems;
    }

    public static ShipmentItemsEntity toUpdateEntity(ShipmentItemDTO shipmentItemDTO){

        ShipmentItemsEntity shipmentItems = new ShipmentItemsEntity();

        if(shipmentItemDTO.getId() != null){
            shipmentItems.setId(shipmentItemDTO.getId());
        }

        if(shipmentItemDTO.getQuantity() != null){
            shipmentItems.setQuantity(shipmentItemDTO.getQuantity());
        }

        if(shipmentItemDTO.getPackagingDetails() != null && !shipmentItemDTO.getPackagingDetails().isEmpty()){
            shipmentItems.setPackagingDetails(shipmentItemDTO.getPackagingDetails());
        }

        return shipmentItems;
    }

    public static ReturnShipmentItemDTO toDTO(ShipmentItemsEntity shipmentItems) {
        ReturnShipmentItemDTO returnShipmentItemDTO = new ReturnShipmentItemDTO();

        returnShipmentItemDTO.setId(shipmentItems.getId());
        returnShipmentItemDTO.setShipmentId(shipmentItems.getShipment().getId());
        returnShipmentItemDTO.setProduct(ProductMapper.returnDTO(shipmentItems.getProduct()));
        returnShipmentItemDTO.setQuantity(shipmentItems.getQuantity());

        return returnShipmentItemDTO;

    }
}
