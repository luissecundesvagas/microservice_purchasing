package com.gruposv.microservice_stock.modules.product.repository;

import com.gruposv.microservice_stock.modules.product.entity.ProductAttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttributeEntity, Long> {
    @Query("SELECT pa FROM ProductAttributeEntity pa JOIN pa.products p WHERE p.id = :productId")
    List<ProductAttributeEntity> findByProductId(@Param("productId") Long productId);
    Optional<ProductAttributeEntity> findByAttributeNameAndAttributeValue(String attributeName, String attributeValue);

    @Query("SELECT COUNT(p) > 0 FROM ProductAttributeEntity p WHERE p.attributeName = :attributeName AND p.attributeValue = :attributeValue AND p.deletedAt IS NULL")
    boolean existsByAttributeNameAndAttributeValue(String attributeName, String attributeValue);

    @Query("SELECT COUNT(p) > 0 FROM ProductAttributeEntity p WHERE p.id = :id AND p.deletedAt IS NULL")
    boolean existsById(Long id);
}
