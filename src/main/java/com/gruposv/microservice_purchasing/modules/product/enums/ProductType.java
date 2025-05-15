package com.gruposv.microservice_purchasing.modules.product.enums;

public enum ProductType {
    MERCADORIA_PARA_REVENDA("00", "00: Mercadoria para Revenda"),
    MATERIA_PRIMA("01","01: Matéria-Prima"),
    EMBALAGEM("02", "02: Embalagem"),
    PRODUTO_EM_PROCESSO("03","03: Produto em Processo"),
    PRODUTO_ACABADO("04","04: Produto Acabado"),
    SUBPRODUTO("05","05: Subproduto"),
    PRODUTO_INTERMEDIARIO("06","06: Produto Intermediário"),
    MATERIAL_DE_USO_E_CONSUMO("07","07: Material de Uso e Consumo"),
    ATIVO_IMOBILIZADO("08","08: Ativo Imobilizado"),
    SERVICOS("09", "09: Serviços"),
    OUTROS_INSUMOS("10","10: Outros insumos"),
    OUTRAS("99", "99: Outras");

    private final String number;
    private final String description;

    ProductType(String number, String description) {
        this.number = number;
        this.description = description;
    }

    public String getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }
}