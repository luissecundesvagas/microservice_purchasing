package com.gruposv.microservice_stock.modules.inventory_planning_and_control.entity;

import com.gruposv.microservice_stock.modules.inventory_planning_and_control.enums.TriggerType;
import jakarta.persistence.*;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_inventory_planning_runs")
@Where(clause = "deleted_at IS NULL")
public class InventoryPlanningRunsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_planning_runs_id")
    private Long id;

    @Column(name = "run_date")
    private LocalDateTime runDate;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(name = "trigger_type")
    private TriggerType triggerType;

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "inventoryPlanningRun", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PlanningSuggestionsEntity> planningSuggestionsList;

    public InventoryPlanningRunsEntity() {
    }

    public InventoryPlanningRunsEntity(Long id, LocalDateTime runDate, String notes, TriggerType triggerType, List<PlanningSuggestionsEntity> planningSuggestionsList) {
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

    public List<PlanningSuggestionsEntity> getPlanningSuggestionsList() {
        return planningSuggestionsList;
    }

    public void setPlanningSuggestionsList(List<PlanningSuggestionsEntity> planningSuggestionsList) {
        this.planningSuggestionsList = planningSuggestionsList;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
