package com.gruposv.microservice_stock.modules.product.dto.products;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class DeleteListProductsDTO {

    @NotEmpty(message = "A lista de IDs não pode estar vazia.")
    @Size(max = 100, message = "A lista de IDs não pode ter mais de 100 elementos.")
    private List<Long> ids;

    public DeleteListProductsDTO() {
    }

    public DeleteListProductsDTO(List<Long> ids) {
        this.ids = ids;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
