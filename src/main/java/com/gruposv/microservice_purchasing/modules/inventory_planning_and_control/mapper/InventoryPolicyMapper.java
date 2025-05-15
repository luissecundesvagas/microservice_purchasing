package com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.mapper;

import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.dto.inventory_policy.InventoryPolicyDTO;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.dto.inventory_policy.ReturnInventoryPolicyDTO;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.entity.InventoryPolicyEntity;
import com.gruposv.microservice_purchasing.modules.product.mapper.products.ProductMapper;

public class InventoryPolicyMapper {

    public static InventoryPolicyEntity toCreateEntity(InventoryPolicyDTO inventoryPolicyDTO){
        InventoryPolicyEntity inventoryPolicy = new InventoryPolicyEntity();
        inventoryPolicy.setMinStockLevel(inventoryPolicyDTO.getMinStockLevel());
        inventoryPolicy.setMaxStockLevel(inventoryPolicyDTO.getMaxStockLevel());
        inventoryPolicy.setReorderQuantity(inventoryPolicyDTO.getReorderQuantity());
        inventoryPolicy.setLeadTimeDays(inventoryPolicyDTO.getLeadTimeDays());
        inventoryPolicy.setSafetyStock(inventoryPolicyDTO.getSafetyStock());

        return inventoryPolicy;
    }

    public static InventoryPolicyEntity toUpdateEntity(InventoryPolicyDTO inventoryPolicyDTO, InventoryPolicyEntity inventoryPolicy){

        if(inventoryPolicyDTO.getMinStockLevel() != null){
            inventoryPolicy.setMinStockLevel(inventoryPolicyDTO.getMinStockLevel());
        }

        if(inventoryPolicyDTO.getMaxStockLevel() != null){
            inventoryPolicy.setMaxStockLevel(inventoryPolicyDTO.getMaxStockLevel());
        }

        if(inventoryPolicyDTO.getReorderQuantity() != null) {
            inventoryPolicy.setReorderQuantity(inventoryPolicyDTO.getReorderQuantity());
        }

        if(inventoryPolicyDTO.getLeadTimeDays() != null){
            inventoryPolicy.setLeadTimeDays(inventoryPolicyDTO.getLeadTimeDays());
        }

        if(inventoryPolicyDTO.getSafetyStock() != null){
            inventoryPolicy.setSafetyStock(inventoryPolicyDTO.getSafetyStock());
        }

        return inventoryPolicy;
    }

    public static ReturnInventoryPolicyDTO toDTO(InventoryPolicyEntity inventoryPolicy){
        ReturnInventoryPolicyDTO returnInventoryPolicyDTO = new ReturnInventoryPolicyDTO();

        returnInventoryPolicyDTO.setId(inventoryPolicy.getId());
        returnInventoryPolicyDTO.setProduct(ProductMapper.returnDTO(inventoryPolicy.getProduct()));
        returnInventoryPolicyDTO.setMinStockLevel(inventoryPolicy.getMinStockLevel());
        returnInventoryPolicyDTO.setMaxStockLevel(inventoryPolicy.getMaxStockLevel());
        returnInventoryPolicyDTO.setReorderQuantity(inventoryPolicy.getReorderQuantity());
        returnInventoryPolicyDTO.setLeadTimeDays(inventoryPolicy.getLeadTimeDays());
        returnInventoryPolicyDTO.setSafetyStock(inventoryPolicy.getSafetyStock());

        return returnInventoryPolicyDTO;
    }

}
