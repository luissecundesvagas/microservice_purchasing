package com.gruposv.microservice_purchasing.modules.supplier_documents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gruposv.microservice_purchasing.modules.supplier_documents.entity.SupplierDocumentsEntity;

@Repository
public interface SupplierDocumentsRepository extends JpaRepository<SupplierDocumentsEntity, Long> {
}