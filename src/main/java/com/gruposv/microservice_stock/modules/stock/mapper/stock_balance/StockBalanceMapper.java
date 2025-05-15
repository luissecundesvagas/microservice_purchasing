package com.gruposv.microservice_stock.modules.stock.mapper.stock_balance;

import com.gruposv.microservice_stock.modules.product.mapper.products.ProductMapper;
import com.gruposv.microservice_stock.modules.stock.dto.stock_balance.ReturnStockBalanceDTO;
import com.gruposv.microservice_stock.modules.stock.dto.stock_balance.StockBalanceDTO;
import com.gruposv.microservice_stock.modules.stock.entity.StockBalanceEntity;
import com.gruposv.microservice_stock.modules.stock.mapper.stock_locations.StockLocationsMapper;

public class StockBalanceMapper {

    public static ReturnStockBalanceDTO toDTO(StockBalanceEntity stockBalanceEntity) {
        return new ReturnStockBalanceDTO(stockBalanceEntity.getId(), ProductMapper.returnDTO(stockBalanceEntity.getProduct()), StockLocationsMapper.toDTO(stockBalanceEntity.getStockLocation()), stockBalanceEntity.getQuantityOnHand(), stockBalanceEntity.getQuantityReserved(), stockBalanceEntity.getMinimumStockLevel());
    }

    public static StockBalanceEntity toEntity(StockBalanceDTO stockBalanceDTO) {
        StockBalanceEntity stockBalance = new StockBalanceEntity();
        stockBalance.setQuantityOnHand(stockBalanceDTO.getQuantityOnHand());
        stockBalance.setQuantityReserved(stockBalanceDTO.getQuantityReserved());
        stockBalance.setMinimumStockLevel(stockBalanceDTO.getMinimumStockLevel());
        return stockBalance;
    }

    public static StockBalanceEntity toEntityUpdate(StockBalanceEntity stockBalanceEntity, StockBalanceDTO stockBalanceDTO) {
        if (stockBalanceDTO.getMinimumStockLevel() != null) {
            stockBalanceEntity.setMinimumStockLevel(stockBalanceDTO.getMinimumStockLevel());
        }
        return stockBalanceEntity;
    }

}
