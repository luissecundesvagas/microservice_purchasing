package com.gruposv.microservice_stock.modules.inventory_planning_and_control.enums;

public enum TriggerType {
    MANUAL("Manual"),
    AGENDADO("Agendado"),
    ESTOQUE_BAIXO("Estoque baixo"),
    PONTO_DE_RESSUPRIMENTO("Ponto de ressuprimento"),
    FIM_DIA("Fim dia"),
    FIM_SEMANA("Fim semana"),
    FIM_MES("Fim mês"),
    NOVA_VENDA_SIGNIFICATIVA("Nova venda significativa"),
    PREVISAO_ATUALIZADA("Previsao atualizada"),
    PARAMETRO_ALTERADO("Parametro alterado");

    private final String value;

    TriggerType(String value) {
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
