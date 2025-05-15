package com.gruposv.microservice_stock.modules.stock.mapper.stock_lot_details;

import com.gruposv.microservice_stock.modules.product.mapper.products.ProductMapper;
import com.gruposv.microservice_stock.modules.stock.dto.stock_lot_details.StockLotDetailsDTO;
import com.gruposv.microservice_stock.modules.stock.dto.stock_lot_details.ReturnStockLotDetailsDTO;
import com.gruposv.microservice_stock.modules.stock.entity.StockLotDetailsEntity;
import com.gruposv.microservice_stock.modules.stock.mapper.stock_locations.StockLocationsMapper;

public class StockLotDetailsMapper {

    public static StockLotDetailsEntity toCreateEntity(StockLotDetailsDTO stockLotDetailsDTO){
        StockLotDetailsEntity stockLotDetails = new StockLotDetailsEntity();
        stockLotDetails.setLotNumber(stockLotDetailsDTO.getLotNumber());
        stockLotDetails.setExpirationDate(stockLotDetailsDTO.getExpirationDate());
        stockLotDetails.setQuantity(stockLotDetailsDTO.getQuantity());
        return stockLotDetails;
    }

    public static StockLotDetailsEntity toUpdatedEntity(StockLotDetailsDTO stockLotDetailsDTO, StockLotDetailsEntity stockLotDetailsEntity){

        if(stockLotDetailsDTO.getLotNumber() != null && stockLotDetailsDTO.getLotNumber().isEmpty()){
            stockLotDetailsEntity.setLotNumber(stockLotDetailsDTO.getLotNumber());
        }

        if(stockLotDetailsDTO.getExpirationDate() != null){
            stockLotDetailsEntity.setExpirationDate(stockLotDetailsDTO.getExpirationDate());
        }

        return stockLotDetailsEntity;
    }

    public static ReturnStockLotDetailsDTO toDTO(StockLotDetailsEntity stockLotDetails){
        ReturnStockLotDetailsDTO returnStockLotDetailsDTO = new ReturnStockLotDetailsDTO();
        returnStockLotDetailsDTO.setId(stockLotDetails.getId());
        returnStockLotDetailsDTO.setProduct(ProductMapper.returnDTO(stockLotDetails.getProduct()));
        returnStockLotDetailsDTO.setStockLocation(StockLocationsMapper.toDTO(stockLotDetails.getStockLocation()));
        returnStockLotDetailsDTO.setLotNumber(stockLotDetails.getLotNumber());
        returnStockLotDetailsDTO.setExpirationDate(stockLotDetails.getExpirationDate());
        returnStockLotDetailsDTO.setQuantity(stockLotDetails.getQuantity());
        return returnStockLotDetailsDTO;
    }

}
