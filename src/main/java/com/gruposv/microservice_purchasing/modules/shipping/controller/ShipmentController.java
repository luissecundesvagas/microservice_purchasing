package com.gruposv.microservice_purchasing.modules.shipping.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gruposv.microservice_purchasing.dto.ApiResponseDTO;
import com.gruposv.microservice_purchasing.modules.shipping.dto.shipment.ListShipmentDTO;
import com.gruposv.microservice_purchasing.modules.shipping.dto.shipment.ReturnShipmentDTO;
import com.gruposv.microservice_purchasing.modules.shipping.dto.shipment.ShipmentDTO;
import com.gruposv.microservice_purchasing.modules.shipping.dto.shipment_item.ReturnShipmentItemDTO;
import com.gruposv.microservice_purchasing.modules.shipping.dto.shipment_item.ShipmentItemDTO;
import com.gruposv.microservice_purchasing.modules.shipping.enums.ShipmentStatus;
import com.gruposv.microservice_purchasing.modules.shipping.service.ShipmentService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/stock/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<ReturnShipmentDTO>> createNewShipment(@RequestBody @Valid ShipmentDTO shipmentDTO){
        ReturnShipmentDTO shipment = this.shipmentService.createNewShipment(shipmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>("success", HttpStatus.CREATED.value(), shipment, "Expedição criada com sucesso!"));
    }

    @PatchMapping("/items/{shipmentItemId}")
    public ResponseEntity<ApiResponseDTO<ReturnShipmentItemDTO>> updatePickedQuantity(
            @PathVariable Long shipmentItemId,
            @RequestBody ShipmentItemDTO shipmentItemDTO
            ){
        ReturnShipmentItemDTO shipmentItem = this.shipmentService.updatePickedQuantity(shipmentItemId, shipmentItemDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), shipmentItem, "Quantidade separada atualizada com sucesso!"));
    }

    @PatchMapping("/{shipmentId}/dispatch")
    public ResponseEntity<ApiResponseDTO<ReturnShipmentDTO>> dispatchShipment(@PathVariable Long shipmentId, @RequestParam(required = false) String lotNumber){
        ReturnShipmentDTO shipment = this.shipmentService.dispatchShipment(shipmentId, lotNumber);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), shipment, "Expedição despachada com sucesso!"));
    }

    @PatchMapping("/{shipmentId}/conclude")
    public ResponseEntity<ApiResponseDTO<ReturnShipmentDTO>> concludeShipment(
            @PathVariable Long shipmentId
    ){
        ReturnShipmentDTO shipment = this.shipmentService.concludeShipment(shipmentId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), shipment, "Expedição concluída com sucesso!"));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<Page<ReturnShipmentDTO>>> findAllShipments(@ModelAttribute @Valid ListShipmentDTO listShipmentDTO){
        Page<ReturnShipmentDTO> shipments = this.shipmentService.findAllShipments(listShipmentDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), shipments, "Dados retornados com sucesso!"));
    }

    @GetMapping("/status/{shipmentStatus}")
    public ResponseEntity<ApiResponseDTO<Page<ReturnShipmentDTO>>> findAllByShipmentStatus(
            @PathVariable ShipmentStatus shipmentStatus,
            @ModelAttribute @Valid ListShipmentDTO listShipmentDTO
    ){
        Page<ReturnShipmentDTO> shipments = this.shipmentService.findAllByShipmentStatus(shipmentStatus, listShipmentDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), shipments, "Dados retornados com sucesso!"));
    }

    @GetMapping("/date-between")
    public ResponseEntity<ApiResponseDTO<Page<ReturnShipmentDTO>>> findAllByShipmentDateBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @ModelAttribute @Valid ListShipmentDTO listShipmentDTO
    ){
        Page<ReturnShipmentDTO> shipments = this.shipmentService.findAllByShipmentDateBetween(startDate, endDate, listShipmentDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), shipments, "Dados retornados com sucesso!"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ReturnShipmentDTO>> findShipmentById(@PathVariable Long id){
        ReturnShipmentDTO shipment = this.shipmentService.findShipmentById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), shipment, "Dados retornados com sucesso!"));
    }

    @GetMapping("/sale-order/{saleOrderId}")
    public ResponseEntity<ApiResponseDTO<ReturnShipmentDTO>> findBySaleOrderId(@PathVariable Long saleOrderId){
        ReturnShipmentDTO shipment = this.shipmentService.findBySaleOrderId(saleOrderId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), shipment, "Dados retornados com sucesso!"));
    }

    @GetMapping("/tracking-number/{trackingNumber}")
    public ResponseEntity<ApiResponseDTO<ReturnShipmentDTO>> findByTrackingNumber(@PathVariable String trackingNumber){
        ReturnShipmentDTO shipment = this.shipmentService.findByTrackingNumber(trackingNumber);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), shipment, "Dados retornados com sucesso!"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ReturnShipmentDTO>> updateShipment(
            @PathVariable Long id,
            @RequestBody ShipmentDTO shipmentDTO
    ){
        ReturnShipmentDTO shipment = this.shipmentService.updateShipment(id, shipmentDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), shipment, "Expedição atualizada com sucesso!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteShipment(@PathVariable Long id){
        this.shipmentService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), null, "Expedição deletada com sucesso!"));
    }
}
