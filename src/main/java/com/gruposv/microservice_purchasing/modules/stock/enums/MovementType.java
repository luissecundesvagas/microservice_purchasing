package com.gruposv.microservice_purchasing.modules.stock.enums;

// Entrada, saída, transferência e ajuste
public enum MovementType {
    ENTRADA( 1, "Entrada"),
    SAIDA( 2, "Saída"),
    RESERVA( 3, "Reserva"),
    TRANSFERENCIA( 4, "Transferência"),
    AJUSTE( 5, "Ajuste");

    private final int movementTypeId;
    private final String description;

    MovementType(int movementTypeId, String description) {
        this.movementTypeId = movementTypeId;
        this.description = description;
    }

    public int getMovementTypeId() {
        return movementTypeId;
    }

    public String getDescription() {
        return description;
    }

}
