package com.gruposv.microservice_purchasing.modules.stock.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gruposv.microservice_purchasing.dto.ApiResponseDTO;
import com.gruposv.microservice_purchasing.modules.stock.dto.stock_movements.ListStockMovementDTO;
import com.gruposv.microservice_purchasing.modules.stock.dto.stock_movements.RegisterStockMovementsDTO;
import com.gruposv.microservice_purchasing.modules.stock.dto.stock_movements.RegisterStockTransferDTO;
import com.gruposv.microservice_purchasing.modules.stock.dto.stock_movements.StockMovementDTO;
import com.gruposv.microservice_purchasing.modules.stock.enums.MovementType;
import com.gruposv.microservice_purchasing.modules.stock.service.StockMovementService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/stock/stock-movements")
public class StockMovementController {

    private final StockMovementService stockMovementService;

    public StockMovementController(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    @PostMapping("/inbound")
    public ResponseEntity<ApiResponseDTO<StockMovementDTO>> registerStockInbound(@RequestBody @Valid RegisterStockMovementsDTO registerStockMovementsDTO) {
        StockMovementDTO stockMovement = this.stockMovementService.registerStockInbound(registerStockMovementsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>("success", HttpStatus.CREATED.value(), stockMovement, "Entrada de estoque registrada com sucesso!"));
    }

    @PostMapping("/inbound/lot")
    public ResponseEntity<ApiResponseDTO<StockMovementDTO>> registerStockInboundWithLot(@RequestBody @Valid RegisterStockMovementsDTO registerStockMovementsDTO) {
        StockMovementDTO stockMovement = this.stockMovementService.registerStockInboundWithLot(registerStockMovementsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>("success", HttpStatus.CREATED.value(), stockMovement, "Entrada de estoque com lote registrada com sucesso!"));
    }

    @PostMapping("/outbound")
    public ResponseEntity<ApiResponseDTO<StockMovementDTO>> registerStockOutbound(@RequestBody @Valid RegisterStockMovementsDTO registerStockMovementsDTO) {
        StockMovementDTO stockMovement = this.stockMovementService.registerStockOutbound(registerStockMovementsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>("success", HttpStatus.CREATED.value(), stockMovement, "Saída de estoque registrada com sucesso!"));
    }

    @PostMapping("/outbound/lot")
    public ResponseEntity<ApiResponseDTO<StockMovementDTO>> registerStockOutboundWithLot(@RequestBody @Valid RegisterStockMovementsDTO registerStockMovementsDTO) {
        StockMovementDTO stockMovement = this.stockMovementService.registerStockOutboundWithLot(registerStockMovementsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>("success", HttpStatus.CREATED.value(), stockMovement, "Saída de estoque com lote registrada com sucesso!"));
    }

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponseDTO<List<StockMovementDTO>>> registerStockTransfer(@RequestBody @Valid RegisterStockTransferDTO registerStockTransferDTO) {
        List<StockMovementDTO> stockMovements = this.stockMovementService.registerStockTransfer(registerStockTransferDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>("success", HttpStatus.CREATED.value(), stockMovements, "Transferência de estoque registrada com sucesso!"));
    }

    @PostMapping("/transfer/lot")
    public ResponseEntity<ApiResponseDTO<List<StockMovementDTO>>> registerStockTransferWithLot(@RequestBody @Valid RegisterStockTransferDTO registerStockTransferDTO) {
        List<StockMovementDTO> stockMovements = this.stockMovementService.registerStockTransferWithLot(registerStockTransferDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>("success", HttpStatus.CREATED.value(), stockMovements, "Transferência de estoque com lote registrada com sucesso!"));
    }

    @PostMapping("/adjustment")
    public ResponseEntity<ApiResponseDTO<StockMovementDTO>> registerStockAdjustment(@RequestBody @Valid RegisterStockMovementsDTO registerStockMovementsDTO) {
        StockMovementDTO stockMovement = this.stockMovementService.registerStockAdjustment(registerStockMovementsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>("success", HttpStatus.CREATED.value(), stockMovement, "Ajuste de estoque registrado com sucesso!"));
    }

    @PostMapping("/adjustment/lot")
    public ResponseEntity<ApiResponseDTO<StockMovementDTO>> registerStockAdjustmentWithLot(@RequestBody @Valid RegisterStockMovementsDTO registerStockMovementsDTO) {
        StockMovementDTO stockMovement = this.stockMovementService.registerStockAdjustmentWithLot(registerStockMovementsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>("success", HttpStatus.CREATED.value(), stockMovement, "Ajuste de estoque com lote registrado com sucesso!"));
    }

    @PostMapping("/reservation")
    public ResponseEntity<ApiResponseDTO<StockMovementDTO>> registerStockReservation(@RequestBody @Valid RegisterStockMovementsDTO registerStockMovementsDTO) {
        StockMovementDTO stockMovement = this.stockMovementService.registerStockReservation(registerStockMovementsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>("success", HttpStatus.CREATED.value(), stockMovement, "Reserva de estoque registrada com sucesso!"));
    }

    @PostMapping("/reservation/lot")
    public ResponseEntity<ApiResponseDTO<StockMovementDTO>> registerStockReservationWithLot(@RequestBody @Valid RegisterStockMovementsDTO registerStockMovementsDTO) {
        StockMovementDTO stockMovement = this.stockMovementService.registerStockReservationWithLot(registerStockMovementsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>("success", HttpStatus.CREATED.value(), stockMovement, "Reserva de estoque com lote registrada com sucesso!"));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<Page<StockMovementDTO>>> findAllStockMovements(@ModelAttribute @Valid ListStockMovementDTO listStockMovementDTO) {
        Page<StockMovementDTO> stockMovements = this.stockMovementService.findAllStockMovements(listStockMovementDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), stockMovements, "Dados retornados com sucesso!"));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponseDTO<Page<StockMovementDTO>>> findAllByProductIdOrderByMovementDateDesc(
            @PathVariable Long productId,
            @ModelAttribute @Valid ListStockMovementDTO listStockMovementDTO
    ) {
        Page<StockMovementDTO> stockMovements = this.stockMovementService.findAllByProductIdOrderByMovementDateDesc(productId, listStockMovementDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), stockMovements, "Dados retornados com sucesso!"));
    }

    @GetMapping("/location/{locationId}")
    public ResponseEntity<ApiResponseDTO<Page<StockMovementDTO>>> findAllByLocationIdOrderByMovementDateDesc(
            @PathVariable Long locationId,
            @ModelAttribute @Valid ListStockMovementDTO listStockMovementDTO
    ) {
        Page<StockMovementDTO> stockMovements = this.stockMovementService.findAllByLocationIdOrderByMovementDateDesc(locationId, listStockMovementDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), stockMovements, "Dados retornados com sucesso!"));
    }

    @GetMapping("/product/{productId}/location/{locationId}")
    public ResponseEntity<ApiResponseDTO<Page<StockMovementDTO>>> findAllByProductIdAndLocationIdOrderByMovementDateDesc(
            @PathVariable Long productId,
            @PathVariable Long locationId,
            @ModelAttribute @Valid ListStockMovementDTO listStockMovementDTO
    ) {
        Page<StockMovementDTO> stockMovements = this.stockMovementService.findAllByProductIdAndLocationIdOrderByMovementDateDesc(productId, locationId, listStockMovementDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), stockMovements, "Dados retornados com sucesso!"));
    }

    @GetMapping("/type/{movementType}/start-date/{startDate}/end-date/{endDate}")
    public ResponseEntity<ApiResponseDTO<Page<StockMovementDTO>>> findAllByMovementTypeAndMovementDateBetween(
            @PathVariable MovementType movementType,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @ModelAttribute @Valid ListStockMovementDTO listStockMovementDTO
    ) {
        Page<StockMovementDTO> stockMovements = this.stockMovementService.findAllByMovementTypeAndMovementDateBetween(movementType, startDate, endDate, listStockMovementDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), stockMovements, "Dados retornados com sucesso!"));
    }
}
