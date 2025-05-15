package com.gruposv.microservice_purchasing.modules.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gruposv.microservice_purchasing.modules.product.entity.ProductCategoryEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {
    @Query("SELECT pc FROM ProductCategoryEntity pc JOIN pc.products p WHERE p.id = :productId")
    List<ProductCategoryEntity> findByProductId(@Param("productId") Long productId);
    Optional<ProductCategoryEntity> findByNameContainingIgnoreCase(String name);

    @Query("SELECT COUNT(p) > 0 FROM ProductCategoryEntity p WHERE p.name = :name AND p.deletedAt IS NULL")
    boolean existsByName(String name);

    @Query("SELECT COUNT(p) > 0 FROM ProductCategoryEntity p WHERE p.id = :id AND p.deletedAt IS NULL")
    boolean existsById(String id);
}
