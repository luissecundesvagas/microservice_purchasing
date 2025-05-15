package com.gruposv.microservice_purchasing.modules.stock.mapper.stock_locations;

import com.gruposv.microservice_purchasing.modules.stock.dto.stock_locations.StockLocationDTO;
import com.gruposv.microservice_purchasing.modules.stock.entity.StockLocationEntity;

public class StockLocationsMapper {

    public static StockLocationDTO toDTO(StockLocationEntity stockLocation) {
        return new StockLocationDTO(stockLocation.getId(), stockLocation.getLocationCode(), stockLocation.getLocationName());
    }

    public static StockLocationEntity toEntity(StockLocationDTO stockLocationDTO) {
        StockLocationEntity stockLocation = new StockLocationEntity();
        stockLocation.setLocationCode(stockLocationDTO.getLocationCode());
        stockLocation.setLocationName(stockLocationDTO.getLocationName());
        return stockLocation;
    }

    public static StockLocationEntity toEntityUpdate(StockLocationDTO stockLocationDTO, StockLocationEntity stockLocation) {

        if(stockLocationDTO.getLocationCode() != null && !stockLocationDTO.getLocationCode().isEmpty()){
            stockLocation.setLocationCode(stockLocationDTO.getLocationCode());
        }

        if(stockLocationDTO.getLocationName() != null && !stockLocationDTO.getLocationName().isEmpty()){
            stockLocation.setLocationName(stockLocationDTO.getLocationName());
        }

        return stockLocation;
    }

}
