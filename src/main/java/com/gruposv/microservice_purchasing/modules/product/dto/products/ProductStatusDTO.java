package com.gruposv.microservice_purchasing.modules.product.dto.products;

import com.gruposv.microservice_purchasing.modules.product.enums.ProductStatus;

public class ProductStatusDTO {

    private final String name;
    private final String description;

    public ProductStatusDTO(ProductStatus productStatus) {
        this.name = productStatus.name();
        this.description = productStatus.getDescription();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
