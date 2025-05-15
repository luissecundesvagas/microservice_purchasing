package com.gruposv.microservice_stock.modules.stock.mapper.stock_movements;

import com.gruposv.microservice_stock.modules.stock.dto.stock_movements.MovementTypeDTO;
import com.gruposv.microservice_stock.modules.stock.enums.MovementType;

public class MovementTypeMapper {

    public static MovementTypeDTO toDTO(MovementType movementType){
        return new MovementTypeDTO(movementType);
    }

}
