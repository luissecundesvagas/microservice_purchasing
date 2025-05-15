package com.gruposv.microservice_purchasing.modules.supplier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gruposv.microservice_purchasing.modules.supplier.entity.SupplierEntity;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {
}