package com.gruposv.microservice_purchasing.modules.product.mapper.categories;

import com.gruposv.microservice_purchasing.modules.product.dto.categories.ProductCategoryDTO;
import com.gruposv.microservice_purchasing.modules.product.entity.ProductCategoryEntity;

public class CategoriesMapper {

    public static ProductCategoryDTO toDTO(ProductCategoryEntity productCategoryEntity){
        return new ProductCategoryDTO(productCategoryEntity.getId(), productCategoryEntity.getName());
    }

    public static ProductCategoryEntity toEntity(ProductCategoryDTO productCategoryDTO){
        ProductCategoryEntity productCategory = new ProductCategoryEntity();
        productCategory.setName(productCategoryDTO.getName());
        return productCategory;
    }

}
