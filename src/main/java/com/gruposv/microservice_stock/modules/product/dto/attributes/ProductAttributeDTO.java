package com.gruposv.microservice_stock.modules.product.dto.attributes;

import jakarta.validation.constraints.NotBlank;

public class ProductAttributeDTO {

    private Long id;

    @NotBlank(message = "O atributo deve possuir um nome.")
    private String attributeName;

    @NotBlank(message = "O atributo deve possuir um valor.")
    private String attributeValue;

    public ProductAttributeDTO() {
    }

    public ProductAttributeDTO(Long id, String attributeName, String attributeValue) {
        this.id = id;
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }
}
