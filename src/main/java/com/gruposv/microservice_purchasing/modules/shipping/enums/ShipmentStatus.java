package com.gruposv.microservice_purchasing.modules.shipping.enums;

public enum ShipmentStatus {

    EM_SEPARACAO("Em separação"),
    DESPACHADO("Despachado"),
    EM_TRANSITO("Em trânsito"),
    ENTREGUE("Entregue"),
    PROBLEMAS_NA_ENTREGA("Problemas na entrega"),
    CANCELADO("Cancelado"),
    RETORNADO("Retornado"),
    EXTRAVIADO("Extraviado");

    private final String value;

    ShipmentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

}
