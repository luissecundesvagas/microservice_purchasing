package com.gruposv.microservice_stock.modules.product.mapper.products;

import com.gruposv.microservice_stock.modules.product.dto.products.UnitOfMeasureDTO;
import com.gruposv.microservice_stock.modules.product.enums.UnitOfMeasure;

public class UnitOfMeasureMapper {

    public static UnitOfMeasureDTO toDTO (UnitOfMeasure unitOfMeasure){
        return new UnitOfMeasureDTO(unitOfMeasure);
    }

}
