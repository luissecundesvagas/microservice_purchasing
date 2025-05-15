package com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.dto.inventory_policy.InventoryPolicyDTO;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.dto.inventory_policy.ListInventoryPolicyDTO;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.dto.inventory_policy.ReturnInventoryPolicyDTO;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.entity.InventoryPolicyEntity;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.exception.DuplicateInventoryPolicyException;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.exception.NotFoundInventoryPolicyException;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.mapper.InventoryPolicyMapper;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.repository.InventoryPolicyRepository;
import com.gruposv.microservice_purchasing.modules.product.entity.ProductEntity;
import com.gruposv.microservice_purchasing.modules.product.exception.ProductNotFoundException;
import com.gruposv.microservice_purchasing.modules.product.repository.ProductRepository;

import java.time.LocalDateTime;

@Service
public class InventoryPolicyService {

    private final InventoryPolicyRepository inventoryPolicyRepository;
    private final ProductRepository productRepository;

    public InventoryPolicyService(InventoryPolicyRepository inventoryPolicyRepository, ProductRepository productRepository) {
        this.inventoryPolicyRepository = inventoryPolicyRepository;
        this.productRepository = productRepository;
    }

    private void validateStockLevel(InventoryPolicyDTO inventoryPolicyDTO) {
        if (inventoryPolicyDTO.getMinStockLevel() != null && inventoryPolicyDTO.getMaxStockLevel() != null && inventoryPolicyDTO.getMinStockLevel() > inventoryPolicyDTO.getMaxStockLevel()) {
            throw new IllegalArgumentException("O nível mínimo de estoque deve ser menor ou igual ao nível máximo de estoque.");
        }
        if (inventoryPolicyDTO.getSafetyStock() != null && inventoryPolicyDTO.getMaxStockLevel() != null && inventoryPolicyDTO.getSafetyStock() > inventoryPolicyDTO.getMaxStockLevel()) {
            throw new IllegalArgumentException("O estoque de segurança não pode ser maior que o nível máximo de estoque.");
        }
    }

    @Transactional
    public ReturnInventoryPolicyDTO createNewInventoryPolicy(InventoryPolicyDTO inventoryPolicyDTO){
        if(this.inventoryPolicyRepository.existsByProductId(inventoryPolicyDTO.getProductId()))
            throw new DuplicateInventoryPolicyException("Já existe uma política de inventário para o produto com o ID " + inventoryPolicyDTO.getProductId() + ", portanto não é possivel realizar esse cadastro.");

        validateStockLevel(inventoryPolicyDTO);

        InventoryPolicyEntity inventoryPolicy = InventoryPolicyMapper.toCreateEntity(inventoryPolicyDTO);

        ProductEntity product = this.productRepository.findById(inventoryPolicyDTO.getProductId()).orElseThrow(() -> new ProductNotFoundException("O produto com o ID " + inventoryPolicyDTO.getProductId() + " não foi encontrado."));
        inventoryPolicy.setProduct(product);

        return InventoryPolicyMapper.toDTO(this.inventoryPolicyRepository.save(inventoryPolicy));
    }

    public Page<ReturnInventoryPolicyDTO> findAllInventoryPolicies(ListInventoryPolicyDTO listInventoryPolicyDTO){
        Pageable pageable = PageRequest.of(listInventoryPolicyDTO.getPage(), listInventoryPolicyDTO.getSize());
        return this.inventoryPolicyRepository.findAll(pageable).map(InventoryPolicyMapper::toDTO);
    }

    public ReturnInventoryPolicyDTO findInventoryPolicyById(Long id){
        return InventoryPolicyMapper.toDTO(this.inventoryPolicyRepository.findById(id).orElseThrow(() -> new NotFoundInventoryPolicyException("A política de estoque com o ID " + id + " não foi encontrada.")));
    }

    public ReturnInventoryPolicyDTO findByProductId(Long productId){
        return InventoryPolicyMapper.toDTO(this.inventoryPolicyRepository.findByProduct_Id(productId).orElseThrow(() -> new NotFoundInventoryPolicyException("A política de estoque com o id do produto " + productId + " não foi encontrada.")));
    }

    @Transactional
    public ReturnInventoryPolicyDTO updateInventoryPolicy(Long id, InventoryPolicyDTO inventoryPolicyDTO){

        InventoryPolicyEntity inventoryPolicy = this.inventoryPolicyRepository.findById(id).orElseThrow(() -> new NotFoundInventoryPolicyException("A política de estoque com o ID " + id + " não foi encontrada."));

        validateStockLevel(inventoryPolicyDTO);

        inventoryPolicy = InventoryPolicyMapper.toUpdateEntity(inventoryPolicyDTO, inventoryPolicy);

        return InventoryPolicyMapper.toDTO(this.inventoryPolicyRepository.save(inventoryPolicy));
    }

    public void deleteInventoryPolicy(Long id){
        InventoryPolicyEntity inventoryPolicy = this.inventoryPolicyRepository.findById(id).orElseThrow(() -> new NotFoundInventoryPolicyException("A política de estoque com o ID " + id + " não foi encontrada."));
        inventoryPolicy.setDeletedAt(LocalDateTime.now());
        this.inventoryPolicyRepository.save(inventoryPolicy);
    }
}
