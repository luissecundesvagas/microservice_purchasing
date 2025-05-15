package com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gruposv.microservice_purchasing.dto.ApiResponseDTO;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.dto.inventory_planning_run.InventoryPlanningRunDTO;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.dto.inventory_planning_run.PlanningSuggestionDTO;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.dto.inventory_planning_run.ReturnInventoryPlanningRunDTO;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.dto.inventory_planning_run.ReturnPlanningSuggestionDTO;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.service.InventoryPlanningService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stock/inventory-planning")
public class InventoryPlanningController {

    private final InventoryPlanningService inventoryPlanningService;

    public InventoryPlanningController(InventoryPlanningService inventoryPlanningService) {
        this.inventoryPlanningService = inventoryPlanningService;
    }

    @PostMapping("/runs")
    public ResponseEntity<ApiResponseDTO<ReturnInventoryPlanningRunDTO>> startPlanningExecution(@RequestBody @Valid InventoryPlanningRunDTO runDTO){
        ReturnInventoryPlanningRunDTO planningRun = this.inventoryPlanningService.startPlanningExecution(runDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>("success", HttpStatus.CREATED.value(), planningRun, "Execução de planejamento iniciada com sucesso!"));
    }

    @PostMapping("/suggestions/low-stock")
    public ResponseEntity<ApiResponseDTO<List<ReturnPlanningSuggestionDTO>>> generateLowStockPlanningSuggestions(){
        List<ReturnPlanningSuggestionDTO> suggestions = this.inventoryPlanningService.generateLowStockPlanningSuggestions();
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>("success", HttpStatus.CREATED.value(), suggestions, "Sugestões de planejamento por baixo estoque geradas com sucesso!"));
    }

    @PostMapping("/runs/{runId}/suggestions")
    public ResponseEntity<ApiResponseDTO<List<ReturnPlanningSuggestionDTO>>> generateReorderSuggestions(@PathVariable Long runId){
        List<ReturnPlanningSuggestionDTO> suggestions = this.inventoryPlanningService.generateReorderSuggestions(runId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>("success", HttpStatus.CREATED.value(), suggestions, "Sugestões de reabastecimento geradas para a execução " + runId + " com sucesso!"));
    }

    @GetMapping("/runs/{runId}")
    public ResponseEntity<ApiResponseDTO<ReturnInventoryPlanningRunDTO>> findPlanningRunById(@PathVariable Long runId){
        Optional<ReturnInventoryPlanningRunDTO> planningRun = this.inventoryPlanningService.findPlanningRunById(runId);
        return planningRun.map(dto ->
                ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), dto, "Execução de planejamento encontrada!"))
        ).orElseGet(() ->
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDTO<>("error", HttpStatus.NOT_FOUND.value(), null, "Execução de planejamento com ID " + runId + " não encontrada."))
        );
    }

    @GetMapping("/runs")
    public ResponseEntity<ApiResponseDTO<Page<ReturnInventoryPlanningRunDTO>>> findAllPlanningRuns(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<ReturnInventoryPlanningRunDTO> planningRuns = this.inventoryPlanningService.findAllPlanningRuns(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), planningRuns, "Execuções de planejamento retornadas com sucesso!"));
    }

    @GetMapping("/runs/{runId}/suggestions")
    public ResponseEntity<ApiResponseDTO<Page<ReturnPlanningSuggestionDTO>>> findPlanningSuggestionsByRunId(
            @PathVariable Long runId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<ReturnPlanningSuggestionDTO> suggestions = this.inventoryPlanningService.findPlanningSuggestionsByRunId(runId, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), suggestions, "Sugestões de planejamento para a execução " + runId + " retornadas com sucesso!"));
    }

    @PostMapping("/runs/{runId}/suggestions/manual")
    public ResponseEntity<ApiResponseDTO<ReturnPlanningSuggestionDTO>> createManualSuggestion(
            @PathVariable Long runId,
            @RequestBody @Valid PlanningSuggestionDTO suggestionDTO
    ){
        ReturnPlanningSuggestionDTO suggestion = this.inventoryPlanningService.createManualSuggestion(runId, suggestionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>("success", HttpStatus.CREATED.value(), suggestion, "Sugestão de planejamento manual criada para a execução " + runId + " com sucesso!"));
    }

    @DeleteMapping("/runs/{runId}")
    public ResponseEntity<ApiResponseDTO<Void>> deletePlanningRun(@PathVariable Long runId){
        this.inventoryPlanningService.deletePlanningRun(runId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), null, "Execução de planejamento com ID " + runId + " deletada com sucesso!"));
    }

    @DeleteMapping("/suggestions/{suggestionId}")
    public ResponseEntity<ApiResponseDTO<Void>> deletePlanningSuggestion(@PathVariable Long suggestionId){
        this.inventoryPlanningService.deletePlanningSuggestion(suggestionId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), null, "Sugestão de planejamento com ID " + suggestionId + " deletada com sucesso!"));
    }
}
