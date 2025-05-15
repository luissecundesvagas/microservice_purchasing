package com.gruposv.microservice_purchasing.modules.supplier_documents.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gruposv.microservice_purchasing.modules.supplier_documents.entity.SupplierDocumentsEntity;
import com.gruposv.microservice_purchasing.modules.supplier_documents.service.SupplierDocumentsService;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierDocumentsController {

    private final SupplierDocumentsService supplierDocumentsService;

    @Autowired
    public SupplierDocumentsController(SupplierDocumentsService supplierDocumentsService) {
        this.supplierDocumentsService = supplierDocumentsService;
    }

    @PostMapping
    public ResponseEntity<SupplierDocumentsEntity> createSupplier(@RequestBody SupplierDocumentsEntity supplierDocuments) {
        SupplierDocumentsEntity savedSupplier = supplierDocumentsService.saveSupplierDocuments(supplierDocuments);
        return ResponseEntity.ok(savedSupplier);
    }
}
