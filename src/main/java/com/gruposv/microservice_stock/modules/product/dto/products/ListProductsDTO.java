package com.gruposv.microservice_stock.modules.product.dto.products;

import jakarta.validation.constraints.NotNull;

public class ListProductsDTO {
    @NotNull(message = "É necessário informar o número da página.")
    int page;

    @NotNull(message = "É necessário informar o tamanho da página.")
    int size;

    public ListProductsDTO(int page, int size) {
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
