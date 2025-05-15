package com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.service;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.dto.inventory_planning_run.InventoryPlanningRunDTO;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.dto.inventory_planning_run.PlanningSuggestionDTO;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.dto.inventory_planning_run.ReturnInventoryPlanningRunDTO;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.dto.inventory_planning_run.ReturnPlanningSuggestionDTO;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.entity.InventoryPlanningRunsEntity;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.entity.InventoryPolicyEntity;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.entity.PlanningSuggestionsEntity;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.enums.TriggerType;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.exception.InventoryPlanningRunNotFoundException;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.exception.PlanningSuggestionNotFoundException;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.mapper.InventoryPlanningRunMapper;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.mapper.PlanningSuggestionMapper;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.repository.InventoryPlanningRunRepository;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.repository.InventoryPolicyRepository;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.repository.PlanningSuggestionRepository;
import com.gruposv.microservice_purchasing.modules.product.entity.ProductEntity;
import com.gruposv.microservice_purchasing.modules.product.enums.ProductStatus;
import com.gruposv.microservice_purchasing.modules.product.repository.ProductRepository;
import com.gruposv.microservice_purchasing.modules.stock.entity.StockBalanceEntity;
import com.gruposv.microservice_purchasing.modules.stock.repository.StockBalanceRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryPlanningService {

    private final InventoryPlanningRunRepository inventoryPlanningRunRepository;
    private final InventoryPolicyRepository inventoryPolicyRepository;
    private final PlanningSuggestionRepository planningSuggestionRepository;
    private final StockBalanceRepository stockBalanceRepository;
    private final ProductRepository productRepository;

    public InventoryPlanningService(InventoryPlanningRunRepository inventoryPlanningRunRepository, InventoryPolicyRepository inventoryPolicyRepository, PlanningSuggestionRepository planningSuggestionRepository, StockBalanceRepository stockBalanceRepository, ProductRepository productRepository) {
        this.inventoryPlanningRunRepository = inventoryPlanningRunRepository;
        this.inventoryPolicyRepository = inventoryPolicyRepository;
        this.planningSuggestionRepository = planningSuggestionRepository;
        this.stockBalanceRepository = stockBalanceRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public ReturnInventoryPlanningRunDTO startPlanningExecution(InventoryPlanningRunDTO runDTO) {
        InventoryPlanningRunsEntity runEntity = InventoryPlanningRunMapper.toEntity(runDTO);
        return InventoryPlanningRunMapper.toDTO(inventoryPlanningRunRepository.save(runEntity));
    }

    @Scheduled(cron = "0 45 08 * * ?", zone = "America/Sao_Paulo")
    @Transactional
    public List<ReturnPlanningSuggestionDTO> generateLowStockPlanningSuggestions() {
        InventoryPlanningRunsEntity planningRun = new InventoryPlanningRunsEntity();
        planningRun.setRunDate(LocalDateTime.now());
        planningRun.setTriggerType(TriggerType.ESTOQUE_BAIXO);
        planningRun = inventoryPlanningRunRepository.save(planningRun);

        List<ReturnPlanningSuggestionDTO> suggestionsDTO = new ArrayList<>();
        List<ProductEntity> activeProducts = productRepository.findByProductStatus(ProductStatus.ACTIVE);

        for (ProductEntity product : activeProducts) {
            Optional<InventoryPolicyEntity> inventoryPolicyOptional = inventoryPolicyRepository.findByProduct_Id(product.getId());

            if (inventoryPolicyOptional.isPresent()) {
                InventoryPolicyEntity policy = inventoryPolicyOptional.get();
                List<StockBalanceEntity> stockBalances = stockBalanceRepository.findAllByProduct_Id(product.getId());

                for (StockBalanceEntity balance : stockBalances) {
                    if (balance.getQuantityOnHand() <= policy.getMinStockLevel()) {
                        PlanningSuggestionsEntity suggestionEntity = new PlanningSuggestionsEntity();
                        suggestionEntity.setInventoryPlanningRun(planningRun);
                        suggestionEntity.setProduct(product);
                        suggestionEntity.setStockLocation(balance.getStockLocation());
                        suggestionEntity.setSuggestedQuantity(policy.getReorderQuantity() != null ? policy.getReorderQuantity() : 10); // Usa reorder_quantity ou um valor padrão
                        suggestionEntity.setReason("Estoque abaixo do nível mínimo.");
                        suggestionsDTO.add(PlanningSuggestionMapper.toDTO(planningSuggestionRepository.save(suggestionEntity)));
                    }
                }
            }
        }
        return suggestionsDTO;
    }

    @Transactional
    public List<ReturnPlanningSuggestionDTO> generateReorderSuggestions(Long runId) {
        List<ReturnPlanningSuggestionDTO> suggestionsDTO = new ArrayList<>();
        Optional<InventoryPlanningRunsEntity> planningRunOptional = inventoryPlanningRunRepository.findById(runId);

        if (planningRunOptional.isPresent()) {
            InventoryPlanningRunsEntity planningRun = planningRunOptional.get();
            List<ProductEntity> activeProducts = productRepository.findByProductStatus(ProductStatus.ACTIVE);

            for (ProductEntity product : activeProducts) {
                Optional<InventoryPolicyEntity> inventoryPolicyOptional = inventoryPolicyRepository.findByProduct_Id(product.getId());

                if (inventoryPolicyOptional.isPresent()) {
                    InventoryPolicyEntity policy = inventoryPolicyOptional.get();
                    List<StockBalanceEntity> stockBalances = stockBalanceRepository.findAllByProduct_Id(product.getId());

                    for (StockBalanceEntity balance : stockBalances) {
                        // Consider safety stock and lead time for proactive planning
                        int reorderPoint = policy.getMinStockLevel() + (policy.getSafetyStock() != null ? policy.getSafetyStock() : 0);

                        // Basic proactive check: if current stock is below reorder point
                        if (balance.getQuantityOnHand() <= reorderPoint) {
                            PlanningSuggestionsEntity suggestionEntity = new PlanningSuggestionsEntity();
                            suggestionEntity.setInventoryPlanningRun(planningRun);
                            suggestionEntity.setProduct(product);
                            suggestionEntity.setStockLocation(balance.getStockLocation());
                            Integer suggestedQty = policy.getReorderQuantity() != null ? policy.getReorderQuantity() : (policy.getMaxStockLevel() != null ? policy.getMaxStockLevel() - balance.getQuantityOnHand() : 10); // Default 10 if no policy
                            suggestionEntity.setSuggestedQuantity(suggestedQty);
                            suggestionEntity.setReason(generateReason(balance.getQuantityOnHand(), policy));
                            suggestionsDTO.add(PlanningSuggestionMapper.toDTO(planningSuggestionRepository.save(suggestionEntity)));
                        }
                    }
                }
            }
        } else {
            throw new InventoryPlanningRunNotFoundException("Execução de planejamento de estoque com ID " + runId + " não encontrada.");
        }
        return suggestionsDTO;
    }

    private String generateReason(Integer quantityOnHand, InventoryPolicyEntity policy) {
        if (quantityOnHand < policy.getMinStockLevel()) {
            return "Estoque abaixo do nível mínimo";
        } else if (policy.getSafetyStock() != null && quantityOnHand < (policy.getMinStockLevel() + policy.getSafetyStock())) {
            return "Estoque abaixo do ponto de segurança";
        } else if (policy.getLeadTimeDays() != null) {
            return "Previsão de necessidade considerando o tempo de reposição";
        }
        return "Necessidade de reabastecimento identificada";
    }

    public Optional<ReturnInventoryPlanningRunDTO> findPlanningRunById(Long runId) {
        return inventoryPlanningRunRepository.findById(runId).map(InventoryPlanningRunMapper::toDTO);
    }

    public Page<ReturnInventoryPlanningRunDTO> findAllPlanningRuns(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return inventoryPlanningRunRepository.findAll(pageable).map(InventoryPlanningRunMapper::toDTO);
    }

    public Page<ReturnPlanningSuggestionDTO> findPlanningSuggestionsByRunId(Long runId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return planningSuggestionRepository.findAllByInventoryPlanningRun_Id(runId, pageable).map(PlanningSuggestionMapper::toDTO);
    }

    @Transactional
    public ReturnPlanningSuggestionDTO createManualSuggestion(Long runId, PlanningSuggestionDTO suggestionDTO) {
        Optional<InventoryPlanningRunsEntity> runOptional = inventoryPlanningRunRepository.findById(runId);
        Optional<ProductEntity> productOptional = productRepository.findById(suggestionDTO.getProductId());

        if (runOptional.isPresent() && productOptional.isPresent()) {
            PlanningSuggestionsEntity suggestionEntity = PlanningSuggestionMapper.toEntity(
                    suggestionDTO, runOptional.get(), productOptional.get()
            );
            return PlanningSuggestionMapper.toDTO(planningSuggestionRepository.save(suggestionEntity));
        } else {
            throw new InventoryPlanningRunNotFoundException("Execução de planejamento com ID " + runId + " ou produto com ID " + suggestionDTO.getProductId() + " não encontrados.");
        }
    }

    @Transactional
    public void deletePlanningRun(Long runId) {
        InventoryPlanningRunsEntity inventoryPlanningRuns = this.inventoryPlanningRunRepository.findById(runId).orElseThrow(() -> new InventoryPlanningRunNotFoundException("Execução de planejamento com ID " + runId + " não encontrada."));
        inventoryPlanningRuns.setDeletedAt(LocalDateTime.now());
        this.inventoryPlanningRunRepository.save(inventoryPlanningRuns);
    }

    @Transactional
    public void deletePlanningSuggestion(Long suggestionId) {
        PlanningSuggestionsEntity planningSuggestion = this.planningSuggestionRepository.findById(suggestionId).orElseThrow(() -> new PlanningSuggestionNotFoundException("Sugestão de planejamento com ID " + suggestionId + " não encontrada."));
        planningSuggestion.setDeletedAt(LocalDateTime.now());
        this.planningSuggestionRepository.save(planningSuggestion);
    }
}
