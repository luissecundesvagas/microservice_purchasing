package com.gruposv.microservice_purchasing.modules.stock.dto.stock_locations;

import jakarta.validation.constraints.NotEmpty;

public class StockLocationDTO {

    private Long id;

    @NotEmpty(message = "Para o cadastro de um local de estoque, é necessário informar o código do local.")
    private String locationCode;

    @NotEmpty(message = "Para o cadastro de um local de estoque, é necessário informar o nome do local.")
    private String locationName;

    public StockLocationDTO() {
    }

    public StockLocationDTO(Long id, String locationCode, String locationName) {
        this.id = id;
        this.locationCode = locationCode;
        this.locationName = locationName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
