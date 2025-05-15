package com.gruposv.microservice_stock.modules.stock.dto.stock_lot_details;

import jakarta.validation.constraints.NotNull;

public class ListStockLotDetailsDTO {
    @NotNull(message = "É necessário informar o número da página.")
    int page;

    @NotNull(message = "É necessário informar o tamanho da página.")
    int size;

    public ListStockLotDetailsDTO(int page, int size) {
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
