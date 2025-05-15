package com.gruposv.microservice_purchasing.modules.product.dto.products;

import com.gruposv.microservice_purchasing.modules.product.enums.ProductType;

public class ProductTypeDTO {

    private final String name;
    private final String number;
    private final String description;

    public ProductTypeDTO(ProductType productType) {
        this.name = productType.name();
        this.number = productType.getNumber();
        this.description = productType.getDescription();
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }
}
