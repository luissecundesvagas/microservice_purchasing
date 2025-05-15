package com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.dto.inventory_planning_run;

import java.time.LocalDateTime;
import java.util.List;

import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.enums.TriggerType;

public class ReturnInventoryPlanningRunDTO {

    private Long id;
    private LocalDateTime runDate;
    private String notes;
    private TriggerType triggerType;
    private List<ReturnPlanningSuggestionDTO> planningSuggestionsList;

    public ReturnInventoryPlanningRunDTO() {
    }

    public ReturnInventoryPlanningRunDTO(Long id, LocalDateTime runDate, String notes, TriggerType triggerType, List<ReturnPlanningSuggestionDTO> planningSuggestionsList) {
        this.id = id;
        this.runDate = runDate;
        this.notes = notes;
        this.triggerType = triggerType;
        this.planningSuggestionsList = planningSuggestionsList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getRunDate() {
        return runDate;
    }

    public void setRunDate(LocalDateTime runDate) {
        this.runDate = runDate;
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

    public List<ReturnPlanningSuggestionDTO> getPlanningSuggestionsList() {
        return planningSuggestionsList;
    }

    public void setPlanningSuggestionsList(List<ReturnPlanningSuggestionDTO> planningSuggestionsList) {
        this.planningSuggestionsList = planningSuggestionsList;
    }
}
