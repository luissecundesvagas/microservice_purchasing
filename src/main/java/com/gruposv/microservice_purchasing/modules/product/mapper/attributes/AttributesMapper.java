package com.gruposv.microservice_purchasing.modules.product.mapper.attributes;


import com.gruposv.microservice_purchasing.modules.product.dto.attributes.ProductAttributeDTO;
import com.gruposv.microservice_purchasing.modules.product.entity.ProductAttributeEntity;

public class AttributesMapper {
    public static ProductAttributeDTO toDTO(ProductAttributeEntity productAttributeEntity) {
        return new ProductAttributeDTO(productAttributeEntity.getId(), productAttributeEntity.getAttributeName(), productAttributeEntity.getAttributeValue());
    }

    public static ProductAttributeEntity toEntity(ProductAttributeDTO productAttributeDTO){
        ProductAttributeEntity productCategory = new ProductAttributeEntity();
        productCategory.setAttributeName(productAttributeDTO.getAttributeName());
        productCategory.setAttributeValue(productAttributeDTO.getAttributeValue());
        return productCategory;
    }
}
