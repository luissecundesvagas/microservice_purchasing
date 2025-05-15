package com.gruposv.microservice_purchasing.modules.product.mapper.products;

import com.gruposv.microservice_purchasing.modules.product.dto.products.UnitOfMeasureDTO;
import com.gruposv.microservice_purchasing.modules.product.enums.UnitOfMeasure;

public class UnitOfMeasureMapper {

    public static UnitOfMeasureDTO toDTO (UnitOfMeasure unitOfMeasure){
        return new UnitOfMeasureDTO(unitOfMeasure);
    }

}
