package com.gruposv.microservice_stock.modules.product.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_product_attributes")
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE tb_product_attributes SET deleted_at = NOW() WHERE attribute_id = ?")
public class ProductAttributeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attribute_id")
    private Long id;

    @ManyToMany(mappedBy = "productAttributes")
    private List<ProductEntity> products;

    @Column(name = "attribute_name")
    private String attributeName;

    @Column(name = "attribute_value")
    private String attributeValue;

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;

    public ProductAttributeEntity() {
    }

    public ProductAttributeEntity(Long id, List<ProductEntity> products, String attributeName, String attributeValue) {
        this.id = id;
        this.products = products;
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
