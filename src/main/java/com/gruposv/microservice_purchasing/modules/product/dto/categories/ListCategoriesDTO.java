package com.gruposv.microservice_purchasing.modules.product.dto.categories;

import jakarta.validation.constraints.NotNull;

public class ListCategoriesDTO {

    @NotNull(message = "É ncessário informar o número da página.")
    int page;

    @NotNull(message = "É ncessário informar o tamanho da página.")
    int size;

    public ListCategoriesDTO() {
    }

    public ListCategoriesDTO(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
