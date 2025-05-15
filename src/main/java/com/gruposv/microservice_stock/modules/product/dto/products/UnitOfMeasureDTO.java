package com.gruposv.microservice_stock.modules.product.dto.products;

import com.gruposv.microservice_stock.modules.product.enums.UnitOfMeasure;

public class UnitOfMeasureDTO {

    private final String name;
    private final String description;

    public UnitOfMeasureDTO(UnitOfMeasure unitOfMeasure) {
        this.name = unitOfMeasure.name();
        this.description = unitOfMeasure.getDescription();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
