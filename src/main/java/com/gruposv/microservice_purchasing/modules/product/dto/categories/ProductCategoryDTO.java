package com.gruposv.microservice_purchasing.modules.product.dto.categories;

import jakarta.validation.constraints.NotBlank;

public class ProductCategoryDTO {

    private Long id;

    @NotBlank(message = "A categoria deve possuir um nome.")
    private String name;

    public ProductCategoryDTO() {
    }

    public ProductCategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
