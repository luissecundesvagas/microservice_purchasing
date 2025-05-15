package com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.dto.inventory_policy;

import jakarta.validation.constraints.NotNull;

// DTO para cadastro ou edição de um "InventoryPolicy"
public class InventoryPolicyDTO {

    @NotNull(message = "Para criar uma política de inventário, é necessário selecionar um produto.")
    private Long productId;

    @NotNull(message = "Para criar uma política de inventário, é necessário especificar o estoque mínimo.")
    private Integer minStockLevel;

    @NotNull(message = "Para criar uma política de inventário, é necessário especificar o estoque máximo.")
    private Integer maxStockLevel;

    @NotNull(message = "Para criar uma política de inventário, é necessário especificar uma quantidade sugerida para reabastecimento.")
    private Integer reorderQuantity;

    @NotNull(message = "Para criar uma política de inventário, é necessário especificar dias necessários para o reabastencimento.")
    private Integer leadTimeDays;

    @NotNull(message = "Para criar uma política de inventário, é necessário especificar uma quantidade extra de estoque.")
    private Integer safetyStock;

    public InventoryPolicyDTO() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getMinStockLevel() {
        return minStockLevel;
    }

    public void setMinStockLevel(Integer minStockLevel) {
        this.minStockLevel = minStockLevel;
    }

    public Integer getMaxStockLevel() {
        return maxStockLevel;
    }

    public void setMaxStockLevel(Integer maxStockLevel) {
        this.maxStockLevel = maxStockLevel;
    }

    public Integer getReorderQuantity() {
        return reorderQuantity;
    }

    public void setReorderQuantity(Integer reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
    }

    public Integer getLeadTimeDays() {
        return leadTimeDays;
    }

    public void setLeadTimeDays(Integer leadTimeDays) {
        this.leadTimeDays = leadTimeDays;
    }

    public Integer getSafetyStock() {
        return safetyStock;
    }

    public void setSafetyStock(Integer safetyStock) {
        this.safetyStock = safetyStock;
    }
}
