package com.gruposv.microservice_purchasing.modules.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gruposv.microservice_purchasing.modules.product.entity.ProductEntity;
import com.gruposv.microservice_purchasing.modules.product.enums.ProductStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByIdIn(List<Long> ids);

    Optional<ProductEntity> findByNameContainingIgnoreCase(String name);

    Optional<ProductEntity> findBySkuCode(String skuCode);

    List<ProductEntity> findByProductStatus(ProductStatus productStatus);

    Page<ProductEntity> findByProductStatus(ProductStatus productStatus, Pageable pageable);

    @Query("SELECT COUNT(p) > 0 FROM ProductEntity p WHERE p.name = :name AND p.deletedAt IS NULL")
    boolean existsByName(@Param("name") String name);

    @Query("SELECT COUNT(p) > 0 FROM ProductEntity p WHERE p.skuCode = :skuCode AND p.deletedAt IS NULL")
    boolean existsBySkuCode(@Param("skuCode") String skuCode);
}
