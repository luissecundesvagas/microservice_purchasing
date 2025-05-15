package com.gruposv.microservice_purchasing.modules.stock.dto.stock_movements;

import jakarta.validation.constraints.NotNull;

public class ListStockMovementDTO {
    @NotNull(message = "É necessário informar o número da página.")
    int page;

    @NotNull(message = "É necessário informar o tamanho da página.")
    int size;

    public ListStockMovementDTO(int page, int size) {
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
