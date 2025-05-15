package com.gruposv.microservice_stock.modules.product.dto.products;

import com.gruposv.microservice_stock.modules.product.dto.attributes.ProductAttributeDTO;
import com.gruposv.microservice_stock.modules.product.dto.categories.ProductCategoryDTO;
import com.gruposv.microservice_stock.modules.product.enums.ProductStatus;
import com.gruposv.microservice_stock.modules.product.enums.ProductType;
import com.gruposv.microservice_stock.modules.product.enums.UnitOfMeasure;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class ProductDTO {

    private Long id;

    @NotBlank(message = "O código do produto é obrigatório!")
    private String skuCode;

    @NotBlank(message = "O nome do produto é obrigatório!")
    private String name;

    @NotBlank(message = "O código do produto é obrigatório!")
    private String description;

    @NotBlank(message = "O código ncm é obrigatório")
    private String ncmCode;

    @NotNull(message = "É necessário definir o tipo do produto.")
    private ProductType productType;

    @NotNull(message = "O campo 'Unidade de medida' é obrigatório.")
    private UnitOfMeasure unitOfMeasure;

    @Valid
    private List<ProductAttributeDTO> attributes;

    @Valid
    private List<ProductCategoryDTO> categories;

    private ProductStatus productStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String skuCode, String name, String description, String ncmCode, ProductType productType, UnitOfMeasure unitOfMeasure, List<ProductAttributeDTO> attributes, List<ProductCategoryDTO> categories, ProductStatus productStatus, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
        this.skuCode = skuCode;
        this.name = name;
        this.description = description;
        this.ncmCode = ncmCode;
        this.productType = productType;
        this.unitOfMeasure = unitOfMeasure;
        this.attributes = attributes;
        this.categories = categories;
        this.productStatus = productStatus;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNcmCode() {
        return ncmCode;
    }

    public void setNcmCode(String ncmCode) {
        this.ncmCode = ncmCode;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public List<ProductAttributeDTO> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ProductAttributeDTO> attributes) {
        this.attributes = attributes;
    }

    public List<ProductCategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<ProductCategoryDTO> categories) {
        this.categories = categories;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}
