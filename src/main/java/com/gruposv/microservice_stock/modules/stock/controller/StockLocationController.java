package com.gruposv.microservice_stock.modules.stock.controller;

import com.gruposv.microservice_stock.dto.ApiResponseDTO;
import com.gruposv.microservice_stock.modules.stock.dto.stock_locations.ListStockLocationsDTO;
import com.gruposv.microservice_stock.modules.stock.dto.stock_locations.StockLocationDTO;
import com.gruposv.microservice_stock.modules.stock.service.StockLocationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock/stock-locations")
public class StockLocationController {

    private final StockLocationService stockLocationService;

    public StockLocationController(StockLocationService stockLocationService) {
        this.stockLocationService = stockLocationService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<StockLocationDTO>> createNewStockLocation(@RequestBody @Valid StockLocationDTO stockLocationDTO){
        StockLocationDTO stockLocation = this.stockLocationService.createNewStockLocation(stockLocationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>("success", HttpStatus.CREATED.value(), stockLocation, "Local de estoque criado com sucesso!"));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<Page<StockLocationDTO>>> findAllStockLocations(@ModelAttribute @Valid ListStockLocationsDTO locationsDTO){
        Page<StockLocationDTO> locations = this.stockLocationService.findAllStockLocations(locationsDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), locations, "Dados retornados com sucesso!"));
    }

    @GetMapping("/location-code/{locationCode}")
    public ResponseEntity<ApiResponseDTO<StockLocationDTO>> findByLocationCode(@PathVariable("locationCode") String locationCode){
        StockLocationDTO location = this.stockLocationService.findByLocationCode(locationCode);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), location, "Dados retornados com sucesso!"));
    }

    @GetMapping("/location-name/{locationName}")
    public ResponseEntity<ApiResponseDTO<StockLocationDTO>> findByLocationName(@PathVariable("locationName") String locationName){
        StockLocationDTO location = this.stockLocationService.findByLocationName(locationName);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), location, "Dados retornados com sucesso!"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<StockLocationDTO>> updateStockLocations(@PathVariable("id") Long locationId, @RequestBody StockLocationDTO stockLocationDTO){
        StockLocationDTO stockLocation = this.stockLocationService.updateStockLocations(locationId, stockLocationDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), stockLocation, "Local de estoque editado com sucesso!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteStockLocation(@PathVariable("id") Long id){
        this.stockLocationService.deleteStockLocation(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponseDTO<>("success", HttpStatus.NO_CONTENT.value(), null, "Local de estoque deletado com sucesso!"));
    }
}
