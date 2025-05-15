package com.gruposv.microservice_stock.modules.inventory_planning_and_control.dto.inventory_planning_run;

import com.gruposv.microservice_stock.modules.inventory_planning_and_control.enums.TriggerType;

public class InventoryPlanningRunDTO {

    private String notes;
    private TriggerType triggerType;

    public InventoryPlanningRunDTO() {
    }

    public InventoryPlanningRunDTO(String notes, TriggerType triggerType) {
        this.notes = notes;
        this.triggerType = triggerType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public TriggerType getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(TriggerType triggerType) {
        this.triggerType = triggerType;
    }
}
