package com.gruposv.microservice_purchasing.modules.supplier.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import com.gruposv.microservice_purchasing.modules.supplier_documents.entity.SupplierDocumentsEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_suppliers")
@Where(clause = "deleted_at IS NULL")
public class SupplierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Long id;

    @Column(name = "supplier_name", length = 255, nullable = false)
    private String name;

    @Column(name = "document_number", length = 50, nullable = false)
    private String documentNumber;

    @Column(name = "address", length = 255, nullable = false)
    private String address;

    @Column(name = "contact_phone", length = 50, nullable = false)
    private String contactPhone;

    @Column(name = "contact_email", length = 100, nullable = false)
    private String contactEmail;

    @Column(name = "payment_terms", length = 100, nullable = false)
    private String paymentTerms;

    @Column(name = "bank_account_info", nullable = false)
    private String bankAccountInfo;

    @Column(name = "supplier_rating", nullable = false)
    private Double supplierRating;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SupplierDocumentsEntity> supplierDocuments;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
