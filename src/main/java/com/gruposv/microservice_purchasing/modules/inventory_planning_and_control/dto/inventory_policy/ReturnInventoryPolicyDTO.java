package com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.dto.inventory_policy;

import com.gruposv.microservice_purchasing.modules.product.dto.products.ReturnProductDTO;

// DTO para retornar um "InventoryPolicy" na resposta da API
public class ReturnInventoryPolicyDTO {

    private Long id;

    private ReturnProductDTO product;

    private Integer minStockLevel;

    private Integer maxStockLevel;

    private Integer reorderQuantity;

    private Integer leadTimeDays;

    private Integer safetyStock;

    public ReturnInventoryPolicyDTO() {
    }

    public ReturnInventoryPolicyDTO(Long id, ReturnProductDTO product, Integer minStockLevel, Integer maxStockLevel, Integer reorderQuantity, Integer leadTimeDays, Integer safetyStock) {
        this.id = id;
        this.product = product;
        this.minStockLevel = minStockLevel;
        this.maxStockLevel = maxStockLevel;
        this.reorderQuantity = reorderQuantity;
        this.leadTimeDays = leadTimeDays;
        this.safetyStock = safetyStock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReturnProductDTO getProduct() {
        return product;
    }

    public void setProduct(ReturnProductDTO product) {
        this.product = product;
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
