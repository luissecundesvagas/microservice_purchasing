package com.gruposv.microservice_stock.modules.product.enums;

public enum ProductStatus {
    ACTIVE("Ativo"),
    INACTIVE("Inativo");

    private final String description;

    ProductStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
