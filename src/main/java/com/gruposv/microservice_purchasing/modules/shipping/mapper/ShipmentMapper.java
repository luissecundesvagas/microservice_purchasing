package com.gruposv.microservice_purchasing.modules.shipping.mapper;

import com.gruposv.microservice_purchasing.modules.shipping.dto.shipment.ReturnShipmentDTO;
import com.gruposv.microservice_purchasing.modules.shipping.dto.shipment.ShipmentDTO;
import com.gruposv.microservice_purchasing.modules.shipping.entity.ShipmentEntity;

public class ShipmentMapper {

    public static ShipmentEntity toCreateEntity(ShipmentDTO shipmentDTO){
        ShipmentEntity shipment = new ShipmentEntity();
        shipment.setSaleOrderId(shipmentDTO.getSaleOrderId());
        shipment.setShipmentDate(shipmentDTO.getShipmentDate());
        shipment.setCarrier(shipmentDTO.getCarrier());
        shipment.setTrackingNumber(shipmentDTO.getTrackingNumber());
        shipment.setShipmentStatus(shipmentDTO.getShipmentStatus());
        shipment.setShipmentItemsList(shipmentDTO.getShipmentItems().stream().map(ShipmentItemsMapper::toCreateEntity).toList());

        return shipment;
    }

    public static ShipmentEntity toUpdateEntity(ShipmentDTO shipmentDTO, ShipmentEntity shipment){

        if(shipmentDTO.getSaleOrderId() != null){
            shipment.setSaleOrderId(shipmentDTO.getSaleOrderId());
        }

        if(shipmentDTO.getShipmentDate() != null){
            shipment.setShipmentDate(shipmentDTO.getShipmentDate());
        }

        if(shipmentDTO.getCarrier() != null && !shipmentDTO.getCarrier().isEmpty()){
            shipment.setCarrier(shipmentDTO.getCarrier());
        }

        if(shipmentDTO.getTrackingNumber() != null && !shipmentDTO.getTrackingNumber().isEmpty()){
            shipment.setTrackingNumber(shipmentDTO.getTrackingNumber());
        }

        if(shipmentDTO.getShipmentStatus() != null){
            shipment.setShipmentStatus(shipmentDTO.getShipmentStatus());
        }

        if(shipmentDTO.getShipmentItems() != null && !shipmentDTO.getShipmentItems().isEmpty()){
            shipment.setShipmentItemsList(shipmentDTO.getShipmentItems()
                    .stream()
                    .map((shipmentItemDTO) -> ShipmentItemsMapper.toUpdateEntity(shipmentItemDTO))
                    .toList());
        }

        return shipment;

    }

    public static ReturnShipmentDTO toDTO(ShipmentEntity shipment){
        ReturnShipmentDTO shipmentDTO = new ReturnShipmentDTO();

        shipmentDTO.setId(shipment.getId());
        shipmentDTO.setSaleOrderId(shipment.getSaleOrderId());
        shipmentDTO.setShipmentDate(shipment.getShipmentDate());
        shipmentDTO.setCarrier((shipment.getCarrier()));
        shipmentDTO.setTrackingNumber(shipment.getTrackingNumber());
        shipmentDTO.setShipmentStatus(ShipmentStatusMapper.toDTO(shipment.getShipmentStatus()));
        shipmentDTO.setCreatedAt(shipment.getCreatedAt());
        shipmentDTO.setUpdatedAt(shipment.getUpdatedAt());
        shipmentDTO.setShipmentItems(shipment.getShipmentItemsList().stream().map(ShipmentItemsMapper::toDTO).toList());

        return shipmentDTO;
    }

}
