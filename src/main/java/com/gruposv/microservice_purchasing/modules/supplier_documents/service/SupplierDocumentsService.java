package com.gruposv.microservice_purchasing.modules.supplier_documents.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.gruposv.microservice_purchasing.modules.supplier_documents.entity.SupplierDocumentsEntity;
import com.gruposv.microservice_purchasing.modules.supplier_documents.repository.SupplierDocumentsRepository;

@Service
public class SupplierDocumentsService {

    private final SupplierDocumentsRepository supplierDocumentsRepository;

    @Autowired
    public SupplierDocumentsService(SupplierDocumentsRepository supplierDocumentsRepository) {
        this.supplierDocumentsRepository = supplierDocumentsRepository;
    }

    public SupplierDocumentsEntity saveSupplierDocuments(SupplierDocumentsEntity supplierDocuments) {
        return supplierDocumentsRepository.save(supplierDocuments);
    }
}
