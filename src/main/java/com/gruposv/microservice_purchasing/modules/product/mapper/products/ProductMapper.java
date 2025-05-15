package com.gruposv.microservice_purchasing.modules.product.mapper.products;

import java.time.LocalDateTime;

import com.gruposv.microservice_purchasing.modules.product.dto.products.ProductDTO;
import com.gruposv.microservice_purchasing.modules.product.dto.products.ReturnProductDTO;
import com.gruposv.microservice_purchasing.modules.product.entity.ProductEntity;

public class ProductMapper {

    public static ProductEntity toEntity(ProductDTO dto) {
        if (dto == null) {
            return null;
        }

        ProductEntity productEntity = new ProductEntity();

        productEntity.setId(dto.getId());
        productEntity.setSkuCode(dto.getSkuCode());
        productEntity.setName(dto.getName());
        productEntity.setDescription(dto.getDescription());
        productEntity.setNcmCode(dto.getNcmCode());
        productEntity.setProductType(dto.getProductType());
        productEntity.setUnitOfMeasure(dto.getUnitOfMeasure());
        productEntity.setProductStatus(dto.getProductStatus());
        productEntity.setCreatedAt(dto.getCreatedAt());
        productEntity.setUpdateAt(dto.getUpdateAt());

        return productEntity;
    }

    public static ProductDTO toDto(ProductEntity product) {
        if (product == null) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(product.getId());
        productDTO.setSkuCode(product.getSkuCode());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setNcmCode(product.getNcmCode());
        productDTO.setProductType(product.getProductType());
        productDTO.setUnitOfMeasure(product.getUnitOfMeasure());
        productDTO.setProductStatus(product.getProductStatus());
        productDTO.setCreatedAt(product.getCreatedAt());
        productDTO.setUpdateAt(product.getUpdateAt());

        return productDTO;
    }

    public static ProductEntity toEntityUpdate(ProductDTO dto, ProductEntity productEntity) {
        if (dto == null || productEntity == null) {
            return productEntity;  // Não há alteração se DTO ou entidade forem nulos.
        }

        // Verificação para não atualizar com valores nulos ou vazios
        if (dto.getSkuCode() != null && !dto.getSkuCode().isEmpty()) {
            productEntity.setSkuCode(dto.getSkuCode());
        }

        if (dto.getName() != null && !dto.getName().isEmpty()) {
            productEntity.setName(dto.getName());
        }

        if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
            productEntity.setDescription(dto.getDescription());
        }

        if (dto.getNcmCode() != null && !dto.getNcmCode().isEmpty()) {
            productEntity.setNcmCode(dto.getNcmCode());
        }

        if (dto.getProductType() != null) {
            productEntity.setProductType(dto.getProductType());
        }

        if (dto.getUnitOfMeasure() != null) {
            productEntity.setUnitOfMeasure(dto.getUnitOfMeasure());
        }

        if (dto.getProductStatus() != null) {
            productEntity.setProductStatus(dto.getProductStatus());
        }

        productEntity.setUpdateAt(LocalDateTime.now());

        return productEntity;
    }

    public static ReturnProductDTO returnDTO(ProductEntity product){
        ReturnProductDTO returnProductDTO = new ReturnProductDTO();
        returnProductDTO.setId(product.getId());
        returnProductDTO.setSkuCode(product.getSkuCode());
        returnProductDTO.setName(product.getName());
        returnProductDTO.setDescription(product.getDescription());
        returnProductDTO.setNcmCode(product.getNcmCode());
        returnProductDTO.setProductType(ProductTypeMapper.toDTO(product.getProductType()));
        returnProductDTO.setUnitOfMeasure(UnitOfMeasureMapper.toDTO(product.getUnitOfMeasure()));
        returnProductDTO.setProductStatus(ProductStatusMapper.toDTO(product.getProductStatus()));
        returnProductDTO.setCreatedAt(product.getCreatedAt());
        returnProductDTO.setUpdateAt(product.getUpdateAt());
        return returnProductDTO;
    }
}

