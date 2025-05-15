package com.gruposv.microservice_purchasing.modules.stock.mapper.stock_movements;

import java.time.LocalDateTime;

import com.gruposv.microservice_purchasing.modules.product.mapper.products.ProductMapper;
import com.gruposv.microservice_purchasing.modules.stock.dto.stock_movements.RegisterStockMovementsDTO;
import com.gruposv.microservice_purchasing.modules.stock.dto.stock_movements.RegisterStockTransferDTO;
import com.gruposv.microservice_purchasing.modules.stock.dto.stock_movements.StockMovementDTO;
import com.gruposv.microservice_purchasing.modules.stock.entity.StockMovementsEntity;
import com.gruposv.microservice_purchasing.modules.stock.mapper.stock_locations.StockLocationsMapper;

public class StockMovementsMapper {

    public static StockMovementsEntity toCreateEntity(RegisterStockMovementsDTO createStockMovementDTO){
        StockMovementsEntity stockMovements = new StockMovementsEntity();
        stockMovements.setQuantity(createStockMovementDTO.getQuantity());
        stockMovements.setMovementDate(LocalDateTime.now());
        stockMovements.setReferenceId(createStockMovementDTO.getReferenceId());
        stockMovements.setRemarks(createStockMovementDTO.getRemarks());
        return stockMovements;
    }

    public static StockMovementsEntity toCreateTransferEntity(RegisterStockTransferDTO registerStockTransferDTO){
        StockMovementsEntity stockMovements = new StockMovementsEntity();
        stockMovements.setQuantity(registerStockTransferDTO.getQuantity());
        stockMovements.setMovementDate(LocalDateTime.now());
        stockMovements.setReferenceId(registerStockTransferDTO.getReferenceId());
        stockMovements.setRemarks(registerStockTransferDTO.getRemarks());
        return stockMovements;
    }

    public static StockMovementDTO toDTO(StockMovementsEntity stockMovements){
        StockMovementDTO stockMovementDTO = new StockMovementDTO();
        stockMovementDTO.setId(stockMovements.getId());
        stockMovementDTO.setMovementType(MovementTypeMapper.toDTO(stockMovements.getMovementType()));
        stockMovementDTO.setProduct(ProductMapper.returnDTO(stockMovements.getProduct()));
        stockMovementDTO.setStockLocation(StockLocationsMapper.toDTO(stockMovements.getStockLocation()));
        stockMovementDTO.setQuantity(stockMovements.getQuantity());
        stockMovementDTO.setMovementDate(stockMovements.getMovementDate());
        stockMovementDTO.setReferenceId(stockMovements.getReferenceId());
        stockMovementDTO.setRemarks(stockMovements.getRemarks());
        return stockMovementDTO;
    }

}
