package com.gruposv.microservice_purchasing.modules.stock.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Where;

import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.entity.PlanningSuggestionsEntity;
import com.gruposv.microservice_purchasing.modules.shipping.entity.ShipmentItemsEntity;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_stock_locations")
@Where(clause = "deleted_at IS NULL")
public class StockLocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;

    @Column(name = "location_code", nullable = false)
    private String locationCode;

    @Column(name = "location_name", nullable = false)
    private String locationName;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "stockLocation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockBalanceEntity> stockBalanceList;

    @OneToMany(mappedBy = "stockLocation", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<StockMovementsEntity> stockMovements;

    @OneToMany(mappedBy = "stockLocation", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<StockLotDetailsEntity> stockLotDetailsList;

    @OneToMany(mappedBy = "stockLocation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShipmentItemsEntity> shipmentItemsList;

    @OneToMany(mappedBy = "stockLocation", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PlanningSuggestionsEntity> planningSuggestionsList;

    public StockLocationEntity() {
    }

    public StockLocationEntity(Long id, String locationCode, String locationName, LocalDateTime deletedAt, List<StockBalanceEntity> stockBalanceList, List<StockMovementsEntity> stockMovements, List<StockLotDetailsEntity> stockLotDetailsList, List<ShipmentItemsEntity> shipmentItemsList, List<PlanningSuggestionsEntity> planningSuggestionsList) {
        this.id = id;
        this.locationCode = locationCode;
        this.locationName = locationName;
        this.deletedAt = deletedAt;
        this.stockBalanceList = stockBalanceList;
        this.stockMovements = stockMovements;
        this.stockLotDetailsList = stockLotDetailsList;
        this.shipmentItemsList = shipmentItemsList;
        this.planningSuggestionsList = planningSuggestionsList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public List<StockBalanceEntity> getStockBalanceList() {
        return stockBalanceList;
    }

    public void setStockBalanceList(List<StockBalanceEntity> stockBalanceList) {
        this.stockBalanceList = stockBalanceList;
    }

    public List<StockMovementsEntity> getStockMovements() {
        return stockMovements;
    }

    public void setStockMovements(List<StockMovementsEntity> stockMovements) {
        this.stockMovements = stockMovements;
    }

    public List<StockLotDetailsEntity> getStockLotDetailsList() {
        return stockLotDetailsList;
    }

    public void setStockLotDetailsList(List<StockLotDetailsEntity> stockLotDetailsList) {
        this.stockLotDetailsList = stockLotDetailsList;
    }

    public List<ShipmentItemsEntity> getShipmentItemsList() {
        return shipmentItemsList;
    }

    public void setShipmentItemsList(List<ShipmentItemsEntity> shipmentItemsList) {
        this.shipmentItemsList = shipmentItemsList;
    }

    public List<PlanningSuggestionsEntity> getPlanningSuggestionsList() {
        return planningSuggestionsList;
    }

    public void setPlanningSuggestionsList(List<PlanningSuggestionsEntity> planningSuggestionsList) {
        this.planningSuggestionsList = planningSuggestionsList;
    }

}
