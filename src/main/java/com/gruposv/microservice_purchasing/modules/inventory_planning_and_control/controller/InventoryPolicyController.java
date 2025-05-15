package com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gruposv.microservice_purchasing.dto.ApiResponseDTO;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.dto.inventory_policy.InventoryPolicyDTO;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.dto.inventory_policy.ListInventoryPolicyDTO;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.dto.inventory_policy.ReturnInventoryPolicyDTO;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.service.InventoryPolicyService;

@RestController
@RequestMapping("/stock/inventory-policies")
public class InventoryPolicyController {

    private final InventoryPolicyService inventoryPolicyService;

    public InventoryPolicyController(InventoryPolicyService inventoryPolicyService) {
        this.inventoryPolicyService = inventoryPolicyService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<ReturnInventoryPolicyDTO>> createNewInventoryPolicy(@RequestBody @Valid InventoryPolicyDTO inventoryPolicyDTO){
        ReturnInventoryPolicyDTO inventoryPolicy = this.inventoryPolicyService.createNewInventoryPolicy(inventoryPolicyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>("success", HttpStatus.CREATED.value(), inventoryPolicy, "Política de inventário criada com sucesso!"));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<Page<ReturnInventoryPolicyDTO>>> findAllInventoryPolicies(@ModelAttribute @Valid ListInventoryPolicyDTO listInventoryPolicyDTO){
        Page<ReturnInventoryPolicyDTO> inventoryPolicies = this.inventoryPolicyService.findAllInventoryPolicies(listInventoryPolicyDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), inventoryPolicies, "Políticas de inventário retornadas com sucesso!"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ReturnInventoryPolicyDTO>> findInventoryPolicyById(@PathVariable Long id){
        ReturnInventoryPolicyDTO inventoryPolicy = this.inventoryPolicyService.findInventoryPolicyById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), inventoryPolicy, "Política de inventário encontrada com sucesso!"));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponseDTO<ReturnInventoryPolicyDTO>> findByProductId(@PathVariable Long productId){
        ReturnInventoryPolicyDTO inventoryPolicy = this.inventoryPolicyService.findByProductId(productId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), inventoryPolicy, "Política de inventário para o produto " + productId + " encontrada com sucesso!"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ReturnInventoryPolicyDTO>> updateInventoryPolicy(
            @PathVariable Long id,
            @RequestBody InventoryPolicyDTO inventoryPolicyDTO
    ){
        ReturnInventoryPolicyDTO inventoryPolicy = this.inventoryPolicyService.updateInventoryPolicy(id, inventoryPolicyDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), inventoryPolicy, "Política de inventário atualizada com sucesso!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteInventoryPolicy(@PathVariable Long id){
        this.inventoryPolicyService.deleteInventoryPolicy(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), null, "Política de inventário com ID " + id + " deletada com sucesso!"));
    }
}
