package com.gruposv.microservice_stock.modules.product.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_product_categories")
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE tb_product_categories SET deleted_at = NOW() WHERE category_id = ?")
public class ProductCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;

    @ManyToMany(mappedBy = "productCategories")
    private List<ProductEntity> products;

    public ProductCategoryEntity() {
    }

    public ProductCategoryEntity(Long id, String name, List<ProductEntity> products) {
        this.id = id;
        this.name = name;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
