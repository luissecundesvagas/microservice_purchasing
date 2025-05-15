package com.gruposv.microservice_purchasing.modules.product.enums;

public enum UnitOfMeasure {
    UNIDADE("Unidade"),
    KILOGRAMA("Kilograma"),
    LITRO("Litro"),
    METRO("Metro"),
    CENTIMETRO("Centímetro"),
    MILIMETRO("Milímetro"),
    CAIXA("Caixa"),
    PACOTE("Pacote"),
    GRAU("Grau"),
    TONELADA("Tonelada"),
    METRO_QUADRADO("Metro Quadrado"),
    LITRO_QUADRADO("Litro Quadrado"),
    MILIGRAMAS("Miligramas"),
    GRAMA("Grama"),
    LITRO_GALÃO("Litro (Galão)"),
    QUILO("quilo"),
    CADA("Cada");

    private final String description;

    UnitOfMeasure(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

