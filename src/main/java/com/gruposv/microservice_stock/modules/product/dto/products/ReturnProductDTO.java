package com.gruposv.microservice_stock.modules.product.dto.products;

import java.time.LocalDateTime;

public class ReturnProductDTO {
    private Long id;

    private String skuCode;

    private String name;

    private String description;

    private String ncmCode;

    private ProductTypeDTO productType;

    private UnitOfMeasureDTO unitOfMeasure;

    private ProductStatusDTO productStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    public ReturnProductDTO() {
    }

    public ReturnProductDTO(Long id, String skuCode, String name, String description, String ncmCode, ProductTypeDTO productType, UnitOfMeasureDTO unitOfMeasure, ProductStatusDTO productStatus, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
        this.skuCode = skuCode;
        this.name = name;
        this.description = description;
        this.ncmCode = ncmCode;
        this.productType = productType;
        this.unitOfMeasure = unitOfMeasure;
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

    public ProductTypeDTO getProductType() {
        return productType;
    }

    public void setProductType(ProductTypeDTO productType) {
        this.productType = productType;
    }

    public UnitOfMeasureDTO getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasureDTO unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public ProductStatusDTO getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatusDTO productStatus) {
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
