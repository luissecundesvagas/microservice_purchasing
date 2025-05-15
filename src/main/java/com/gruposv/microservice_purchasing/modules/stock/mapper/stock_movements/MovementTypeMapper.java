package com.gruposv.microservice_purchasing.modules.stock.mapper.stock_movements;

import com.gruposv.microservice_purchasing.modules.stock.dto.stock_movements.MovementTypeDTO;
import com.gruposv.microservice_purchasing.modules.stock.enums.MovementType;

public class MovementTypeMapper {

    public static MovementTypeDTO toDTO(MovementType movementType){
        return new MovementTypeDTO(movementType);
    }

}
