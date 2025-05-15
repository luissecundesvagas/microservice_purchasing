package com.gruposv.microservice_stock.modules.stock.dto.stock_movements;

import com.gruposv.microservice_stock.modules.stock.enums.MovementType;

public class MovementTypeDTO {

    private int movementTypeId;
    private String name;
    private String description;

    public MovementTypeDTO(MovementType movementType) {
        this.movementTypeId = movementType.getMovementTypeId();
        this.name = movementType.name();
        this.description = movementType.getDescription();
    }

    public int getMovementTypeId() {
        return movementTypeId;
    }

    public void setMovementTypeId(int movementTypeId) {
        this.movementTypeId = movementTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
