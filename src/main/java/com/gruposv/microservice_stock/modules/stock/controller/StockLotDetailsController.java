package com.gruposv.microservice_stock.modules.stock.controller;

import com.gruposv.microservice_stock.dto.ApiResponseDTO;
import com.gruposv.microservice_stock.modules.stock.dto.stock_lot_details.ListStockLotDetailsDTO;
import com.gruposv.microservice_stock.modules.stock.dto.stock_lot_details.ReturnStockLotDetailsDTO;
import com.gruposv.microservice_stock.modules.stock.dto.stock_lot_details.StockLotDetailsDTO;
import com.gruposv.microservice_stock.modules.stock.service.StockLotDetailsService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/stock/stock-lot-details")
public class StockLotDetailsController {

    private final StockLotDetailsService stockLotDetailsService;

    public StockLotDetailsController(StockLotDetailsService stockLotDetailsService) {
        this.stockLotDetailsService = stockLotDetailsService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<Page<ReturnStockLotDetailsDTO>>> findAllStockLotDetails(@ModelAttribute @Valid ListStockLotDetailsDTO listStockLotDetailsDTO){
        Page<ReturnStockLotDetailsDTO> stockLotDetails = this.stockLotDetailsService.findAllStockLotDetails(listStockLotDetailsDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), stockLotDetails, "Dados retornados com sucesso!"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ReturnStockLotDetailsDTO>> findStockLotDetailsById(@PathVariable Long id){
        ReturnStockLotDetailsDTO stockLotDetails = this.stockLotDetailsService.findStockLotDetailsById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), stockLotDetails, "Dados retornados com sucesso!"));
    }

    @GetMapping("/product/{productId}/lot/{lotNumber}/location/{locationId}")
    public ResponseEntity<ApiResponseDTO<ReturnStockLotDetailsDTO>> findByProductIdAndLotNumberAndLocationId(
            @PathVariable Long productId,
            @PathVariable String lotNumber,
            @PathVariable Long locationId
    ){
        ReturnStockLotDetailsDTO stockLotDetails = this.stockLotDetailsService.findByProductIdAndLotNumberAndLocationId(productId, lotNumber, locationId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), stockLotDetails, "Dados retornados com sucesso!"));
    }

    @GetMapping("/product/{productId}/location/{locationId}")
    public ResponseEntity<ApiResponseDTO<Page<ReturnStockLotDetailsDTO>>> findAllByProductIdAndLocationId(
            @PathVariable Long productId,
            @PathVariable Long locationId,
            @ModelAttribute @Valid ListStockLotDetailsDTO listStockLotDetailsDTO
    ){
        Page<ReturnStockLotDetailsDTO> stockLotDetails = this.stockLotDetailsService.findAllByProductIdAndLocationId(productId, null, locationId, listStockLotDetailsDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), stockLotDetails, "Dados retornados com sucesso!"));
    }

    @GetMapping("/product/{productId}/location/{locationId}/expiration-before/{expirationDate}")
    public ResponseEntity<ApiResponseDTO<Page<ReturnStockLotDetailsDTO>>> findByProductIdAndLocationIdAndExpirationDateBefore(
            @PathVariable Long productId,
            @PathVariable Long locationId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expirationDate,
            @ModelAttribute @Valid ListStockLotDetailsDTO listStockLotDetailsDTO
    ){
        Page<ReturnStockLotDetailsDTO> stockLotDetails = this.stockLotDetailsService.findByProductIdAndLocationIdAndExpirationDateBefore(productId, locationId, expirationDate, listStockLotDetailsDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), stockLotDetails, "Dados retornados com sucesso!"));
    }

    @GetMapping("/product/{productId}/location/{locationId}/quantity-greater-than/{quantity}")
    public ResponseEntity<ApiResponseDTO<Page<ReturnStockLotDetailsDTO>>> findByProductIdAndLocationIdAndQuantityGreaterThan(
            @PathVariable Long productId,
            @PathVariable Long locationId,
            @PathVariable Integer quantity,
            @ModelAttribute @Valid ListStockLotDetailsDTO listStockLotDetailsDTO
    ){
        Page<ReturnStockLotDetailsDTO> stockLotDetails = this.stockLotDetailsService.findByProductIdAndLocationIdAndQuantityGreaterThan(productId, locationId, quantity, listStockLotDetailsDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), stockLotDetails, "Dados retornados com sucesso!"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ReturnStockLotDetailsDTO>> updateStockLotDetails(
            @PathVariable Long id,
            @RequestBody StockLotDetailsDTO stockLotDetailsDTO
    ){
        ReturnStockLotDetailsDTO stockLotDetails = this.stockLotDetailsService.updateStockLotDetails(id, stockLotDetailsDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), stockLotDetails, "Detalhe de lote atualizado com sucesso!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteStockLotDetails(@PathVariable Long id){
        this.stockLotDetailsService.DeleteStockLotDetails(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), null, "Detalhe de lote deletado com sucesso!"));
    }
}
