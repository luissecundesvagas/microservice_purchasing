package com.gruposv.microservice_purchasing.modules.stock.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gruposv.microservice_purchasing.dto.ApiResponseDTO;
import com.gruposv.microservice_purchasing.modules.stock.dto.stock_balance.ListStockBalanceDTO;
import com.gruposv.microservice_purchasing.modules.stock.dto.stock_balance.ReturnStockBalanceDTO;
import com.gruposv.microservice_purchasing.modules.stock.dto.stock_balance.StockBalanceDTO;
import com.gruposv.microservice_purchasing.modules.stock.service.StockBalanceService;

@RestController
@RequestMapping("/stock/stock-balances")
public class StockBalanceController {

    private final StockBalanceService stockBalanceService;

    public StockBalanceController(StockBalanceService stockBalanceService) {
        this.stockBalanceService = stockBalanceService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<Page<ReturnStockBalanceDTO>>> findAllStockBalance(@ModelAttribute @Valid ListStockBalanceDTO listStockBalanceDTO){
        Page<ReturnStockBalanceDTO> stockBalances = this.stockBalanceService.findAllStockBalance(listStockBalanceDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), stockBalances, "Dados retornados com sucesso!"));
    }

    @GetMapping("/product/{productId}/location/{locationId}")
    public ResponseEntity<ApiResponseDTO<ReturnStockBalanceDTO>> findByProductIdAndLocationId(@PathVariable Long productId, @PathVariable Long locationId){
        ReturnStockBalanceDTO stockBalance = this.stockBalanceService.findByProductIdAndLocationId(productId, locationId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), stockBalance, "Dados retornados com sucesso!"));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponseDTO<Page<ReturnStockBalanceDTO>>> findAllByProductId(@PathVariable Long productId, @ModelAttribute @Valid ListStockBalanceDTO listStockBalanceDTO){
        Page<ReturnStockBalanceDTO> stockBalances = this.stockBalanceService.findAllByProductId(productId, listStockBalanceDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), stockBalances, "Dados retornados com sucesso!"));
    }

    @GetMapping("/product/{productId}/location/{locationId}/quantity-greater-equal-than/{quantity}")
    public ResponseEntity<ApiResponseDTO<ReturnStockBalanceDTO>> findByProductIdAndLocationIdAndQuantityOnHandGreaterThanEqual(
            @PathVariable Long productId,
            @PathVariable Long locationId,
            @PathVariable Integer quantity
    ){
        ReturnStockBalanceDTO stockBalance = this.stockBalanceService.findByProductIdAndLocationIdAndQuantityOnHandGreaterThanEqual(productId, locationId, quantity);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), stockBalance, "Dados retornados com sucesso!"));
    }

    @GetMapping("/product/{productId}/location/{locationId}/quantity-greater-than/{quantity}")
    public ResponseEntity<ApiResponseDTO<ReturnStockBalanceDTO>> findByProductIdAndLocationIdAndQuantityOnHandGreaterThan(
            @PathVariable Long productId,
            @PathVariable Long locationId,
            @PathVariable Integer quantity
    ){
        ReturnStockBalanceDTO stockBalance = this.stockBalanceService.findByProductIdAndLocationIdAndQuantityOnHandGreaterThan(productId, locationId, quantity);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), stockBalance, "Dados retornados com sucesso!"));
    }

    @GetMapping("/product/{productId}/location/{locationId}/quantity-less-equal-than/{quantity}")
    public ResponseEntity<ApiResponseDTO<ReturnStockBalanceDTO>> findByProductIdAndLocationIdAndQuantityOnHandLessThanOrEqualTo(
            @PathVariable Long productId,
            @PathVariable Long locationId,
            @PathVariable Integer quantity
    ){
        ReturnStockBalanceDTO stockBalance = this.stockBalanceService.findByProductIdAndLocationIdAndQuantityOnHandLessThanOrEqualTo(productId, locationId, quantity);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), stockBalance, "Dados retornados com sucesso!"));
    }

    @PatchMapping("/product/{productId}/location/{locationId}")
    public ResponseEntity<ApiResponseDTO<ReturnStockBalanceDTO>> updateStockBalance(
            @PathVariable("productId") Long productId,
            @PathVariable("locationId") Long locationId,
            @RequestBody StockBalanceDTO stockBalanceDTO
    ){
        stockBalanceDTO.setProductId(productId);
        stockBalanceDTO.setStockLocationId(locationId);
        ReturnStockBalanceDTO stockBalance = this.stockBalanceService.updateStockBalance(stockBalanceDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), stockBalance, "Balanço de estoque atualizado com sucesso!"));
    }

    @DeleteMapping("/product/{productId}/location/{locationId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteStockBalance(@PathVariable Long productId, @PathVariable Long locationId){
        this.stockBalanceService.deleteStockBalance(productId, locationId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), null, "Balanço de estoque deletado com sucesso!"));
    }
}
