package com.gruposv.microservice_stock.modules.inventory_planning_and_control.dto.inventory_planning_run;

public class PlanningSuggestionDTO {

    private Long productId;
    private Integer suggestedQuantity;
    private String reason;

    public PlanningSuggestionDTO() {
    }

    public PlanningSuggestionDTO(Long productId, Integer suggestedQuantity, String reason) {
        this.productId = productId;
        this.suggestedQuantity = suggestedQuantity;
        this.reason = reason;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getSuggestedQuantity() {
        return suggestedQuantity;
    }

    public void setSuggestedQuantity(Integer suggestedQuantity) {
        this.suggestedQuantity = suggestedQuantity;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
